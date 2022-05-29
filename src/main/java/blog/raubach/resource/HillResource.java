package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.records.HillsRecord;
import blog.raubach.pojo.Hill;
import blog.raubach.utils.StringUtils;
import org.jooq.*;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Hills.*;
import static blog.raubach.database.codegen.tables.Posthills.*;
import static blog.raubach.database.codegen.tables.Posts.*;

@Path("hill")
@Secured
@PermitAll
public class HillResource
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

			hills.forEach(h -> h.setHikeIds(context.select(POSTS.ID).from(POSTS).leftJoin(POSTHILLS).on(POSTHILLS.POST_ID.eq(POSTS.ID)).where(POSTS.TYPE.eq(PostsType.hike)).and(POSTHILLS.HILL_ID.eq(h.getId())).fetch(POSTS.ID)));

			return hills;
		}
	}
}
