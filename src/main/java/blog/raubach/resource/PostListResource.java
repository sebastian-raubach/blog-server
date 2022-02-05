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
	public List<Hike> getHikes(@QueryParam("year") Integer year, @QueryParam("postType") PostsType postType)
		throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectConditionStep<PostsRecord> step = context.selectFrom(POSTS)
														   .where(POSTS.TYPE.eq(postType));

			if (year != null)
				step.and(DSL.year(POSTS.CREATED_ON).eq(year));

			// Get the posts
			List<Hike> posts = setPaginationAndOrderBy(step)
				.fetchInto(Hike.class);

			// Get the images and videos
			posts.forEach(h -> {
				h.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(h.getId())).fetchInto(Postimages.class));
				h.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(h.getId())).fetchInto(Postvideos.class));
				h.setHills(context.selectFrom(HILLS).where(DSL.exists(DSL.selectFrom(POSTHILLS).where(POSTHILLS.HILL_ID.eq(HILLS.ID)).and(POSTHILLS.POST_ID.eq(h.getId())))).fetchInto(Hills.class));
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
				h.setHills(context.selectFrom(HILLS).where(DSL.exists(DSL.selectFrom(POSTHILLS).where(POSTHILLS.HILL_ID.eq(HILLS.ID)).and(POSTHILLS.POST_ID.eq(h.getId())))).fetchInto(Hills.class));
				h.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(h.getId())).fetchAnyInto(Hikestats.class));
				h.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(h.getId())).fetchAnyInto(Hikeratings.class));
			});

			return posts;
		}
	}
}
