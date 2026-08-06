package blog.raubach.utils.task;

import blog.raubach.database.Database;
import blog.raubach.database.codegen.tables.records.HikestatsRecord;
import blog.raubach.utils.*;
import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import io.jenetics.jpx.*;
import org.jooq.*;

import java.io.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static blog.raubach.database.codegen.tables.Hikestats.HIKESTATS;

public class GoogleElevationTask implements Runnable
{
	private Integer postId = null;

	public GoogleElevationTask()
	{
	}

	public GoogleElevationTask(Integer postId)
	{
		this.postId = postId;
	}

	public static void main(String[] args)
	{
		Database.init("localhost", "blog", null, "root", null);

		new GoogleElevationTask(304).run();
	}

	public static ElevationResult[] fetchElevationsInBatches(GeoApiContext gContext, LatLng[] latLngs)
			throws IOException, InterruptedException, ApiException
	{
		int batchSize = 500; // Keep slightly under the 512 limit
		List<ElevationResult> allResults = new ArrayList<>();

		for (int i = 0; i < latLngs.length; i += batchSize)
		{
			int end = Math.min(i + batchSize, latLngs.length);
			LatLng[] batch = Arrays.copyOfRange(latLngs, i, end);

			ElevationResult[] batchResults = ElevationApi.getByPoints(gContext, batch).await();
			if (batchResults != null)
			{
				allResults.addAll(Arrays.asList(batchResults));
			}
		}

		return allResults.toArray(new ElevationResult[0]);
	}

	@Override
	public void run()
	{
		File mediaFolder = new File(PropertyWatcher.get("media.directory.external"));

		Logger.getLogger("").info("RUNNING GOOGLE ELEVATION TASK");

		GeoApiContext gContext = new GeoApiContext.Builder()
				.apiKey(PropertyWatcher.get("google.elevation.api.key"))
				.build();

		try (Connection conn = Database.getConnection(true))
		{
			DSLContext context = Database.getContext(conn);

			SelectConditionStep<HikestatsRecord> step = context.selectFrom(HIKESTATS)
			                                                   .where(HIKESTATS.GPX_PATH.isNotNull())
			                                                   .and(HIKESTATS.ELEVATION_PROFILE_PATH.isNull().or(HIKESTATS.TIME_DISTANCE_PROFILE_PATH.isNull()));

			if (this.postId != null)
				step = step.and(HIKESTATS.POST_ID.eq(postId));

			step.forEach(hs -> {
				Logger.getLogger("").info("RUNNING GOOGLE ELEVATION TASK FOR POST: " + hs.getPostId());
				File gpx = new File(mediaFolder, hs.getGpxPath());

				if (gpx.exists() && gpx.isFile())
				{
					try
					{
						// Read the GPX file
						GPX data = GPX.read(gpx.getPath());

						// Extract all the points
						List<Point> points = new ArrayList<>();
						if (!data.getTracks().isEmpty())
						{
							points = data.tracks()
							             .flatMap(Track::segments)
							             .flatMap(TrackSegment::points)
							             .collect(Collectors.toList());
						}
						else if (!data.getRoutes().isEmpty())
						{
							points = data.routes()
							             .flatMap(Route::points)
							             .collect(Collectors.toList());
						}

						if (!CollectionUtils.isEmpty(points))
						{
							boolean hasElevation = true;

							for (Point point : points)
							{
								if (point.getElevation().isEmpty())
								{
									hasElevation = false;
									break;
								}
							}

							ElevationResult[] result;

							if (!hasElevation)
							{
								// Map them to Google Maps LatLng objects
								LatLng[] latLngs = points.stream().map(p -> new LatLng(p.getLatitude().doubleValue(), p.getLongitude().doubleValue())).toArray(LatLng[]::new);

								// Fetch their elevations
								result = fetchElevationsInBatches(gContext, latLngs);
							}
							else
							{
								result = points.stream().map(p -> {
									ElevationResult er = new ElevationResult();
									er.location = new LatLng(p.getLatitude().doubleValue(), p.getLongitude().doubleValue());
									er.elevation = p.getElevation().get().doubleValue();
									return er;
								}).toArray(ElevationResult[]::new);
							}

							String uuid = gpx.getParentFile().getName();

							double totalAscent = 0;
							// Write the outputs
							File elevation = new File(gpx.getParentFile(), uuid + "-elevation.tsv");
							File timeDistance = new File(gpx.getParentFile(), uuid + "-time-distance.tsv");
							try (BufferedWriter ew = new BufferedWriter(new FileWriter(elevation, StandardCharsets.UTF_8));
							     BufferedWriter tdw = new BufferedWriter(new FileWriter(timeDistance, StandardCharsets.UTF_8)))
							{
								ew.write("distance\televation");
								ew.newLine();
								tdw.write("time\tdistance");
								tdw.newLine();

								double accu = 0;
								double tdAccu = 0;
								int lastTime = -1;

								long startTime = getTime(points.get(0));
								for (int i = 1; i < points.size(); i++)
								{
									Point prev = points.get(i - 1);
									Point curr = points.get(i);
									double haversine = getHaversine(prev, curr);
									accu += haversine;
									tdAccu += haversine;

									totalAscent += Math.max(0, result[i].elevation - result[i - 1].elevation);

									ew.write(accu + "\t" + result[i].elevation);
									ew.newLine();

									long time = getTime(curr);
									int tdSeconds = getTimeDistance(time, startTime);

									if (tdSeconds > lastTime)
									{
										lastTime = tdSeconds;
										tdw.write(tdSeconds + "\t" + tdAccu);
										tdw.newLine();
									}
								}
							}

							hs.setAscent(totalAscent);
							hs.setElevationProfilePath(mediaFolder.toPath().relativize(elevation.toPath()).toString());
							hs.setTimeDistanceProfilePath(mediaFolder.toPath().relativize(timeDistance.toPath()).toString());
							hs.store();
						}
					}
					catch (IOException | InterruptedException | ApiException e)
					{
						e.printStackTrace();
						Logger.getLogger("").severe(e.getMessage());
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

	private long getTime(Point point)
	{
		ZonedDateTime d = point.getTime().orElse(null);

		if (d != null)
			return d.toEpochSecond();
		else
			return 0;
	}

	private int getTimeDistance(long one, long two)
	{
		return (int) Math.floor(Math.abs(two - one) / 60.0);
	}

	private double getHaversine(Point one, Point two)
	{
		double r = 6371.0;

		double dLat = toRadians(two.getLatitude().doubleValue() - one.getLatitude().doubleValue());
		double dLng = toRadians(two.getLongitude().doubleValue() - one.getLongitude().doubleValue());

		double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) + Math.cos(toRadians(one.getLatitude().doubleValue())) * Math.cos(toRadians(two.getLatitude().doubleValue())) * Math.sin(dLng / 2.0) * Math.sin(dLng / 2.0);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return r * c;
	}

	private double toRadians(double value)
	{
		return value * Math.PI / 180.0;
	}
}
