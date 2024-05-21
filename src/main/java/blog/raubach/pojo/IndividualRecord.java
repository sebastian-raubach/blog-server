package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class IndividualRecord
{
	private Individuals           individual;
	private List<HillIndividuals> hillIndividuals;

	public Individuals getIndividual()
	{
		return individual;
	}

	public IndividualRecord setIndividual(Individuals individual)
	{
		this.individual = individual;
		return this;
	}

	public List<HillIndividuals> getHillIndividuals()
	{
		return hillIndividuals;
	}

	public IndividualRecord setHillIndividuals(List<HillIndividuals> hillIndividuals)
	{
		this.hillIndividuals = hillIndividuals;
		return this;
	}
}
