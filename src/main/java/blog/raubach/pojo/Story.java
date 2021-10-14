package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class Story extends Stories
{
	private List<Hike> posts;

	public List<Hike> getPosts()
	{
		return posts;
	}

	public Story setPosts(List<Hike> posts)
	{
		this.posts = posts;
		return this;
	}
}
