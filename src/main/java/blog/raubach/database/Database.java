package blog.raubach.database;

import blog.raubach.database.codegen.BlogDb;
import blog.raubach.utils.StringUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.jooq.*;
import org.jooq.conf.*;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.TimeZone;
import java.util.logging.*;

/**
 * @author Sebastian Raubach
 */
public class Database
{
	private static String databaseServer;
	private static String databaseName;
	private static String databasePort;
	private static String username;
	private static String password;

	private static final String utc = TimeZone.getDefault().getID();

	public static void init(String databaseServer, String databaseName, String databasePort, String username, String password)
	{
		Database.databaseServer = databaseServer;
		Database.databaseName = databaseName;
		Database.databasePort = databasePort;
		Database.username = username;
		Database.password = password;

		try
		{
			// The newInstance() call is a work around for some
			// broken Java implementations
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		}
		catch (Exception ex)
		{
			// handle the error
		}

		// Get an initial connection to try if it works. Attempt a connection 10 times before failing
		boolean connectionSuccessful = false;
		for (int attempt = 0; attempt < 10; attempt++)
		{
			try (Connection conn = getConnection())
			{
				Database.getContext(conn);
				connectionSuccessful = true;
				break;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				Logger.getLogger("").severe(e.getLocalizedMessage());

				// If the attempt fails, wait 5 seconds before the next one
				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		}

		if (!connectionSuccessful)
			throw new RuntimeException("Unable to connect to database after 10 attempts. Exiting.");

		// Run database updates
		try
		{
			Logger.getLogger("").log(Level.INFO, "RUNNING FLYWAY on: " + databaseName);
			Flyway flyway = Flyway.configure()
								  .table("schema_version")
								  .validateOnMigrate(false)
								  .dataSource(getDatabaseUrl(false), username, password)
								  .locations("classpath:blog/raubach/utils/database/migration")
								  .baselineOnMigrate(true)
								  .load();
			flyway.migrate();
			flyway.repair();
		}
		catch (FlywayException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Creates and returns the database connection string
	 *
	 * @param allowStreaming Should streaming of results be allowed?
	 * @return
	 */
	private static String getDatabaseUrl(boolean allowStreaming)
	{
		return "jdbc:mysql://"
			+ databaseServer
			+ ":"
			+ (StringUtils.isEmptyOrQuotes(databasePort) ? "3306" : databasePort)
			+ "/"
			+ databaseName
			+ "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone="
			+ utc
			+ (allowStreaming ? "&useCursorFetch=true" : "");
	}

	/**
	 * Get a database connection
	 *
	 * @return The {@link Connection} to the database
	 * @throws SQLException thrown if the database interaction fails
	 */
	public static Connection getConnection()
		throws SQLException
	{
		return getConnection(false);
	}

	/**
	 * Get a database connection
	 *
	 * @param allowStreaming Should streaming of results be allowed?
	 * @return The {@link Connection} to the database
	 * @throws SQLException thrown if the database interaction fails
	 */
	public static Connection getConnection(boolean allowStreaming)
		throws SQLException
	{
		return DriverManager.getConnection(getDatabaseUrl(allowStreaming), username, password);
	}

	public static DSLContext getContext(Connection connection)
	{
		Settings settings = new Settings()
			.withRenderMapping(new RenderMapping()
				.withSchemata(
					new MappedSchema().withInput(BlogDb.BLOG_DB.getQualifiedName().first())
									  .withOutput(databaseName)));

		return DSL.using(connection, SQLDialect.MYSQL, settings);
	}
}