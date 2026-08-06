package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class Hill extends Hills
{
	private List<Individuals> hillIndividuals;

	public List<Individuals> getHillIndividuals()
	{
		return hillIndividuals;
	}

	public Hill setHillIndividuals(List<Individuals> hillIndividuals)
	{
		this.hillIndividuals = hillIndividuals;
		return this;
	}
}
