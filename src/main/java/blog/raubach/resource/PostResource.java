package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.pojo.*;
import blog.raubach.utils.*;
import org.jooq.*;
import org.jooq.impl.DSL;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.io.*;
import java.io.File;
import java.sql.*;
import java.util.*;

import static blog.raubach.database.codegen.tables.Hikeratings.*;
import static blog.raubach.database.codegen.tables.Hikestats.*;
import static blog.raubach.database.codegen.tables.Hills.*;
import static blog.raubach.database.codegen.tables.Posthills.*;
import static blog.raubach.database.codegen.tables.Postimages.*;
import static blog.raubach.database.codegen.tables.Posts.*;
import static blog.raubach.database.codegen.tables.Postvideos.*;
import static blog.raubach.database.codegen.tables.Relationships.*;

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
	public Hike getPost()
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
			Hike post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId))
							   .fetchAnyInto(Hike.class);

			if (post == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			// Increment view count
			PostsRecord rec = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).fetchAny();
			rec.setViewCount(rec.getViewCount() + 1);
			rec.store(POSTS.VIEW_COUNT);

			// Get the images and videos
			post.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(post.getId())).fetchInto(Postimages.class));
			post.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(post.getId())).fetchInto(Postvideos.class));
			List<Field<?>> fields = new ArrayList<>();
			fields.addAll(Arrays.asList(HILLS.fields()));
			fields.add(POSTHILLS.SUCCESSFUL);
			post.setHills(context.select(fields).from(HILLS).leftJoin(POSTHILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID)).where(POSTHILLS.POST_ID.eq(post.getId())).fetchInto(PostHill.class));
			post.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(post.getId())).fetchAnyInto(Hikestats.class));
			post.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(post.getId())).fetchAnyInto(Hikeratings.class));

			return post;
		}
	}

	@GET
	@Path("/related")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hike> getRelatedPosts()
		throws IOException, SQLException
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
			Hike post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId))
							   .fetchAnyInto(Hike.class);

			if (post == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			List<Hike> posts = context.selectFrom(POSTS)
									  .whereExists(DSL.selectOne().from(RELATIONSHIPS).where(RELATIONSHIPS.POST_A_ID.eq(POSTS.ID).and(RELATIONSHIPS.POST_B_ID.eq(post.getId()))))
									  .orExists(DSL.selectOne().from(RELATIONSHIPS).where(RELATIONSHIPS.POST_B_ID.eq(POSTS.ID).and(RELATIONSHIPS.POST_A_ID.eq(post.getId()))))
									  .fetchInto(Hike.class);
			posts.forEach(p -> {
				p.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(p.getId())).fetchInto(Postimages.class));
				p.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(p.getId())).fetchInto(Postvideos.class));
				List<Field<?>> fields = new ArrayList<>();
				fields.addAll(Arrays.asList(HILLS.fields()));
				fields.add(POSTHILLS.SUCCESSFUL);
				p.setHills(context.select(fields).from(HILLS).leftJoin(POSTHILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID)).where(POSTHILLS.POST_ID.eq(p.getId())).fetchInto(PostHill.class));
				p.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(p.getId())).fetchAnyInto(Hikestats.class));
				p.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(p.getId())).fetchAnyInto(Hikeratings.class));
			});

			return posts;
		}
	}

	@POST
	@Path("/related")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean PostRelatedPosts(List<Integer> postIds)
		throws IOException, SQLException
	{
		if (postId == null || CollectionUtils.isEmpty(postIds))
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return false;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			// Get the posts
			Hike post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId))
							   .fetchAnyInto(Hike.class);

			if (post == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return false;
			}

			List<Integer> existingRelatedPostIds = context.select(POSTS.ID).from(POSTS)
														  .whereExists(DSL.selectOne().from(RELATIONSHIPS).where(RELATIONSHIPS.POST_A_ID.eq(POSTS.ID).and(RELATIONSHIPS.POST_B_ID.eq(post.getId()))))
														  .orExists(DSL.selectOne().from(RELATIONSHIPS).where(RELATIONSHIPS.POST_B_ID.eq(POSTS.ID).and(RELATIONSHIPS.POST_A_ID.eq(post.getId()))))
														  .fetchInto(Integer.class);

			// Remove all existing
			postIds.removeAll(existingRelatedPostIds);

			postIds.forEach(p -> {
				Integer min = Math.min(p, postId);
				Integer max = Math.max(p, postId);

				RelationshipsRecord rec = context.newRecord(RELATIONSHIPS);
				rec.setPostAId(min);
				rec.setPostBId(max);
				rec.store();
			});

			return true;
		}
	}

	@GET
	@Path("/gpx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/gpx+xml")
	public Response getHikeGpx()
		throws IOException, SQLException
	{
		if (postId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).fetchAnyInto(Posts.class);
			Hikestats stats = context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(postId)).fetchAnyInto(Hikestats.class);

			if (post == null || stats == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			File mediaFolder = new File(PropertyWatcher.get("media.directory.external"));
			File gpx = new File(mediaFolder, stats.getGpxPath());

			if (!gpx.exists() || !gpx.isFile())
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			return Response.ok(gpx)
						   .type("application/gpx+xml")
						   .header("content-disposition", "attachment;filename= \"" + gpx.getName() + "\"")
						   .header("content-length", gpx.length())
						   .build();
		}
	}

	@GET
	@Path("/elevation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/tab-separated-values")
	public Response getElevationProfile()
		throws IOException, SQLException
	{
		if (postId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).fetchAnyInto(Posts.class);
			Hikestats stats = context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(postId)).fetchAnyInto(Hikestats.class);

			if (post == null || stats == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			File mediaFolder = new File(PropertyWatcher.get("media.directory.external"));
			String ep = stats.getElevationProfilePath();
			if (StringUtils.isEmpty(ep))
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}
			File elevation = new File(mediaFolder, ep);

			if (!elevation.exists() || !elevation.isFile())
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			return Response.ok(elevation)
						   .type("text/tab-separated-values")
						   .header("content-disposition", "attachment;filename= \"" + elevation.getName() + "\"")
						   .header("content-length", elevation.length())
						   .build();
		}
	}

	@GET
	@Path("/time-distance")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/tab-separated-values")
	public Response getTimeDistanceProfile()
		throws IOException, SQLException
	{
		if (postId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).fetchAnyInto(Posts.class);
			Hikestats stats = context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(postId)).fetchAnyInto(Hikestats.class);

			if (post == null || stats == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			File mediaFolder = new File(PropertyWatcher.get("media.directory.external"));
			String tdp = stats.getTimeDistanceProfilePath();
			if (StringUtils.isEmpty(tdp))
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			File elevation = new File(mediaFolder, tdp);

			if (!elevation.exists() || !elevation.isFile())
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			return Response.ok(elevation)
						   .type("text/tab-separated-values")
						   .header("content-disposition", "attachment;filename= \"" + elevation.getName() + "\"")
						   .header("content-length", elevation.length())
						   .build();
		}
	}
}
