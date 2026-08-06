package blog.raubach.resource.base;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.ViewStoriesRecord;
import blog.raubach.pojo.*;
import blog.raubach.resource.BaseResource;
import blog.raubach.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.*;
import java.util.*;

import static blog.raubach.database.codegen.tables.ViewStories.VIEW_STORIES;

@Path("story")
@Secured
public class StoryResource extends BaseResource
{
	@GET
	@Path("/{storyId:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getStory(@PathParam("storyId") Integer storyId)
			throws IOException, SQLException
	{
		if (storyId == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			ViewStories story = context.selectFrom(VIEW_STORIES)
			                           .where(VIEW_STORIES.STORY_ID.eq(storyId))
			                           .fetchAnyInto(ViewStories.class);

			if (StringUtils.isEmpty(userDetails.getToken()) && story != null)
			{
				if (!CollectionUtils.isEmpty(story.getPosts()))
				{
					// Filter to visible posts
					story.setPosts(Arrays.stream(story.getPosts())
					                     .filter(p -> p.getVisible() == 1)

					                     .toArray(MiniPost[]::new));

					Arrays.stream(story.getPosts())
					      .forEach(p -> {
							  p.setPrimaryImagePath(p.getPrimaryImagePath().substring(p.getPrimaryImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
						  });
				}
			}

			return Response.ok(story).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response postStories(PaginatedRequest request)
			throws SQLException
	{
		processRequest(request);
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			SelectWhereStep<ViewStoriesRecord> step = context.selectFrom(VIEW_STORIES);

			// Get the stories
			List<ViewStories> stories = setPaginationAndOrderBy(step)
					.fetchInto(ViewStories.class);

			if (StringUtils.isEmpty(userDetails.getToken()))
			{
				stories.forEach(s -> {
					if (!CollectionUtils.isEmpty(s.getPosts()))
					{
						// Filter to visible posts
						s.setPosts(Arrays.stream(s.getPosts())
						                 .filter(p -> p.getVisible() == 1)

						                 .toArray(MiniPost[]::new));

						Arrays.stream(s.getPosts())
						      .forEach(p -> {
								  p.setPrimaryImagePath(p.getPrimaryImagePath().substring(p.getPrimaryImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
							  });
					}
				});
			}

			return Response.ok(stories).build();
		}
	}
}
