package blog.raubach.resource.base;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.ViewHills;
import blog.raubach.database.codegen.tables.records.ViewHillsRecord;
import blog.raubach.pojo.*;
import blog.raubach.resource.BaseResource;
import blog.raubach.utils.StringUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.nio.file.FileSystems;
import java.sql.*;
import java.util.*;

import static blog.raubach.database.codegen.tables.Posthills.POSTHILLS;
import static blog.raubach.database.codegen.tables.ViewHills.VIEW_HILLS;

@Path("hill")
@Secured
@PermitAll
public class HillResource extends BaseResource
{
	@GET
	@Path("/{hillId:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHillById(@PathParam("hillId") Integer hillId)
			throws SQLException
	{
		if (hillId == null)
			Response.status(Response.Status.BAD_REQUEST).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectConditionStep<ViewHillsRecord> step = context.selectFrom(VIEW_HILLS)
			                                                   .where(VIEW_HILLS.HILL_ID.eq(hillId));

			// Get the hills
			ViewHills hill = setPaginationAndOrderBy(step)
					.fetchAnyInto(ViewHills.class);

			if (hill != null)
			{
				AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
				if (StringUtils.isEmpty(userDetails.getToken()))
				{
					// Filter to visible posts
					hill.setPosts(Arrays.stream(hill.getPosts())
					                    .filter(p -> p.getVisible() == 1)
					                    .toArray(MiniPost[]::new));

					Arrays.stream(hill.getPosts())
					      .forEach(p -> {
							  p.setPrimaryImagePath(p.getPrimaryImagePath().substring(p.getPrimaryImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
						  });
				}
			}

			return Response.ok(hill).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postHills(HillRequest request)
			throws SQLException
	{
		processRequest(request);
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<ViewHillsRecord> step = context.selectFrom(VIEW_HILLS);

			if (!StringUtils.isEmpty(request.getHillName()))
				step.where(DSL.lower(VIEW_HILLS.HILL_NAME).contains(request.getHillName().toLowerCase()));
			if (request.getPostId() != null)
				step.whereExists(DSL.selectOne().from(POSTHILLS).where(POSTHILLS.POST_ID.eq(request.getPostId()).and(POSTHILLS.HILL_ID.eq(VIEW_HILLS.HILL_ID))));

			// Get the hills
			List<ViewHills> hills = setPaginationAndOrderBy(step)
					.fetchInto(ViewHills.class);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			if (StringUtils.isEmpty(userDetails.getToken()))
			{
				// Filter to visible posts
				hills.forEach(h -> {
					h.setPosts(Arrays.stream(h.getPosts())
					                 .filter(p -> p.getVisible() == 1)
					                 .toArray(MiniPost[]::new));

					Arrays.stream(h.getPosts())
					      .forEach(p -> {
							  p.setPrimaryImagePath(p.getPrimaryImagePath().substring(p.getPrimaryImagePath().lastIndexOf(FileSystems.getDefault().getSeparator()) + 1));
						  });
				});
			}

			return Response.ok(hills).build();
		}
	}
}
