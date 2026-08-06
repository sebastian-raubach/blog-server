package blog.raubach.pojo;

import java.util.Map;

public class PostEditPayload
{
	private String               markdown;
	private Map<Integer, String> imageDescriptions;

	public Map<Integer, String> getImageDescriptions()
	{
		return imageDescriptions;
	}

	public PostEditPayload setImageDescriptions(Map<Integer, String> imageDescriptions)
	{
		this.imageDescriptions = imageDescriptions;
		return this;
	}

	public String getMarkdown()
	{
		return markdown;
	}

	public PostEditPayload setMarkdown(String markdown)
	{
		this.markdown = markdown;
		return this;
	}
}
