package blog.raubach.resource.base;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsitesGroundtype;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.pojo.PostRequest;
import blog.raubach.pojo.view.PostImage;
import blog.raubach.resource.BaseResource;
import blog.raubach.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import static blog.raubach.database.codegen.tables.Hikestats.HIKESTATS;
import static blog.raubach.database.codegen.tables.Posthills.POSTHILLS;
import static blog.raubach.database.codegen.tables.Posts.POSTS;
import static blog.raubach.database.codegen.tables.Postsites.POSTSITES;
import static blog.raubach.database.codegen.tables.Relationships.RELATIONSHIPS;
import static blog.raubach.database.codegen.tables.Sites.SITES;
import static blog.raubach.database.codegen.tables.Storyposts.STORYPOSTS;
import static blog.raubach.database.codegen.tables.ViewPosts.VIEW_POSTS;

@Path("post")
@Secured
public class PostResource extends BaseResource
{
	@GET
	@Path("/{postId:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getPostById(@PathParam("postId") Integer postId)
			throws SQLException
	{
		if (postId == null)
			Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectConditionStep<ViewPostsRecord> step = context.selectFrom(VIEW_POSTS)
			                                                   .where(VIEW_POSTS.POST_ID.eq(postId));

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			if (StringUtils.isEmpty(userDetails.getToken()))
				step.and(VIEW_POSTS.POST_VISIBLE.eq(true));

			// Get the posts
			ViewPosts post = setPaginationAndOrderBy(step)
					.fetchAnyInto(ViewPosts.class);

			if (post != null)
			{
				if (!CollectionUtils.isEmpty(post.getImages()))
				{
					for (PostImage i : post.getImages())
					{
						i.setImagePath(i.getImagePath().substring(i.getImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
					}
				}
			}

			return Response.ok(post).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response postPosts(PostRequest request)
			throws SQLException
	{
		processRequest(request);
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<ViewPostsRecord> step = context.selectFrom(VIEW_POSTS);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			if (StringUtils.isEmpty(userDetails.getToken()))
				step.where(VIEW_POSTS.POST_VISIBLE.eq(true));

			if (request.getPostType() != null)
				step.where(VIEW_POSTS.POST_TYPE.eq(request.getPostType()));

			if (request.getRelatedPostId() != null)
				step.whereExists(
						DSL.selectOne().from(RELATIONSHIPS)
						   .where(RELATIONSHIPS.POST_A_ID.eq(request.getRelatedPostId()).and(RELATIONSHIPS.POST_B_ID.eq(VIEW_POSTS.POST_ID)))
						   .or(RELATIONSHIPS.POST_B_ID.eq(request.getRelatedPostId()).and(RELATIONSHIPS.POST_A_ID.eq(VIEW_POSTS.POST_ID)))
				);

			if (!StringUtils.isEmpty(searchTerm))
			{
				step.where(VIEW_POSTS.POST_TITLE.containsIgnoreCase(searchTerm)
				                                .or(VIEW_POSTS.POST_CONTENT.containsIgnoreCase(searchTerm))
				                                .or(VIEW_POSTS.POST_MARKDOWN.containsIgnoreCase(searchTerm))
				                                .or(BDSL.jsonSearch(searchTerm, VIEW_POSTS.HILLS).isNotNull())
				                                .or(BDSL.jsonSearch(searchTerm, VIEW_POSTS.IMAGES).isNotNull())
				);
			}

			if (request.getSiteId() != null)
				step.whereExists(DSL.selectOne().from(POSTSITES).where(POSTSITES.POST_ID.eq(VIEW_POSTS.POST_ID).and(POSTSITES.SITE_ID.eq(request.getSiteId()))));

			if (request.getHillId() != null)
				step.whereExists(DSL.selectOne().from(POSTHILLS).where(POSTHILLS.POST_ID.eq(VIEW_POSTS.POST_ID).and(POSTHILLS.HILL_ID.eq(request.getHillId()))));

			if (request.getStoryId() != null)
				step.whereExists(DSL.selectOne().from(STORYPOSTS).where(STORYPOSTS.POST_ID.eq(VIEW_POSTS.POST_ID).and(STORYPOSTS.STORY_ID.eq(request.getStoryId()))));

			if (request.getYear() != null)
				step.where(DSL.year(VIEW_POSTS.POST_START_DATE).eq(request.getYear()));

			// Get the posts
			List<ViewPosts> posts = setPaginationAndOrderBy(step)
					.fetchInto(ViewPosts.class);

			posts.forEach(post -> {
				if (!CollectionUtils.isEmpty(post.getImages()))
				{
					for (PostImage i : post.getImages())
					{
						i.setImagePath(i.getImagePath().substring(i.getImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
					}
				}
			});

			return Response.ok(posts).build();
		}
	}

	@POST
	@Path("/{postId:\\d+}/related")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRelated(@PathParam("postId") Integer postId, List<Integer> related)
			throws SQLException
	{
		if (CollectionUtils.isEmpty(related))
			return Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).fetchAnyInto(Posts.class);

			if (post != null)
			{
				Set<Integer> reladedIdsInDb = new HashSet<>();
				context.selectFrom(RELATIONSHIPS)
				       .where(RELATIONSHIPS.POST_A_ID.in(related).and(RELATIONSHIPS.POST_B_ID.eq(VIEW_POSTS.POST_ID)))
				       .or(RELATIONSHIPS.POST_B_ID.in(related).and(RELATIONSHIPS.POST_A_ID.eq(VIEW_POSTS.POST_ID)))
				       .forEach(r -> {
						   reladedIdsInDb.add(r.getPostAId());
						   reladedIdsInDb.add(r.getPostBId());
					   });

				// Remove all those that already have relationships with this post
				related.removeAll(reladedIdsInDb);

				for (Integer other : related)
				{
					context.insertInto(RELATIONSHIPS)
					       .set(RELATIONSHIPS.POST_A_ID, postId)
					       .set(RELATIONSHIPS.POST_B_ID, other)
					       .execute();
				}
			}
		}

		return Response.ok().build();
	}

	/**
	 * Assigns an existing site with this post using the given ground type
	 *
	 * @param postId The id of the post to assign the site to
	 * @param siteId The id of the site to assign to this site
	 * @param type   The ground type
	 * @return boolean - 1=success, 0=failure
	 * @throws SQLException
	 */
	@POST
	@Path("/{postId:\\d+}/site/{siteId:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSiteWithId(@PathParam("postId") Integer postId, @PathParam("siteId") Integer siteId, PostsitesGroundtype type)
			throws SQLException
	{
		if (type == null || siteId == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			PostsRecord postsRecord = context.selectFrom(POSTS).where(POSTS.ID.eq(postId)).fetchAny();
			SitesRecord sitesRecord = context.selectFrom(SITES).where(SITES.ID.eq(siteId)).fetchAny();

			if (postsRecord == null || sitesRecord == null)
				return Response.status(Response.Status.NOT_FOUND).build();

			PostsitesRecord postSites = context.newRecord(POSTSITES);
			postSites.setSiteId(siteId);
			postSites.setPostId(postId);
			postSites.setGroundtype(type);
			return Response.ok(postSites.store() > 0).build();
		}
	}

	@GET
	@Path("/{postId:\\d+}/gpx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/gpx+xml")
	@PermitAll
	public Response getHikeGpx(@PathParam("postId") Integer postId)
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

			java.io.File mediaFolder = new java.io.File(PropertyWatcher.get("media.directory.external"));
			java.io.File gpx = new java.io.File(mediaFolder, stats.getGpxPath());

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
	@Path("/{postId:\\d+}/elevation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/tab-separated-values")
	@PermitAll
	public Response getElevationProfile(@PathParam("postId") Integer postId)
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

			java.io.File mediaFolder = new java.io.File(PropertyWatcher.get("media.directory.external"));
			String ep = stats.getElevationProfilePath();
			if (StringUtils.isEmpty(ep))
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}
			java.io.File elevation = new java.io.File(mediaFolder, ep);

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
	@Path("/{postId:\\d+}/time-distance")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/tab-separated-values")
	@PermitAll
	public Response getTimeDistanceProfile(@PathParam("postId") Integer postId)
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

			java.io.File mediaFolder = new java.io.File(PropertyWatcher.get("media.directory.external"));
			String tdp = stats.getTimeDistanceProfilePath();
			if (StringUtils.isEmpty(tdp))
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			java.io.File elevation = new File(mediaFolder, tdp);

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
