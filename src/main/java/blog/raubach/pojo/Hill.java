package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class Hill extends Hills
{
	private List<IndividualRecord> hillIndividuals;

	public List<IndividualRecord> getHillIndividuals()
	{
		return hillIndividuals;
	}

	public Hill setHillIndividuals(List<IndividualRecord> hillIndividuals)
	{
		this.hillIndividuals = hillIndividuals;
		return this;
	}
}
