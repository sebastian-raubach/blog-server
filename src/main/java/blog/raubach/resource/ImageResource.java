package blog.raubach.resource;

import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.records.ImagesRecord;
import blog.raubach.utils.task.ImageScalerTask;
import org.apache.commons.io.IOUtils;
import org.jooq.DSLContext;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Logger;

import static blog.raubach.database.codegen.tables.Images.*;

@Path("image/{imageId}")
public class ImageResource extends ContextResource
{
	@PathParam("imageId")
	private Integer imageId;

	@GET
	@Path("/{size}/{filename}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("image/*")
	public Response getImageWithFilename(@PathParam("size") String size)
			throws IOException, SQLException
	{
		return getImage(size);
	}

	@GET
	@Path("/{size}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("image/*")
	public Response getImage(@PathParam("size") String size)
		throws IOException, SQLException
	{
		if (imageId == null)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		ImageScalerTask.Size imageSize;
		try
		{
			imageSize = ImageScalerTask.Size.valueOf(size);
		}
		catch (IllegalArgumentException e)
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return null;
		}

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			ImagesRecord image = context.selectFrom(IMAGES).where(IMAGES.ID.eq(imageId)).fetchAny();

			if (image == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			File target = ImageScalerTask.getOrCreateThumbnail(image, imageSize);

			if (target == null)
			{
				resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
				return null;
			}

			String name = target.getName();
			String extension = name.substring(name.lastIndexOf(".") + 1, name.length() - 1).toLowerCase();

			String mediaType;

			switch (extension)
			{
				case "jpg":
				case "jpeg":
					mediaType = "image/jpeg";
					break;
				case "png":
					mediaType = "image/png";
					break;
				case "svg":
					mediaType = "image/svg+xml";
					break;
				default:
					mediaType = "image/*";
			}

			try
			{
				byte[] bytes = IOUtils.toByteArray(target.toURI());

				return Response.ok(new ByteArrayInputStream(bytes))
							   .header("Content-Type", mediaType)
							   .build();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				Logger.getLogger("").severe(e.getLocalizedMessage());
				return null;
			}
		}
	}
}
