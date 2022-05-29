package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.Postimages;
import blog.raubach.database.codegen.tables.records.StoriesRecord;
import blog.raubach.pojo.*;
import blog.raubach.utils.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Hills.*;
import static blog.raubach.database.codegen.tables.Posthills.*;
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

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Story> getStories(@QueryParam("searchQuery") String searchQuery)
		throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<StoriesRecord> select = context.selectFrom(STORIES);

			if (!StringUtils.isEmpty(searchQuery))
			{
				select.where(STORIES.TITLE.contains(searchQuery))
					  .or(STORIES.CONTENT.contains(searchQuery))
					  .orExists(DSL.selectOne().from(POSTS)
								   .leftJoin(STORYPOSTS).on(STORYPOSTS.POST_ID.eq(POSTS.ID))
								   .where(STORYPOSTS.STORY_ID.eq(STORIES.ID))
								   .and(POSTS.TITLE.contains(searchQuery)
												   .or(POSTS.CONTENT.contains(searchQuery))))
					  .orExists(DSL.selectOne().from(POSTIMAGES)
								   .leftJoin(POSTS).on(POSTS.ID.eq(POSTIMAGES.POST_ID))
								   .leftJoin(STORYPOSTS).on(STORYPOSTS.POST_ID.eq(POSTS.ID))
								   .where(STORYPOSTS.STORY_ID.eq(STORIES.ID))
								   .and(POSTIMAGES.DESCRIPTION.contains(searchQuery)))
					  .orExists(DSL.selectOne().from(POSTHILLS)
								   .leftJoin(POSTS).on(POSTS.ID.eq(POSTHILLS.POST_ID))
								   .leftJoin(STORYPOSTS).on(STORYPOSTS.POST_ID.eq(POSTS.ID))
								   .leftJoin(HILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID))
								   .where(STORYPOSTS.STORY_ID.eq(STORIES.ID))
								   .and(HILLS.NAME.contains(searchQuery)
												  .or(HILLS.REGION.contains(searchQuery))
												  .or(HILLS.TYPE.cast(String.class).contains(searchQuery))));
			}

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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Story> postStories(PaginatedRequest request)
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
