package blog.raubach.resource;

import blog.raubach.Secured;
import blog.raubach.pojo.Settings;
import blog.raubach.utils.PropertyWatcher;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("settings")
@Secured
@PermitAll
public class SettingsResource
{
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Settings getSettings()
	{
		return new Settings()
			.setGoogleAnalyticsKey(PropertyWatcher.get("google.analytics.key"));
	}
}
