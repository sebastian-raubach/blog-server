package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.pojo.*;
import blog.raubach.utils.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.DSLContext;

import java.io.IOException;
import java.sql.*;

import static blog.raubach.database.codegen.tables.Hikeratings.HIKERATINGS;
import static blog.raubach.database.codegen.tables.Hikestats.HIKESTATS;
import static blog.raubach.database.codegen.tables.Hills.HILLS;
import static blog.raubach.database.codegen.tables.Posthills.POSTHILLS;
import static blog.raubach.database.codegen.tables.Posts.POSTS;
import static blog.raubach.database.codegen.tables.Postvideos.POSTVIDEOS;

@Path("import/post")
@Secured
public class PostImportPutResource extends ContextResource
{
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Integer putPostImport(PostImport hi)
			throws IOException, SQLException
	{
		if (hi == null || hi.getType() == null || StringUtils.isEmpty(hi.getTitle()) || (StringUtils.isEmpty(hi.getContent()) && StringUtils.isEmpty(hi.getContentMarkdown())))
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode(), "Payload is null or main attributes aren't set");
			return null;
		}

		boolean hillsValid = isHillsValid(hi.getHills());
		boolean ratingValid = isRatingValid(hi.getRating());
		boolean statsValid = isStatsValid(hi.getStats());

		if (hi.getType() == PostsType.hike && (!hillsValid || !ratingValid || !statsValid))
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode(), "Hike provided, but either hills, rating or stats invalid");
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			PostsRecord post = context.selectFrom(POSTS)
									  .where(POSTS.TITLE.eq(hi.getTitle()))
									  .and(POSTS.CONTENT.isNotDistinctFrom(hi.getContent()))
									  .and(POSTS.CONTENT_MARKDOWN.isNotDistinctFrom(hi.getContentMarkdown()))
									  .and(POSTS.END_DATE.isNotDistinctFrom(hi.getEndDate()))
									  .and(POSTS.CREATED_ON.eq(hi.getCreatedOn()))
									  .and(POSTS.TYPE.eq(hi.getType()))
									  .fetchAny();

			if (post == null)
			{
				post = context.newRecord(POSTS);
				post.setTitle(hi.getTitle());
				post.setContent(hi.getContent());
				post.setContentMarkdown(hi.getContentMarkdown());
				post.setVisible(hi.getVisible());
				post.setEndDate(hi.getEndDate());
				post.setCreatedOn(hi.getCreatedOn());
				post.setType(hi.getType());
				post.store();
			}

			if (ratingValid)
			{
				HikeratingsRecord rating = context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(post.getId())).fetchAny();

				if (rating == null)
				{
					rating = context.newRecord(HIKERATINGS);
					rating.setPostId(post.getId());
				}

				rating.setPath(hi.getRating().getPath());
				rating.setView(hi.getRating().getView());
				rating.setWeather(hi.getRating().getWeather());
				rating.store();
			}

			if (statsValid)
			{
				HikestatsRecord stats = context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(post.getId())).fetchAny();

				if (stats == null)
				{
					stats = context.newRecord(HIKESTATS);
					stats.setPostId(post.getId());
					stats.setElevationProfilePath(null);
					stats.setGpxPath(null);
				}

				stats.setAscent(hi.getStats().getAscent());
				stats.setDuration(hi.getStats().getDuration());
				stats.setDistance(hi.getStats().getDistance());
				stats.store();
			}

			if (hillsValid && !CollectionUtils.isEmpty(hi.getHills()))
			{
				for (PostHill hill : hi.getHills())
				{
					HillsRecord h;

					if (hill.getId() != null)
					{
						h = context.selectFrom(HILLS).where(HILLS.ID.eq(hill.getId())).fetchAny();
					}
					else
					{
						// Check if it exists
						h = context.selectFrom(HILLS)
								   .where(HILLS.NAME.eq(hill.getName()))
								   .and(HILLS.LATITUDE.isNotDistinctFrom(hill.getLatitude()))
								   .and(HILLS.LONGITUDE.isNotDistinctFrom(hill.getLongitude()))
								   .and(HILLS.ELEVATION.isNotDistinctFrom(hill.getElevation()))
								   .and(HILLS.REGION.isNotDistinctFrom(hill.getRegion()))
								   .and(HILLS.URL.isNotDistinctFrom(hill.getUrl()))
								   .fetchAny();
					}

					if (h == null)
					{
						// Create if not
						h = context.newRecord(HILLS, hill);
						h.store();
					}

					// Link to post/hike
					PosthillsRecord ph = context.selectFrom(POSTHILLS).where(POSTHILLS.HILL_ID.eq(h.getId())).and(POSTHILLS.POST_ID.eq(post.getId())).fetchAny();

					if (ph == null)
					{
						ph = context.newRecord(POSTHILLS);
						ph.setPostId(post.getId());
						ph.setHillId(h.getId());
						ph.setSuccessful(hill.isSuccessful());
						ph.store();
					}
				}
			}

			if (!CollectionUtils.isEmpty(hi.getVideos()))
			{
				for (String video : hi.getVideos())
				{
					context.insertInto(POSTVIDEOS)
						   .set(POSTVIDEOS.POST_ID, post.getId())
						   .set(POSTVIDEOS.VIDEO_PATH, video)
						   .execute();
				}
			}

			return post.getId();
		}
	}

	private boolean isRatingValid(Hikeratings rating)
	{
		return rating != null && rating.getPath() != null && rating.getWeather() != null && rating.getView() != null && rating.getPostId() == null;
	}

	private boolean isStatsValid(Hikestats stats)
	{
		return stats != null && stats.getPostId() == null && stats.getAscent() != null && stats.getDistance() != null;
	}

	private boolean isHillsValid(PostHill[] hills)
	{
		if (CollectionUtils.isEmpty(hills))
			return true;

		for (PostHill hill : hills)
		{
			if (hill == null || StringUtils.isEmpty(hill.getName()) || hill.getType() == null)
				return false;
		}

		return true;
	}
}
