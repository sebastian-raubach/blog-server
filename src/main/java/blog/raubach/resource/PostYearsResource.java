package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.pojo.YearCount;
import org.jooq.*;
import org.jooq.impl.DSL;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Posts.*;

@Path("post/years")
@Secured
@PermitAll
public class PostYearsResource
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
			return context.select(year, DSL.count().as("count"))
						  .from(POSTS)
						  .where(POSTS.TYPE.eq(postType))
						  .groupBy(year)
						  .orderBy(year.desc())
						  .fetchInto(YearCount.class);
		}
	}
}
