package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.StoriesRecord;
import blog.raubach.pojo.*;
import blog.raubach.utils.StringUtils;
import org.jooq.*;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.*;
import java.util.*;

import static blog.raubach.database.codegen.tables.Hikeratings.*;
import static blog.raubach.database.codegen.tables.Hikestats.*;
import static blog.raubach.database.codegen.tables.Hills.*;
import static blog.raubach.database.codegen.tables.ImageDetails.IMAGE_DETAILS;
import static blog.raubach.database.codegen.tables.Posthills.*;
import static blog.raubach.database.codegen.tables.Postimages.*;
import static blog.raubach.database.codegen.tables.Posts.*;
import static blog.raubach.database.codegen.tables.Postvideos.*;
import static blog.raubach.database.codegen.tables.Stories.*;
import static blog.raubach.database.codegen.tables.Storyposts.*;

@Path("story/{storyId}")
@Secured
@PermitAll
public class StoryResource extends ContextResource
{
	@PathParam("storyId")
	private Integer storyId;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Story getStory()
		throws IOException, SQLException
	{
		if (storyId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<StoriesRecord> step = context.selectFrom(STORIES);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			if (storyId != null)
				step.where(STORIES.ID.eq(storyId));

			Story story = step.fetchAnyInto(Story.class);

			if (story != null)
			{
				List<Hike> posts = context.select()
										  .from(POSTS)
										  .leftJoin(STORYPOSTS).on(POSTS.ID.eq(STORYPOSTS.POST_ID))
										  .where(STORYPOSTS.STORY_ID.eq(story.getId()))
										  .and(condition)
										  .orderBy(POSTS.CREATED_ON.asc())
										  .fetchInto(Hike.class);
				posts.forEach(p -> {
					List<ImageDetails> images = context.selectFrom(IMAGE_DETAILS).where(IMAGE_DETAILS.POST_ID.eq(p.getId())).fetchInto(ImageDetails.class);
					images.forEach(i -> {
						if (!StringUtils.isEmpty(i.getImagePath()))
							i.setImagePath(i.getImagePath().substring(i.getImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
					});
					p.setImages(images);
					p.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(p.getId())).fetchInto(Postvideos.class));
					List<Field<?>> fields = new ArrayList<>();
					fields.addAll(Arrays.asList(HILLS.fields()));
					fields.add(POSTHILLS.SUCCESSFUL);
					p.setHills(context.select(fields).from(HILLS).leftJoin(POSTHILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID)).where(POSTHILLS.POST_ID.eq(p.getId())).fetchInto(PostHill.class));
					p.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(p.getId())).fetchAnyInto(Hikestats.class));
					p.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(p.getId())).fetchAnyInto(Hikeratings.class));
				});
				story.setPosts(posts);
			}

			return story;
		}
	}
}
