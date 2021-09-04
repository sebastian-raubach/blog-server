package blog.raubach.utils.task;

import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.records.ImagesRecord;
import blog.raubach.utils.PropertyWatcher;
import net.coobird.thumbnailator.Thumbnails;
import org.jooq.DSLContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.logging.Logger;

import static blog.raubach.database.codegen.tables.Images.*;

public class ImageScalerTask implements Runnable
{
	public static int getHeight(Size size)
	{
		Integer height = PropertyWatcher.getInteger(size.key);
		if (height == null)
			return size.fallback;
		else
			return height;
	}

	public static File getOrCreateThumbnail(ImagesRecord image, Size size)
	{
		File mediaFolder = new File(PropertyWatcher.get("media.directory.external"));
		File original = new File(mediaFolder, image.getPath());

		if (original.exists())
		{
			File target = new File(original.getParentFile(), size.name() + "-" + original.getName());

			if (!target.exists())
			{
				try
				{
					BufferedImage i = ImageIO.read(original);

					boolean widerThanHigh = i.getWidth() > i.getHeight();

					Thumbnails.Builder<File> builder = Thumbnails.of(original);

					if (widerThanHigh)
						builder = builder.height(getHeight(size));
					else
						builder = builder.width(getHeight(size));

					builder.keepAspectRatio(true)
						   .toFile(target);

					return target;
				}
				catch (IOException e)
				{
					return null;
				}
			}
			else
			{
				return target;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public void run()
	{
		try (Connection conn = Database.getConnection(true))
		{
			DSLContext context = Database.getContext(conn);

			context.selectFrom(IMAGES)
				   .stream()
				   .forEach(i -> {
					   getOrCreateThumbnail(i, Size.small);
					   getOrCreateThumbnail(i, Size.large);
				   });
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Logger.getLogger("").severe(e.getMessage());
		}
	}

	public enum Size
	{
		small("thumbnail.height.small", 640),
		large("thumbnail.height.large", 1920);

		private String key;
		private int    fallback;

		Size(String key, int fallback)
		{
			this.key = key;
		}
	}
}
