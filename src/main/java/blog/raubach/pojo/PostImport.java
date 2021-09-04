package blog.raubach.pojo;

import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.pojos.*;

import java.sql.Timestamp;

public class PostImport
{
	private PostsType type;
	private String    title;
	private String    content;
	private Timestamp createdOn;
	private Timestamp updatedOn;

	private Hills hills[];
	private String videos[];
	private Hikestats stats;
	private Hikeratings rating;

	public PostsType getType()
	{
		return type;
	}

	public PostImport setType(PostsType type)
	{
		this.type = type;
		return this;
	}

	public String getTitle()
	{
		return title;
	}

	public PostImport setTitle(String title)
	{
		this.title = title;
		return this;
	}

	public String getContent()
	{
		return content;
	}

	public PostImport setContent(String content)
	{
		this.content = content;
		return this;
	}

	public Timestamp getCreatedOn()
	{
		return createdOn;
	}

	public PostImport setCreatedOn(Timestamp createdOn)
	{
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn()
	{
		return updatedOn;
	}

	public PostImport setUpdatedOn(Timestamp updatedOn)
	{
		this.updatedOn = updatedOn;
		return this;
	}

	public Hills[] getHills()
	{
		return hills;
	}

	public PostImport setHills(Hills[] hills)
	{
		this.hills = hills;
		return this;
	}

	public String[] getVideos()
	{
		return videos;
	}

	public PostImport setVideos(String[] videos)
	{
		this.videos = videos;
		return this;
	}

	public Hikestats getStats()
	{
		return stats;
	}

	public PostImport setStats(Hikestats stats)
	{
		this.stats = stats;
		return this;
	}

	public Hikeratings getRating()
	{
		return rating;
	}

	public PostImport setRating(Hikeratings rating)
	{
		this.rating = rating;
		return this;
	}
}
