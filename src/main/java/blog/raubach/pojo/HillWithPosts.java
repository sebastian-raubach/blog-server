package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.Posts;

import java.util.List;

public class HillWithPosts extends Hill
{
	private List<Posts> posts;

	public List<Posts> getPosts()
	{
		return posts;
	}

	public HillWithPosts setPosts(List<Posts> posts)
	{
		this.posts = posts;
		return this;
	}
}
