package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.ImageDetails;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.pojo.*;
import blog.raubach.utils.StringUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.ImageDetails.IMAGE_DETAILS;
import static blog.raubach.database.codegen.tables.Posts.POSTS;
import static blog.raubach.database.codegen.tables.Stories.STORIES;
import static blog.raubach.database.codegen.tables.Storyposts.STORYPOSTS;

@Path("post/{postId}/story")
@Secured
@PermitAll
public class PostStoryResource extends BaseResource
{
	@PathParam("postId")
	private Integer postId;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Story> getPostStories(PaginatedRequest request)
			throws SQLException, IOException
	{
		if (postId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		processRequest(request);
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<?> step = context.selectFrom(STORIES);

			SelectConditionStep<?> condition = DSL.select()
																 .from(STORYPOSTS)
																 .leftJoin(POSTS).on(POSTS.ID.eq(STORYPOSTS.POST_ID))
																 .where(STORYPOSTS.POST_ID.eq(postId)
																						  .and(STORYPOSTS.STORY_ID.eq(STORIES.ID)));

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition.and(POSTS.VISIBLE.eq(true));

			step.whereExists(condition);

			List<Story> stories = setPaginationAndOrderBy(step)
					.fetchInto(Story.class);

			stories.forEach(s -> {
				List<Hike> posts = context.select().from(POSTS).leftJoin(STORYPOSTS).on(POSTS.ID.eq(STORYPOSTS.POST_ID)).where(STORYPOSTS.STORY_ID.eq(s.getId())).fetchInto(Hike.class);
				posts.forEach(p -> {
					List<ImageDetails> images = context.selectFrom(IMAGE_DETAILS).where(IMAGE_DETAILS.POST_ID.eq(p.getId())).fetchInto(ImageDetails.class);
					images.forEach(i -> {
						if (!StringUtils.isEmpty(i.getImagePath()))
							i.setImagePath(i.getImagePath().substring(i.getImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
					});
					p.setImages(images);
				});
				s.setPosts(posts);
			});

			return stories;
		}
	}
}
