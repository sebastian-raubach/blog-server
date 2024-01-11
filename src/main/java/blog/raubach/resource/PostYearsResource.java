package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.pojo.YearCount;
import blog.raubach.utils.StringUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

import static blog.raubach.database.codegen.tables.Posts.POSTS;

@Path("post/years")
@Secured
@PermitAll
public class PostYearsResource extends ContextResource
{
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<YearCount> getPostYears(@QueryParam("postType") PostsType postType)
			throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			Field<?> year = DSL.year(POSTS.CREATED_ON).as("year");
			SelectConditionStep<?> step = context.select(year, DSL.count().as("count"))
												.from(POSTS)
												.where(POSTS.TYPE.eq(postType));

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Logger.getLogger("").info("AUTH: " + userDetails);
			if (StringUtils.isEmpty(userDetails.getToken()))
				step.and(POSTS.VISIBLE.eq(true));

			return step.groupBy(year)
					   .orderBy(year.desc())
					   .fetchInto(YearCount.class);
		}
	}
}
