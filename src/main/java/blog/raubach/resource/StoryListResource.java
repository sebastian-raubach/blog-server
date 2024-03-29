package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.ImageDetails;
import blog.raubach.database.codegen.tables.records.StoriesRecord;
import blog.raubach.pojo.*;
import blog.raubach.utils.StringUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.nio.file.FileSystems;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Hills.HILLS;
import static blog.raubach.database.codegen.tables.ImageDetails.IMAGE_DETAILS;
import static blog.raubach.database.codegen.tables.Posthills.POSTHILLS;
import static blog.raubach.database.codegen.tables.Postimages.POSTIMAGES;
import static blog.raubach.database.codegen.tables.Posts.POSTS;
import static blog.raubach.database.codegen.tables.Stories.STORIES;
import static blog.raubach.database.codegen.tables.Storyposts.STORYPOSTS;

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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			if (!StringUtils.isEmpty(searchQuery))
			{
				select.where(STORIES.TITLE.contains(searchQuery))
					  .or(STORIES.CONTENT.contains(searchQuery))
					  .orExists(DSL.selectOne().from(POSTS)
								   .leftJoin(STORYPOSTS).on(STORYPOSTS.POST_ID.eq(POSTS.ID))
								   .where(STORYPOSTS.STORY_ID.eq(STORIES.ID))
								   .and(condition)
								   .and(POSTS.TITLE.contains(searchQuery)
												   .or(POSTS.CONTENT.contains(searchQuery))))
					  .orExists(DSL.selectOne().from(POSTIMAGES)
								   .leftJoin(POSTS).on(POSTS.ID.eq(POSTIMAGES.POST_ID))
								   .leftJoin(STORYPOSTS).on(STORYPOSTS.POST_ID.eq(POSTS.ID))
								   .where(STORYPOSTS.STORY_ID.eq(STORIES.ID))
								   .and(condition)
								   .and(POSTIMAGES.DESCRIPTION.contains(searchQuery)))
					  .orExists(DSL.selectOne().from(POSTHILLS)
								   .leftJoin(POSTS).on(POSTS.ID.eq(POSTHILLS.POST_ID))
								   .leftJoin(STORYPOSTS).on(STORYPOSTS.POST_ID.eq(POSTS.ID))
								   .leftJoin(HILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID))
								   .where(STORYPOSTS.STORY_ID.eq(STORIES.ID))
								   .and(condition)
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
										  .and(condition)
										  .orderBy(POSTS.CREATED_ON.asc())
										  .fetchInto(Hike.class);
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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			if (postId != null)
				select.whereExists(DSL.selectOne()
									  .from(STORYPOSTS)
									  .leftJoin(POSTS).on(POSTS.ID.eq(STORYPOSTS.POST_ID))
									  .where(STORYPOSTS.POST_ID.eq(postId))
									  .and(condition)
									  .and(STORYPOSTS.STORY_ID.eq(STORIES.ID)));

			List<Story> stories = setPaginationAndOrderBy(select)
					.fetchInto(Story.class);

			stories.forEach(s -> {
				List<Hike> posts = context.select().from(POSTS)
										  .leftJoin(STORYPOSTS).on(POSTS.ID.eq(STORYPOSTS.POST_ID))
										  .where(STORYPOSTS.STORY_ID.eq(s.getId()))
										  .and(condition)
										  .orderBy(POSTS.CREATED_ON.asc())
										  .fetchInto(Hike.class);
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
