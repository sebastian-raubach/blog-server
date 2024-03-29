package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.Posts;
import blog.raubach.database.codegen.tables.records.HillsRecord;
import blog.raubach.pojo.Hill;
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

@Path("hill")
@Secured
@PermitAll
public class HillResource extends ContextResource
{
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hill> getHill(@QueryParam("name") String name)
			throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<HillsRecord> step = context.selectFrom(HILLS);

			if (!StringUtils.isEmpty(name))
				step.where(HILLS.NAME.like("%" + name + "%"));

			List<Hill> hills = step.fetchInto(Hill.class);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			hills.forEach(h -> h.setPosts(context.select()
												 .from(POSTS)
												 .leftJoin(POSTHILLS).on(POSTHILLS.POST_ID.eq(POSTS.ID))
												 .where(POSTS.TYPE.eq(PostsType.hike))
												 .and(condition)
												 .and(POSTHILLS.HILL_ID.eq(h.getId()))
												 .fetchInto(Posts.class)));

			return hills;
		}
	}
}
