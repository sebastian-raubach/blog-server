package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.PostsRecord;
import blog.raubach.pojo.*;
import blog.raubach.utils.StringUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.nio.file.FileSystems;
import java.sql.*;
import java.util.*;

import static blog.raubach.database.codegen.tables.Hikeratings.HIKERATINGS;
import static blog.raubach.database.codegen.tables.Hikestats.HIKESTATS;
import static blog.raubach.database.codegen.tables.Hills.HILLS;
import static blog.raubach.database.codegen.tables.ImageDetails.IMAGE_DETAILS;
import static blog.raubach.database.codegen.tables.Posthills.POSTHILLS;
import static blog.raubach.database.codegen.tables.Postimages.POSTIMAGES;
import static blog.raubach.database.codegen.tables.Posts.POSTS;
import static blog.raubach.database.codegen.tables.Postvideos.POSTVIDEOS;

@Path("post")
@Secured
@PermitAll
public class PostListResource extends BaseResource
{
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hike> getPosts(@QueryParam("year") Integer year, @QueryParam("postType") PostsType postType, @QueryParam("searchQuery") String searchQuery)
			throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<PostsRecord> step = context.selectFrom(POSTS);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			if (StringUtils.isEmpty(userDetails.getToken()))
				step.where(POSTS.VISIBLE.eq(true));

			if (postType != null)
				step.where(POSTS.TYPE.eq(postType));

			if (year != null)
				step.where(DSL.year(POSTS.CREATED_ON).eq(year));

			if (!StringUtils.isEmpty(searchQuery))
			{
				step.where(POSTS.TITLE.contains(searchQuery))
					.or(POSTS.CONTENT.contains(searchQuery))
					.or(POSTS.CONTENT_MARKDOWN.contains(searchQuery))
					.orExists(DSL.selectOne().from(POSTHILLS)
								 .leftJoin(HILLS).on(HILLS.ID.eq(POSTHILLS.HILL_ID))
								 .where(POSTHILLS.POST_ID.eq(POSTS.ID))
								 .and(HILLS.NAME.contains(searchQuery)
												.or(HILLS.REGION.contains(searchQuery))
												.or(HILLS.TYPE.cast(String.class).contains(searchQuery))))
					.orExists(DSL.selectOne().from(POSTIMAGES)
								 .where(POSTIMAGES.POST_ID.eq(POSTS.ID))
								 .and(POSTIMAGES.DESCRIPTION.contains(searchQuery)));
			}

			// Get the posts
			List<Hike> posts = setPaginationAndOrderBy(step)
					.fetchInto(Hike.class);

			// Get the images and videos
			posts.forEach(h -> {
				List<ImageDetails> images = context.selectFrom(IMAGE_DETAILS).where(IMAGE_DETAILS.POST_ID.eq(h.getId())).fetchInto(ImageDetails.class);
				images.forEach(i -> {
					if (!StringUtils.isEmpty(i.getImagePath()))
						i.setImagePath(i.getImagePath().substring(i.getImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
				});
				h.setImages(images);
				h.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(h.getId())).fetchInto(Postvideos.class));
				List<Field<?>> fields = new ArrayList<>();
				fields.addAll(Arrays.asList(HILLS.fields()));
				fields.add(POSTHILLS.SUCCESSFUL);
				h.setHills(context.select(fields).from(HILLS).leftJoin(POSTHILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID)).where(POSTHILLS.POST_ID.eq(h.getId())).fetchInto(PostHill.class));
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

			@NotNull SelectWhereStep<PostsRecord> step = context.selectFrom(POSTS);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			if (StringUtils.isEmpty(userDetails.getToken()))
				step.where(POSTS.VISIBLE.eq(true));

			if (postType != null)
				step.where(POSTS.TYPE.eq(postType));

			if (!StringUtils.isEmpty(searchTerm))
				step.where(POSTS.TITLE.contains(searchTerm));

			// Get the posts
			List<Hike> posts = setPaginationAndOrderBy(step)
					.fetchInto(Hike.class);

			// Get the images and videos
			posts.forEach(h -> {
				List<ImageDetails> images = context.selectFrom(IMAGE_DETAILS).where(IMAGE_DETAILS.POST_ID.eq(h.getId())).fetchInto(ImageDetails.class);
				images.forEach(i -> {
					if (!StringUtils.isEmpty(i.getImagePath()))
						i.setImagePath(i.getImagePath().substring(i.getImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
				});
				h.setImages(images);
				h.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(h.getId())).fetchInto(Postvideos.class));
				List<Field<?>> fields = new ArrayList<>();
				fields.addAll(Arrays.asList(HILLS.fields()));
				fields.add(POSTHILLS.SUCCESSFUL);
				h.setHills(context.select(fields).from(HILLS).leftJoin(POSTHILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID)).where(POSTHILLS.POST_ID.eq(h.getId())).fetchInto(PostHill.class));
				h.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(h.getId())).fetchAnyInto(Hikestats.class));
				h.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(h.getId())).fetchAnyInto(Hikeratings.class));
			});

			return posts;
		}
	}
}
