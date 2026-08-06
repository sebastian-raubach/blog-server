package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class Story extends Stories
{
	private List<MiniPost> posts;

	public List<MiniPost> getPosts()
	{
		return posts;
	}

	public Story setPosts(List<MiniPost> posts)
	{
		this.posts = posts;
		return this;
	}
}
