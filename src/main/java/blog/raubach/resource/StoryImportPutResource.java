package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.Stories;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.utils.*;
import org.jooq.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Stories.*;
import static blog.raubach.database.codegen.tables.Storyposts.*;

@Path("import/story")
@Secured
public class StoryImportPutResource extends ContextResource
{
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Integer putStory(Stories story)
		throws SQLException, IOException
	{
		if (story == null || StringUtils.isEmpty(story.getTitle()) || StringUtils.isEmpty(story.getContent()))
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			StoriesRecord record = context.newRecord(STORIES, story);
			record.store();

			return record.getId();
		}
	}

	@Path("/{storyId}/post")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean putStoryPosts(@PathParam("storyId") Integer storyId, List<Integer> postIds)
		throws SQLException, IOException
	{
		if (CollectionUtils.isEmpty(postIds) || storyId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return false;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			InsertValuesStep2<StorypostsRecord, Integer, Integer> step = context.insertInto(STORYPOSTS, STORYPOSTS.STORY_ID, STORYPOSTS.POST_ID);

			postIds.forEach(p -> step.values(storyId, p));

			return step.execute() > 0;
		}
	}
}
