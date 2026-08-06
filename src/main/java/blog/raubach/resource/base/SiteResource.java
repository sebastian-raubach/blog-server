package blog.raubach.resource.base;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.ViewSites;
import blog.raubach.database.codegen.tables.records.SitesRecord;
import blog.raubach.resource.ContextResource;
import blog.raubach.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.DSLContext;

import java.sql.*;

import static blog.raubach.database.codegen.tables.Sites.SITES;
import static blog.raubach.database.codegen.tables.ViewSites.VIEW_SITES;

@Path("site")
@Secured
@PermitAll
public class SiteResource extends ContextResource
{
	@GET
	@Path("/{siteId:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getSiteById(@PathParam("siteId") Integer siteId)
			throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			return Response.ok(context.selectFrom(VIEW_SITES).where(VIEW_SITES.ID.eq(siteId)).fetchAnyInto(ViewSites.class)).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getSites()
			throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			return Response.ok(context.selectFrom(VIEW_SITES).fetchInto(ViewSites.class)).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSite(ViewSites site)
			throws SQLException
	{
		if (StringUtils.isEmpty(site.getName()) || site.getSitetype() == null || site.getGroundtypes() == null || CollectionUtils.isEmpty(site.getGroundtypes()) || site.getLatitude() == null || site.getLongitude() == null || site.getRating() == null || site.getFacilities() == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SitesRecord record = context.newRecord(SITES, site);
			record.store();
			return Response.ok(record.getId()).build();
		}
	}
}
