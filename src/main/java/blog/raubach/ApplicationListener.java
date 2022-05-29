package blog.raubach;

import blog.raubach.utils.PropertyWatcher;
import blog.raubach.utils.task.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.*;

/**
 * The {@link ApplicationListener} is the main {@link ServletContextListener} of the application. It's started when the application is loaded by
 * Tomcat. It contains {@link #contextInitialized(ServletContextEvent)} which is executed on start and {@link #contextDestroyed(ServletContextEvent)}
 * which is executed when the application terminates.
 *
 * @author Sebastian Raubach
 */
@WebListener
public class ApplicationListener implements ServletContextListener
{
	private static ScheduledExecutorService backgroundScheduler;

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		PropertyWatcher.initialize();

		backgroundScheduler = Executors.newSingleThreadScheduledExecutor();
		backgroundScheduler.scheduleAtFixedRate(new ExifScannerTask(), 0, 12, TimeUnit.HOURS);
		backgroundScheduler.scheduleAtFixedRate(new ImageScalerTask(), 0, 12, TimeUnit.HOURS);
		backgroundScheduler.scheduleAtFixedRate(new GoogleElevationTask(), 0, 12, TimeUnit.HOURS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		PropertyWatcher.stopFileWatcher();

		try
		{
			// Stop the scheduler
			if (backgroundScheduler != null)
				backgroundScheduler.shutdownNow();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
