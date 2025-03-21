package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class IndividualRecord
{
	private Individuals           individual;
	private List<PostIndividuals> postIndividuals;

	public Individuals getIndividual()
	{
		return individual;
	}

	public IndividualRecord setIndividual(Individuals individual)
	{
		this.individual = individual;
		return this;
	}

	public List<PostIndividuals> getPostIndividuals()
	{
		return postIndividuals;
	}

	public IndividualRecord setPostIndividuals(List<PostIndividuals> postIndividuals)
	{
		this.postIndividuals = postIndividuals;
		return this;
	}
}
