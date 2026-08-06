package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.ViewStories;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.pojo.MiniPost;
import blog.raubach.utils.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static blog.raubach.database.codegen.tables.Stories.STORIES;
import static blog.raubach.database.codegen.tables.Storyposts.STORYPOSTS;

@Path("import/story")
@Secured
public class StoryImportPutResource extends ContextResource
{
	@PATCH
	@Path("/{storyId:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response patchStory(@PathParam("storyId") Integer storyId, ViewStories story)
			throws IOException, SQLException
	{
		if (storyId == null || story == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			StoriesRecord existing = context.selectFrom(STORIES)
			                                .where(STORIES.ID.eq(storyId))
			                                .fetchAny();

			if (existing != null)
			{
				existing.setTitle(story.getStoryTitle());
				existing.setContent(story.getStoryContent());
				existing.setContentMarkdown(story.getStoryMarkdown());
				existing.setCreatedOn(story.getStoryStartDate());
				existing.store();

				if (!CollectionUtils.isEmpty(story.getPosts()))
				{
					Set<Integer> existingPosts = context.select(STORYPOSTS.POST_ID).from(STORYPOSTS).where(STORYPOSTS.STORY_ID.eq(storyId)).fetchSet(STORYPOSTS.POST_ID);
					Set<Integer> newPosts = Arrays.stream(story.getPosts()).map(MiniPost::getId).collect(Collectors.toSet());

					// Add all that have been added
					for (MiniPost newPost : story.getPosts())
					{
						if (!existingPosts.contains(newPost.getId()))
						{
							StorypostsRecord rec = context.newRecord(STORYPOSTS);
							rec.setStoryId(storyId);
							rec.setPostId(newPost.getId());
							rec.store();
						}
					}

					// Remove any that existed before, but have been removed
					for (Integer existingPostId : existingPosts)
						if (!newPosts.contains(existingPostId))
							context.deleteFrom(STORYPOSTS).where(STORYPOSTS.STORY_ID.eq(storyId)).and(STORYPOSTS.POST_ID.eq(existingPostId)).execute();
				}
			}

			return Response.ok().build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putStory(ViewStories story)
			throws SQLException, IOException
	{
		if (story == null || StringUtils.isEmpty(story.getStoryTitle()) || StringUtils.isEmpty(story.getStoryMarkdown()))
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			StoriesRecord record = context.newRecord(STORIES);
			record.setTitle(story.getStoryTitle());
			record.setContentMarkdown(story.getStoryMarkdown());
			record.setCreatedOn(story.getStoryStartDate());
			record.store();

			if (!CollectionUtils.isEmpty(story.getPosts()))
			{
				for (MiniPost post : story.getPosts())
				{
					StorypostsRecord sp = context.newRecord(STORYPOSTS);
					sp.setPostId(post.getId());
					sp.setStoryId(record.getId());
					sp.store();
				}
			}

			return Response.ok(record.getId()).build();
		}
	}
}
