package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.PostsRecord;
import blog.raubach.pojo.*;
import blog.raubach.utils.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.sql.*;
import java.util.*;

import static blog.raubach.database.codegen.tables.Hikeratings.*;
import static blog.raubach.database.codegen.tables.Hikestats.*;
import static blog.raubach.database.codegen.tables.Hills.*;
import static blog.raubach.database.codegen.tables.Posthills.*;
import static blog.raubach.database.codegen.tables.Postimages.*;
import static blog.raubach.database.codegen.tables.Posts.*;
import static blog.raubach.database.codegen.tables.Postvideos.*;

@Path("post")
@Secured
@PermitAll
public class PostListResource extends BaseResource
{
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hike> getPosts(@QueryParam("year") Integer year, @QueryParam("postType") PostsType postType, @QueryParam("searchQuery") String searchQuery)
		throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<PostsRecord> step = context.selectFrom(POSTS);

			if (postType != null)
				step.where(POSTS.TYPE.eq(postType));

			if (year != null)
				step.where(DSL.year(POSTS.CREATED_ON).eq(year));

			if (!StringUtils.isEmpty(searchQuery))
			{
				step.where(POSTS.TITLE.contains(searchQuery))
					.or(POSTS.CONTENT.contains(searchQuery))
					.or(POSTS.CONTENT_MARKDOWN.contains(searchQuery))
					.orExists(DSL.selectOne().from(POSTHILLS)
								 .leftJoin(HILLS).on(HILLS.ID.eq(POSTHILLS.HILL_ID))
								 .where(POSTHILLS.POST_ID.eq(POSTS.ID))
								 .and(HILLS.NAME.contains(searchQuery)
												.or(HILLS.REGION.contains(searchQuery))
												.or(HILLS.TYPE.cast(String.class).contains(searchQuery))))
					.orExists(DSL.selectOne().from(POSTIMAGES)
								 .where(POSTIMAGES.POST_ID.eq(POSTS.ID))
								 .and(POSTIMAGES.DESCRIPTION.contains(searchQuery)));
			}

			// Get the posts
			List<Hike> posts = setPaginationAndOrderBy(step)
				.fetchInto(Hike.class);

			// Get the images and videos
			posts.forEach(h -> {
				h.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(h.getId())).fetchInto(Postimages.class));
				h.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(h.getId())).fetchInto(Postvideos.class));
				List<Field<?>> fields = new ArrayList<>();
				fields.addAll(Arrays.asList(HILLS.fields()));
				fields.add(POSTHILLS.SUCCESSFUL);
				h.setHills(context.select(fields).from(HILLS).leftJoin(POSTHILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID)).where(POSTHILLS.POST_ID.eq(h.getId())).fetchInto(PostHill.class));
				h.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(h.getId())).fetchAnyInto(Hikestats.class));
				h.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(h.getId())).fetchAnyInto(Hikeratings.class));
			});

			return posts;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hike> getPosts(PaginatedRequest request, @QueryParam("postType") PostsType postType)
		throws SQLException
	{
		processRequest(request);
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectConditionStep<PostsRecord> step = context.selectFrom(POSTS)
														   .where(POSTS.TYPE.eq(postType));

			if (!StringUtils.isEmpty(searchTerm))
				step.and(POSTS.TITLE.contains(searchTerm));

			// Get the posts
			List<Hike> posts = setPaginationAndOrderBy(step)
				.fetchInto(Hike.class);

			// Get the images and videos
			posts.forEach(h -> {
				h.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(h.getId())).fetchInto(Postimages.class));
				h.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(h.getId())).fetchInto(Postvideos.class));
				List<Field<?>> fields = new ArrayList<>();
				fields.addAll(Arrays.asList(HILLS.fields()));
				fields.add(POSTHILLS.SUCCESSFUL);
				h.setHills(context.select(fields).from(HILLS).leftJoin(POSTHILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID)).where(POSTHILLS.POST_ID.eq(h.getId())).fetchInto(PostHill.class));
				h.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(h.getId())).fetchAnyInto(Hikestats.class));
				h.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(h.getId())).fetchAnyInto(Hikeratings.class));
			});

			return posts;
		}
	}
}
