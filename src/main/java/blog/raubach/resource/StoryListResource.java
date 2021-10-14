package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.Postimages;
import blog.raubach.pojo.*;
import org.jooq.DSLContext;

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

			List<Story> stories = setPaginationAndOrderBy(context.selectFrom(STORIES))
				.fetchInto(Story.class);

			stories.forEach(s -> {
				List<Hike> posts = context.select().from(POSTS).leftJoin(STORYPOSTS).on(POSTS.ID.eq(STORYPOSTS.POST_ID)).where(STORYPOSTS.STORY_ID.eq(s.getId())).fetchInto(Hike.class);
				posts.forEach(p -> p.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(p.getId())).fetchInto(Postimages.class)));
				s.setPosts(posts);
			});

			return stories;
		}
	}
}
