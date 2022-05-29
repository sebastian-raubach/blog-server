package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.pojo.HillTypeCount;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Hills.*;
import static blog.raubach.database.codegen.tables.Posthills.*;

@Path("hill/types")
@Secured
@PermitAll
public class HillTypeCountResource
{
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<HillTypeCount> getHillTypeCounts()
		throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			return context.selectDistinct(HILLS.TYPE, DSL.count().as("count"))
						  .from(HILLS)
						  .whereExists(DSL.selectOne().from(POSTHILLS)
										  .where(POSTHILLS.HILL_ID.eq(HILLS.ID))
										  .and(POSTHILLS.SUCCESSFUL.eq(true)))
						  .groupBy(HILLS.TYPE)
						  .fetchInto(HillTypeCount.class);
		}
	}
}
