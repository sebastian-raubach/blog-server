package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.pojos.Individuals;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.*;
import org.jooq.DSLContext;

import java.io.*;
import java.sql.*;
import java.util.List;

import static blog.raubach.database.codegen.tables.Individuals.INDIVIDUALS;
import static blog.raubach.database.codegen.tables.Users.USERS;

@Path("individual")
@Secured
public class IndividualResource
{
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getIndividuals()
			throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			List<Individuals> result = context.selectFrom(INDIVIDUALS).fetchInto(Individuals.class);

			if (!CollectionUtils.isEmpty(result))
			{
				result.forEach(i -> {
					i.setPhoto(null);
				});
			}

			return Response.ok(result).build();
		}
	}

	@GET
	@Path("/{individualId:\\d+}/img")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({"image/png", "image/jpeg", "image/*"})
	@PermitAll
	public Response getParticipantImage(@PathParam("individualId") Integer individualId)
			throws SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			IndividualsRecord individual = context.selectFrom(INDIVIDUALS).where(INDIVIDUALS.ID.eq(individualId)).fetchAny();

			if (individual == null || individual.getPhoto() == null)
				return Response.status(Response.Status.NOT_FOUND).build();
			else
				return Response.ok((StreamingOutput) output -> {
								   output.write(individual.getPhoto());
								   output.flush();
							   })
							   .type("image/png")
							   .build();
		}
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postInvididual(@FormDataParam("name") String name, @FormDataParam("userId") Integer userId, @FormDataParam("image") InputStream fileIs, @FormDataParam("image") FormDataContentDisposition fileDetails)
			throws IOException, SQLException
	{
		if (StringUtils.isEmpty(name))
			return Response.status(Response.Status.BAD_REQUEST).build();

		if (fileDetails != null && fileDetails.getSize() >= 4194304)
			return Response.status(Response.Status.REQUEST_ENTITY_TOO_LARGE).build();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			if (userId != null)
			{
				UsersRecord usersRecord = context.selectFrom(USERS).where(USERS.ID.eq(userId)).fetchAny();

				if (usersRecord == null)
				{
					return Response.status(Response.Status.NOT_FOUND).build();
				}
			}

			IndividualsRecord record = context.newRecord(INDIVIDUALS);
			record.setName(name);
			record.setPhoto(IOUtils.toByteArray(fileIs));
			record.setUserId(userId);
			record.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			record.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			return Response.ok(record.store() > 0).build();
		}
	}
}
