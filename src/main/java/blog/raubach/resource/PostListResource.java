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
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Post> getHikes(@QueryParam("year") Integer year)
		throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectConditionStep<PostsRecord> step = context.selectFrom(POSTS).where(POSTS.TYPE.eq(PostsType.news));

			if (year != null)
				step.and(DSL.year(POSTS.CREATED_ON).eq(year));

			// Get the posts
			List<Post> posts = setPaginationAndOrderBy(step)
				.fetchInto(Post.class);

			// Get the images and videos
			posts.forEach(p -> {
				p.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(p.getId())).fetchInto(Postimages.class));
				p.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(p.getId())).fetchInto(Postvideos.class));
			});

			return posts;
		}
	}

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

			SelectConditionStep<PostsRecord> step = context.selectFrom(POSTS).where(POSTS.TYPE.eq(PostsType.news));

			if (!StringUtils.isEmpty(searchTerm))
				step.and(POSTS.TITLE.contains(searchTerm));

			// Get the posts
			List<Post> posts = setPaginationAndOrderBy(step)
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
