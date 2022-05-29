package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class Hill extends Hills
{
	private List<Posts> posts;

	public List<Posts> getPosts()
	{
		return posts;
	}

	public Hill setPosts(List<Posts> posts)
	{
		this.posts = posts;
		return this;
	}
}
