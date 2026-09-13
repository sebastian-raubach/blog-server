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

	private static final ThreadLocal<SimpleDateFormat> sdfThreadLocal =
			ThreadLocal.withInitial(() -> new SimpleDateFormat(PATTERN));

	public static SimpleDateFormat getSDFInstance()
	{
		return sdfThreadLocal.get();
	}

	private static GsonBuilder getGsonBuilderInstance(boolean onlyExpose)
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		if (onlyExpose)
		{
			gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		}

		// Flexible Date Deserializer
		gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, type, arg2) -> {
			String str = json.getAsString();
			if (str == null || str.trim().isEmpty()) {
				return null;
			}
			try
			{
				return getSDFInstance().parse(str);
			}
			catch (ParseException e)
			{
				try
				{
					// Fallback for simple "yyyy-MM-dd" format
					return new SimpleDateFormat("yyyy-MM-dd").parse(str);
				}
				catch (ParseException ex)
				{
					return null;
				}
			}
		});

		gsonBuilder.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) ->
				src == null ? null : new JsonPrimitive(getSDFInstance().format(src)));

		// Flexible Timestamp Deserializer
		gsonBuilder.registerTypeAdapter(Timestamp.class, (JsonDeserializer<Timestamp>) (json, type, arg2) -> {
			String str = json.getAsString();
			if (str == null || str.trim().isEmpty()) {
				return null;
			}
			try
			{
				return new Timestamp(getSDFInstance().parse(str).getTime());
			}
			catch (ParseException e)
			{
				try
				{
					// Fallback for simple "yyyy-MM-dd" format
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
					return new Timestamp(date.getTime());
				}
				catch (ParseException ex)
				{
					return null;
				}
			}
		});

		gsonBuilder.registerTypeAdapter(Timestamp.class, (JsonSerializer<Timestamp>) (src, typeOfSrc, context) ->
				src == null ? null : new JsonPrimitive(getSDFInstance().format(src)));

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