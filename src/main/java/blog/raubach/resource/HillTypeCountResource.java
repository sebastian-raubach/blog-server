package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.pojo.HillTypeCount;
import blog.raubach.utils.StringUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Hills.HILLS;
import static blog.raubach.database.codegen.tables.Posthills.POSTHILLS;
import static blog.raubach.database.codegen.tables.Posts.POSTS;

@Path("hill/types")
@Secured
@PermitAll
public class HillTypeCountResource extends ContextResource
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

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			return context.selectDistinct(HILLS.TYPE, DSL.count().as("count"))
						  .from(HILLS)
						  .whereExists(DSL.selectOne().from(POSTHILLS)
										  .leftJoin(POSTS).on(POSTS.ID.eq(POSTHILLS.POST_ID))
										  .where(POSTHILLS.HILL_ID.eq(HILLS.ID))
										  .and(condition)
										  .and(POSTHILLS.SUCCESSFUL.eq(true)))
						  .groupBy(HILLS.TYPE)
						  .fetchInto(HillTypeCount.class);
		}
	}
}
