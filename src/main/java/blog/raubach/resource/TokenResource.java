/*
 * Copyright 2018 Information & Computational Sciences, The James Hutton Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blog.raubach.resource;

import blog.raubach.*;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.records.UsersRecord;
import blog.raubach.pojo.*;
import blog.raubach.utils.BCrypt;
import org.jooq.DSLContext;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

import static blog.raubach.database.codegen.tables.Users.*;

/**
 * @author Sebastian Raubach
 */
@Path("token")
public class TokenResource extends ContextResource
{
	public static Integer SALT = 10;

	@DELETE
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteToken(LoginDetails user)
		throws IOException
	{
		if (user == null)
		{
			resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
			return false;
		}

		try
		{
			// Try and see if it's a valid UUID
			UUID.fromString(user.getPassword());
			AuthenticationFilter.removeToken(user.getPassword(), req, resp);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Token postToken(LoginDetails request)
		throws IOException, SQLException
	{
		if (request == null) {
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		UsersRecord user;

		// TODO: Check credentials
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			user = context.selectFrom(USERS).where(USERS.USERNAME.eq(request.getUsername())).fetchAny();
		}

		if (user == null)
		{
			resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
			return null;
		}

		boolean canAccess = BCrypt.checkpw(request.getPassword(), user.getPassword());

		String token;
		String imageToken;

		if (canAccess)
		{
			token = UUID.randomUUID().toString();
			imageToken = UUID.randomUUID().toString();
			AuthenticationFilter.addToken(this.req, this.resp, token, imageToken, null);
		}
		else
		{
			this.resp.sendError(Response.Status.FORBIDDEN.getStatusCode());
			return null;
		}

		return new Token(token, imageToken, user.getId(), user.getUsername(), AuthenticationFilter.AGE, System.currentTimeMillis());
	}
}
