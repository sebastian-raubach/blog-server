package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.pojo.Hike;
import blog.raubach.utils.*;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.sql.*;

import static blog.raubach.database.codegen.tables.Hikeratings.*;
import static blog.raubach.database.codegen.tables.Hikestats.*;
import static blog.raubach.database.codegen.tables.Hills.*;
import static blog.raubach.database.codegen.tables.Posthills.*;
import static blog.raubach.database.codegen.tables.Postimages.*;
import static blog.raubach.database.codegen.tables.Posts.*;
import static blog.raubach.database.codegen.tables.Postvideos.*;

@Path("hike/{hikeId}")
@Secured
@PermitAll
public class HikeResource extends ContextResource
{
	@PathParam("hikeId")
	private Integer hikeId;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Hike getHike()
		throws SQLException, IOException
	{
		if (hikeId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			// Get the posts
			Hike hike = context.selectFrom(POSTS).where(POSTS.TYPE.eq(PostsType.hike))
							   .and(POSTS.ID.eq(hikeId))
							   .fetchAnyInto(Hike.class);

			if (hike == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			// Get the images and videos
			hike.setImages(context.selectFrom(POSTIMAGES).where(POSTIMAGES.POST_ID.eq(hike.getId())).fetchInto(Postimages.class));
			hike.setVideos(context.selectFrom(POSTVIDEOS).where(POSTVIDEOS.POST_ID.eq(hike.getId())).fetchInto(Postvideos.class));
			hike.setHills(context.selectFrom(HILLS).where(DSL.exists(DSL.selectFrom(POSTHILLS).where(POSTHILLS.HILL_ID.eq(HILLS.ID)).and(POSTHILLS.POST_ID.eq(hike.getId())))).fetchInto(Hills.class));
			hike.setStats(context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(hike.getId())).fetchAnyInto(Hikestats.class));
			hike.setRatings(context.selectFrom(HIKERATINGS).where(HIKERATINGS.POST_ID.eq(hike.getId())).fetchAnyInto(Hikeratings.class));

			return hike;
		}
	}

	@GET
	@Path("/gpx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/gpx+xml")
	public Response getHikeGpx()
		throws IOException, SQLException
	{
		if (hikeId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(hikeId)).fetchAnyInto(Posts.class);
			Hikestats stats = context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(hikeId)).fetchAnyInto(Hikestats.class);

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
		if (hikeId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(hikeId)).fetchAnyInto(Posts.class);
			Hikestats stats = context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(hikeId)).fetchAnyInto(Hikestats.class);

			if (post == null || stats == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			File mediaFolder = new File(PropertyWatcher.get("media.directory.external"));
			String ep = stats.getElevationProfilePath();
			if (StringUtils.isEmpty(ep)) {
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
		if (hikeId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			Posts post = context.selectFrom(POSTS).where(POSTS.ID.eq(hikeId)).fetchAnyInto(Posts.class);
			Hikestats stats = context.selectFrom(HIKESTATS).where(HIKESTATS.POST_ID.eq(hikeId)).fetchAnyInto(Hikestats.class);

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
