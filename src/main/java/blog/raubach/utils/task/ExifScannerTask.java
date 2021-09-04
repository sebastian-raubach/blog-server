package blog.raubach.utils.task;

import blog.raubach.database.Database;
import blog.raubach.utils.PropertyWatcher;
import com.drew.imaging.*;
import com.drew.lang.GeoLocation;
import com.drew.metadata.*;
import com.drew.metadata.exif.GpsDirectory;
import org.jooq.DSLContext;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.logging.Logger;

import static blog.raubach.database.codegen.tables.Images.*;

public class ExifScannerTask implements Runnable
{
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

	@Override
	public void run()
	{
		File mediaFolder = new File(PropertyWatcher.get("media.directory.external"));

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);

			context.selectFrom(IMAGES)
				   .where(IMAGES.LATITUDE.isNull())
				   .forEach(i -> {
					   File image = new File(mediaFolder, i.getPath());

					   if (image.exists() && image.isFile())
					   {
						   try
						   {
							   Exif exif = getExif(image);

							   if (exif.getGpsLatitude() != null)
								   i.setLatitude(exif.getGpsLatitude());
							   if (exif.getGpsLongitude() != null)
								   i.setLongitude(exif.getGpsLongitude());

							   if (exif.getDateTimeOriginal() != null)
								   i.setCreatedOn(new Timestamp(exif.getDateTimeOriginal().getTime()));
							   else if (exif.getDateTime() != null)
								   i.setCreatedOn(new Timestamp(exif.getDateTime().getTime()));
							   else if (exif.getDateTimeDigitized() != null)
								   i.setCreatedOn(new Timestamp(exif.getDateTimeDigitized().getTime()));

							   if (i.changed())
								   i.store();
						   }
						   catch (ImageProcessingException | IOException e)
						   {
							   e.printStackTrace();
						   }
					   }
				   });
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Logger.getLogger("").severe(e.getMessage());
		}
	}

	private synchronized Exif getExif(File image)
		throws ImageProcessingException, IOException
	{
		Metadata metadata = ImageMetadataReader.readMetadata(image);

		Exif exif = new Exif();

		// See whether it has GPS data
		Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
		for (GpsDirectory gpsDirectory : gpsDirectories)
		{
			// Try to read out the location, making sure it's non-zero
			GeoLocation geoLocation = gpsDirectory.getGeoLocation();
			if (geoLocation != null && !geoLocation.isZero())
			{
				// Add to our collection for use below
				exif.setGpsLatitude(geoLocation.getLatitude())
					.setGpsLongitude(geoLocation.getLongitude());
				break;
			}
		}

		Iterable<Directory> directories = metadata.getDirectories();
		Iterator<Directory> iterator = directories.iterator();
		while (iterator.hasNext())
		{
			Directory dir = iterator.next();

			if (dir.getName().contains("PrintIM"))
				continue;

			Collection<com.drew.metadata.Tag> tags = dir.getTags();
			for (Tag tag : tags)
			{
				try
				{
					switch (tag.getTagType())
					{
						case 0x9003:
							exif.setDateTimeOriginal(sdf.parse(tag.getDescription()));
							break;
						case 0x0132:
							exif.setDateTime(sdf.parse(tag.getDescription()));
							break;
						case 0x9004:
							exif.setDateTimeDigitized(sdf.parse(tag.getDescription()));
							break;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return exif;
	}

	private static class Exif
	{
		private Double gpsAltitude;
		private Double gpsLatitude;
		private Double gpsLongitude;
		private Date   dateTime;
		private Date   dateTimeOriginal;
		private Date   dateTimeDigitized;

		public Double getGpsAltitude()
		{
			return gpsAltitude;
		}

		public Exif setGpsAltitude(Double gpsAltitude)
		{
			this.gpsAltitude = gpsAltitude;
			return this;
		}

		public Double getGpsLatitude()
		{
			return gpsLatitude;
		}

		public Exif setGpsLatitude(Double gpsLatitude)
		{
			this.gpsLatitude = gpsLatitude;
			return this;
		}

		public Double getGpsLongitude()
		{
			return gpsLongitude;
		}

		public Exif setGpsLongitude(Double gpsLongitude)
		{
			this.gpsLongitude = gpsLongitude;
			return this;
		}

		public Date getDateTime()
		{
			return dateTime;
		}

		public Exif setDateTime(Date dateTime)
		{
			this.dateTime = dateTime;
			return this;
		}

		public Date getDateTimeOriginal()
		{
			return dateTimeOriginal;
		}

		public Exif setDateTimeOriginal(Date dateTimeOriginal)
		{
			this.dateTimeOriginal = dateTimeOriginal;
			return this;
		}

		public Date getDateTimeDigitized()
		{
			return dateTimeDigitized;
		}

		public Exif setDateTimeDigitized(Date dateTimeDigitized)
		{
			this.dateTimeDigitized = dateTimeDigitized;
			return this;
		}
	}
}
