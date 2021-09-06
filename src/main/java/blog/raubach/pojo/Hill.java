package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.Hills;

import java.util.List;

public class Hill extends Hills
{
	private List<Integer> hikeIds;

	public List<Integer> getHikeIds()
	{
		return hikeIds;
	}

	public Hill setHikeIds(List<Integer> hikeIds)
	{
		this.hikeIds = hikeIds;
		return this;
	}
}
