package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class Post extends Posts
{
	private List<ImageDetails> images;
	private List<Postvideos>   videos;

	public List<ImageDetails> getImages()
	{
		return images;
	}

	public Post setImages(List<ImageDetails> images)
	{
		this.images = images;
		return this;
	}

	public List<Postvideos> getVideos()
	{
		return videos;
	}

	public Post setVideos(List<Postvideos> videos)
	{
		this.videos = videos;
		return this;
	}
}
