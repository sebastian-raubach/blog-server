package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.pojo.*;
import blog.raubach.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.File;
import java.io.*;
import java.nio.file.FileSystems;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static blog.raubach.database.codegen.tables.Hikeratings.HIKERATINGS;
import static blog.raubach.database.codegen.tables.Hikestats.HIKESTATS;
import static blog.raubach.database.codegen.tables.HillIndividuals.HILL_INDIVIDUALS;
import static blog.raubach.database.codegen.tables.Hills.HILLS;
import static blog.raubach.database.codegen.tables.ImageDetails.IMAGE_DETAILS;
import static blog.raubach.database.codegen.tables.Individuals.INDIVIDUALS;
import static blog.raubach.database.codegen.tables.Posthills.POSTHILLS;
import static blog.raubach.database.codegen.tables.Posts.POSTS;
import static blog.raubach.database.codegen.tables.Postsites.POSTSITES;
import static blog.raubach.database.codegen.tables.Postvideos.POSTVIDEOS;
import static blog.raubach.database.codegen.tables.Relationships.RELATIONSHIPS;
import static blog.raubach.database.codegen.tables.Sites.SITES;

@Path("post/{postId}")
@Secured
public class PostResource extends ContextResource
{
	@PathParam("postId")
	private Integer postId;

	@POST
	@Path("/site")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSite(Sites site)
			throws SQLException
	{
		if (StringUtils.isEmpty(site.getName()) || site.getSitetype() == null || site.getGroundtype() == null || site.getLatitude() == null || site.getLongitude() == null || site.getRating() == null || site.getFacilities() == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			PostsRecord postsRecord = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).fetchAny();

			if (postsRecord == null)
				return Response.status(Response.Status.NOT_FOUND).build();

			SitesRecord record = context.newRecord(SITES, site);
			record.store();

			PostsitesRecord postSites = context.newRecord(POSTSITES);
			postSites.setSiteId(record.getId());
			postSites.setPostId(postId);
			return Response.ok(postSites.store() > 0).build();
		}
	}

	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response patchHike(Hike hike)
			throws SQLException
	{
		if (postId == null || hike == null || hike.getId() == null || !Objects.equals(hike.getId(), postId) || StringUtils.isEmpty(hike.getTitle()) || StringUtils.isEmpty(hike.getContentMarkdown()) || hike.getCreatedOn() == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			// Get the posts
			PostsRecord post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId))
									  .fetchAny();

			if (post == null)
				return Response.status(Response.Status.NOT_FOUND).build();

			post.setTitle(hike.getTitle());
			post.setContentMarkdown(hike.getContentMarkdown());
			post.setVisible(hike.getVisible());
			post.setCreatedOn(hike.getCreatedOn());
			post.setEndDate(hike.getEndDate());
			return Response.ok(post.store() > 0).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			// Get the posts
			Hike post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId))
							   .and(condition)
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
			List<ImageDetails> images = context.selectFrom(IMAGE_DETAILS).where(IMAGE_DETAILS.POST_ID.eq(post.getId())).fetchInto(ImageDetails.class);
			images.forEach(i -> {
				if (!StringUtils.isEmpty(i.getImagePath()))
					i.setImagePath(i.getImagePath().substring(i.getImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
			});
			post.setImages(images);
			post.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(post.getId())).fetchInto(Postvideos.class));
			List<Field<?>> fields = new ArrayList<>();
			fields.addAll(Arrays.asList(HILLS.fields()));
			fields.add(POSTHILLS.SUCCESSFUL);
			post.setHills(context.select(fields).from(HILLS).leftJoin(POSTHILLS).on(POSTHILLS.HILL_ID.eq(HILLS.ID)).where(POSTHILLS.POST_ID.eq(post.getId())).fetchInto(PostHill.class));
			post.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(post.getId())).fetchAnyInto(Hikestats.class));
			post.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(post.getId())).fetchAnyInto(Hikeratings.class));
			post.setSites(context.select(SITES.fields()).from(SITES).leftJoin(POSTSITES).on(SITES.ID.eq(POSTSITES.SITE_ID)).where(POSTSITES.POST_ID.eq(post.getId())).fetchInto(Sites.class));

			if (!CollectionUtils.isEmpty(post.getHills()))
			{
				Map<Integer, IndividualsRecord> individuals = context.selectFrom(INDIVIDUALS).fetchMap(INDIVIDUALS.ID);
				post.getHills().forEach(h -> {
					List<HillIndividuals> inds = context.select()
														.from(HILL_INDIVIDUALS)
														.where(HILL_INDIVIDUALS.HILL_ID.eq(h.getId()))
														.fetchInto(HillIndividuals.class);

					h.setHillIndividuals(inds.stream().map(i -> {
						Individuals match = individuals.get(i.getIndividualId()).into(Individuals.class);
						match.setPhoto(null);

						return new IndividualRecord()
								.setIndividual(match)
								.setHillIndividuals(inds);
					}).collect(Collectors.toList()));
				});
			}

			return post;
		}
	}

	@GET
	@Path("/related")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			// Get the posts
			Hike post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId))
							   .and(condition)
							   .fetchAnyInto(Hike.class);

			if (post == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			List<Hike> posts = context.selectFrom(POSTS)
									  .whereExists(DSL.selectOne().from(RELATIONSHIPS).where(RELATIONSHIPS.POST_A_ID.eq(POSTS.ID).and(RELATIONSHIPS.POST_B_ID.eq(post.getId()))))
									  .orExists(DSL.selectOne().from(RELATIONSHIPS).where(RELATIONSHIPS.POST_B_ID.eq(POSTS.ID).and(RELATIONSHIPS.POST_A_ID.eq(post.getId()))))
									  .and(condition)
									  .fetchInto(Hike.class);
			posts.forEach(p -> {
				List<ImageDetails> images = context.selectFrom(IMAGE_DETAILS).where(IMAGE_DETAILS.POST_ID.eq(p.getId())).fetchInto(ImageDetails.class);
				images.forEach(i -> {
					if (!StringUtils.isEmpty(i.getImagePath()))
						i.setImagePath(i.getImagePath().substring(i.getImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
				});
				p.setImages(images);
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
	@PermitAll
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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			// Get the posts
			Hike post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId))
							   .and(condition)
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
	@PermitAll
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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).and(condition).fetchAnyInto(Posts.class);
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
	@PermitAll
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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).and(condition).fetchAnyInto(Posts.class);
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
	@PermitAll
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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).and(condition).fetchAnyInto(Posts.class);
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
