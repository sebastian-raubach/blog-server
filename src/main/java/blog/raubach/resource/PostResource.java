package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.pojo.Post;
import org.jooq.DSLContext;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.sql.*;

import static blog.raubach.database.codegen.tables.Postimages.*;
import static blog.raubach.database.codegen.tables.Posts.*;
import static blog.raubach.database.codegen.tables.Postvideos.*;

@Path("post/{postId}")
@Secured
@PermitAll
public class PostResource extends ContextResource
{
	@PathParam("postId")
	private Integer postId;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Post getPost()
		throws SQLException, IOException
	{
		if (postId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			// Get the posts
			Post post = context.selectFrom(POSTS).where(POSTS.TYPE.eq(PostsType.news))
							   .and(POSTS.ID.eq(postId))
							   .fetchAnyInto(Post.class);

			if (post == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			// Get the images and videos
			post.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(post.getId())).fetchInto(Postimages.class));
			post.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(post.getId())).fetchInto(Postvideos.class));

			return post;
		}
	}
}
