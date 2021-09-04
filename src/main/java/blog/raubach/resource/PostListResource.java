package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.pojo.*;
import org.jooq.DSLContext;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Postimages.*;
import static blog.raubach.database.codegen.tables.Posts.*;
import static blog.raubach.database.codegen.tables.Postvideos.*;

@Path("post")
@Secured
@PermitAll
public class PostListResource extends BaseResource
{
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Post> getPosts(PaginatedRequest request)
		throws SQLException
	{
		processRequest(request);
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			// Get the posts
			List<Post> posts = setPaginationAndOrderBy(context.selectFrom(POSTS).where(POSTS.TYPE.eq(PostsType.news)))
				.fetchInto(Post.class);

			// Get the images and videos
			posts.forEach(p -> {
				p.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(p.getId())).fetchInto(Postimages.class));
				p.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(p.getId())).fetchInto(Postvideos.class));
			});

			return posts;
		}
	}
}
