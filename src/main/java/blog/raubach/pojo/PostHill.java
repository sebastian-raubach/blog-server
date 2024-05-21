package blog.raubach.pojo;

public class PostHill extends Hill
{
	private boolean successful;

	public boolean isSuccessful()
	{
		return successful;
	}

	public PostHill setSuccessful(boolean successful)
	{
		this.successful = successful;
		return this;
	}
}
