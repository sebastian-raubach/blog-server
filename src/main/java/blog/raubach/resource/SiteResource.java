package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.Sites;
import blog.raubach.database.codegen.tables.records.SitesRecord;
import blog.raubach.utils.StringUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.DSLContext;

import java.sql.*;

import static blog.raubach.database.codegen.tables.Sites.SITES;

@Path("site")
@Secured
@PermitAll
public class SiteResource extends ContextResource
{
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

			return Response.ok(context.selectFrom(SITES).fetchInto(Sites.class)).build();
		}
	}
}
