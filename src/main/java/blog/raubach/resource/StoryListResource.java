package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.Postimages;
import blog.raubach.database.codegen.tables.records.StoriesRecord;
import blog.raubach.pojo.*;
import org.jooq.*;
import org.jooq.impl.DSL;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Postimages.*;
import static blog.raubach.database.codegen.tables.Posts.*;
import static blog.raubach.database.codegen.tables.Stories.*;
import static blog.raubach.database.codegen.tables.Storyposts.*;

@Path("story")
@Secured
@PermitAll
public class StoryListResource extends BaseResource
{
	@QueryParam("postId")
	Integer postId;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Story> getStories(PaginatedRequest request)
		throws SQLException
	{
		processRequest(request);
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<StoriesRecord> select = context.selectFrom(STORIES);

			if (postId != null)
				select.whereExists(DSL.selectOne().from(STORYPOSTS).where(STORYPOSTS.POST_ID.eq(postId)).and(STORYPOSTS.STORY_ID.eq(STORIES.ID)));

			List<Story> stories = setPaginationAndOrderBy(select)
				.fetchInto(Story.class);

			stories.forEach(s -> {
				List<Hike> posts = context.select().from(POSTS)
										  .leftJoin(STORYPOSTS).on(POSTS.ID.eq(STORYPOSTS.POST_ID))
										  .where(STORYPOSTS.STORY_ID.eq(s.getId()))
										  .orderBy(POSTS.CREATED_ON.asc())
										  .fetchInto(Hike.class);
				posts.forEach(p -> p.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(p.getId())).fetchInto(Postimages.class)));
				s.setPosts(posts);
			});

			return stories;
		}
	}
}
