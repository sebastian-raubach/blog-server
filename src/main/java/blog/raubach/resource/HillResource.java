package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.*;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.pojo.*;
import blog.raubach.utils.StringUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static blog.raubach.database.codegen.tables.Hills.HILLS;
import static blog.raubach.database.codegen.tables.Individuals.INDIVIDUALS;
import static blog.raubach.database.codegen.tables.PostIndividuals.POST_INDIVIDUALS;
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
	public List<HillWithPosts> getHill(@QueryParam("name") String name)
			throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			SelectWhereStep<HillsRecord> step = context.selectFrom(HILLS);

			if (!StringUtils.isEmpty(name))
				step.where(HILLS.NAME.like("%" + name + "%"));

			List<HillWithPosts> hills = step.fetchInto(HillWithPosts.class);

			AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
			Condition condition;
			if (StringUtils.isEmpty(userDetails.getToken()))
				condition = POSTS.VISIBLE.eq(true);
			else
				condition = DSL.trueCondition();

			Map<Integer, IndividualsRecord> individuals = context.selectFrom(INDIVIDUALS).fetchMap(INDIVIDUALS.ID);

			hills.forEach(h -> {
				h.setPosts(context.select(POSTS.ID, POSTS.TITLE, POSTS.CREATED_ON)
								  .from(POSTS)
								  .leftJoin(POSTHILLS).on(POSTHILLS.POST_ID.eq(POSTS.ID))
								  .where(POSTS.TYPE.eq(PostsType.hike))
								  .and(condition)
								  .and(POSTHILLS.HILL_ID.eq(h.getId()))
								  .fetchInto(Posts.class));

				List<PostIndividuals> inds = context.select()
													.from(POST_INDIVIDUALS)
													.leftJoin(POSTS).on(POSTS.ID.eq(POST_INDIVIDUALS.POST_ID))
													.leftJoin(POSTHILLS).on(POSTHILLS.POST_ID.eq(POSTS.ID))
													.where(POSTHILLS.HILL_ID.eq(h.getId()))
													.fetchInto(PostIndividuals.class);

				h.setHillIndividuals(inds.stream().map(i -> {
					Individuals match = individuals.get(i.getIndividualId()).into(Individuals.class);
					match.setPhoto(null);

					return new IndividualRecord()
							.setIndividual(match)
							.setPostIndividuals(inds);
				}).collect(Collectors.toList()));
			});

			return hills;
		}
	}
}
