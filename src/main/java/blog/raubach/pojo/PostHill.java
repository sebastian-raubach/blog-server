package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.Hills;

public class PostHill extends Hills
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
