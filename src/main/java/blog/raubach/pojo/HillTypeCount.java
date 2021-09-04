package blog.raubach.pojo;

import blog.raubach.database.codegen.enums.HillsType;

public class HillTypeCount
{
	private HillsType type;
	private Integer count;

	public HillsType getType()
	{
		return type;
	}

	public HillTypeCount setType(HillsType type)
	{
		this.type = type;
		return this;
	}

	public Integer getCount()
	{
		return count;
	}

	public HillTypeCount setCount(Integer count)
	{
		this.count = count;
		return this;
	}
}
