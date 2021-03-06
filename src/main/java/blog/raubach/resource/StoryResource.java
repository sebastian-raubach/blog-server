package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.StoriesRecord;
import blog.raubach.pojo.*;
import org.jooq.*;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static blog.raubach.database.codegen.tables.Hikeratings.*;
import static blog.raubach.database.codegen.tables.Hikestats.*;
import static blog.raubach.database.codegen.tables.Hills.*;
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

			if (storyId != null)
				step.where(STORIES.ID.eq(storyId));

			Story story = step.fetchAnyInto(Story.class);

			if (story != null)
			{
				List<Hike> posts = context.select()
										  .from(POSTS)
										  .leftJoin(STORYPOSTS).on(POSTS.ID.eq(STORYPOSTS.POST_ID))
										  .where(STORYPOSTS.STORY_ID.eq(story.getId()))
										  .orderBy(POSTS.CREATED_ON.asc())
										  .fetchInto(Hike.class);
				posts.forEach(p -> {
					p.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(p.getId())).fetchInto(Postimages.class));
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
