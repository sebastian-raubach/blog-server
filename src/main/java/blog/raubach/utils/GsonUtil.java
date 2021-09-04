package blog.raubach.utils;

import com.google.gson.*;

import java.sql.Timestamp;
import java.text.*;
import java.util.Date;

public class GsonUtil
{
	public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXX";

	private static Gson             gson;
	private static Gson             gsonExpose;
	private static SimpleDateFormat sdf;

	public static Gson getInstance()
	{
		if (gson == null)
		{
			gson = getGsonBuilderInstance(false).create();
		}
		return gson;
	}

	public static Gson getExposeInstance()
	{
		if (gsonExpose == null)
		{
			gsonExpose = getGsonBuilderInstance(true).create();
		}
		return gsonExpose;
	}

	public static Gson getInstance(boolean onlyExpose)
	{
		if (!onlyExpose)
		{
			if (gson == null)
			{
				gson = getGsonBuilderInstance(false).create();
			}
			return gson;
		}
		else
		{
			if (gsonExpose == null)
			{
				gsonExpose = getGsonBuilderInstance(true).create();
			}
			return gsonExpose;
		}
	}

	public static SimpleDateFormat getSDFInstance()
	{
		if (sdf == null)
		{
			sdf = new SimpleDateFormat(PATTERN);
		}
		return sdf;
	}

	private static GsonBuilder getGsonBuilderInstance(boolean onlyExpose)
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		if (onlyExpose)
		{
			gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		}
		gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, type, arg2) -> {
			try
			{
				return getSDFInstance().parse(json.getAsString());
			}
			catch (ParseException e)
			{
				return null;
			}
		});
		gsonBuilder.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(getSDFInstance()
			.format(src)));
		gsonBuilder.registerTypeAdapter(Timestamp.class, (JsonDeserializer<Timestamp>) (json, type, arg2) -> {
			try
			{
				return new Timestamp(getSDFInstance().parse(json.getAsString()).getTime());
			}
			catch (ParseException e)
			{
				return null;
			}
		});
		gsonBuilder.registerTypeAdapter(Timestamp.class, (JsonSerializer<Timestamp>) (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(getSDFInstance()
			.format(src)));
		return gsonBuilder;
	}

	public static <T> T fromJson(String json, Class<T> classOfT,
								 boolean onlyExpose)
	{
		try
		{
			return getInstance(onlyExpose).fromJson(json, classOfT);
		}
		catch (Exception ex)
		{
			// Log exception
			return null;
		}
	}
}