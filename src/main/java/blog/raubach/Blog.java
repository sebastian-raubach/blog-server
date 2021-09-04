package blog.raubach;

import org.glassfish.jersey.server.ResourceConfig;

//@ApplicationPath("/api/")
public class Blog extends ResourceConfig
{
	public Blog()
	{
		// Otherwise, just load the main stuff
		packages("blog.raubach");
	}
}
