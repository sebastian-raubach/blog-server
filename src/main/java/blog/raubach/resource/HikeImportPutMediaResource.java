package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.records.*;
import blog.raubach.utils.*;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.*;
import org.jooq.DSLContext;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import static blog.raubach.database.codegen.tables.Hikestats.*;
import static blog.raubach.database.codegen.tables.Images.*;
import static blog.raubach.database.codegen.tables.Postimages.*;

@Path("post/media/{postId}")
@Secured
public class HikeImportPutMediaResource extends ContextResource
{
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean postPostMedia(@PathParam("postId") Integer postId,
								 FormDataMultiPart form)
		throws IOException, SQLException
	{
		//https://stackoverflow.com/questions/12125277/how-to-read-several-file-inputs-with-the-same-name-from-a-multipart-form-with

		List<FormDataBodyPart> images = form.getFields("image");
		List<FormDataBodyPart> imageDescriptions = form.getFields("image-description");
		List<FormDataBodyPart> imageIsPrimary = form.getFields("image-is-primary");
		List<FormDataBodyPart> gpx = form.getFields("gpx");
		List<FormDataBodyPart> elevationProfile = form.getFields("elevation-profile");
		List<FormDataBodyPart> timeDistanceProfile = form.getFields("time-distance-profile");

		if (postId == null || CollectionUtils.isEmpty(images) || CollectionUtils.isEmpty(imageDescriptions) || CollectionUtils.isEmpty(imageIsPrimary) || images.size() != imageDescriptions.size() || images.size() != imageIsPrimary.size())
		{
			resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
			return false;
		}

		String uuid = UUID.randomUUID().toString();
		File mediaFolder = new File(PropertyWatcher.get("media.directory.external"));

		File targetFolder = new File(mediaFolder, uuid);
		targetFolder.mkdirs();

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			for (int i = 0; i < images.size(); i++)
			{
				BodyPart part = images.get(i);
				String mt = part.getMediaType().toString();
				String extension = ".jpg";

				if (Objects.equals(mt, "image/png"))
					extension = ".png";

				String name = "image-" + i + extension;
				InputStream is = part.getEntityAs(InputStream.class);
				File target = new File(targetFolder, name);
				FileUtils.copyInputStreamToFile(is, target);

				ImagesRecord im = context.newRecord(IMAGES);
				im.setPath(mediaFolder.toPath().relativize(target.toPath()).toString());
				im.setCreatedOn(new Timestamp(System.currentTimeMillis())); // We're setting this to now, but it'll be read from EXIF later
				im.store();

				PostimagesRecord pim = context.newRecord(POSTIMAGES);
				pim.setImageId(im.getId());
				pim.setPostId(postId);
				pim.setDescription(imageDescriptions.get(i).getValueAs(String.class));
				pim.setIsPrimary(imageIsPrimary.get(i).getValueAs(Boolean.class));
				pim.store();
			}

			HikestatsRecord hs = context.selectFrom(HIKESTATS)
										.where(HIKESTATS.POST_ID.eq(postId))
										.fetchAny();

			if (hs != null)
			{
				if (!CollectionUtils.isEmpty(gpx))
				{
					File target = new File(targetFolder, uuid + ".gpx");
					FileUtils.copyInputStreamToFile(gpx.get(0).getEntityAs(InputStream.class), target);

					hs.setGpxPath(mediaFolder.toPath().relativize(target.toPath()).toString());
				}

				if (!CollectionUtils.isEmpty(elevationProfile))
				{
					File target = new File(targetFolder, uuid + "-elevation.tsv");
					FileUtils.copyInputStreamToFile(elevationProfile.get(0).getEntityAs(InputStream.class), target);

					hs.setElevationProfilePath(mediaFolder.toPath().relativize(target.toPath()).toString());
				}

				if (!CollectionUtils.isEmpty(timeDistanceProfile))
				{
					File target = new File(targetFolder, uuid + "-time-distance.tsv");
					FileUtils.copyInputStreamToFile(timeDistanceProfile.get(0).getEntityAs(InputStream.class), target);

					hs.setTimeDistanceProfilePath(mediaFolder.toPath().relativize(target.toPath()).toString());
				}

				hs.store();
			}
		}

		return true;
	}
}
