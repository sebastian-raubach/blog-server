package blog.raubach.pojo;

import blog.raubach.database.codegen.tables.pojos.*;

import java.util.List;

public class Hike extends Post
{
	private List<Hills> hills;
	private Hikestats stats;
	private Hikeratings ratings;

	public List<Hills> getHills()
	{
		return hills;
	}

	public Hike setHills(List<Hills> hills)
	{
		this.hills = hills;
		return this;
	}

	public Hikestats getStats()
	{
		return stats;
	}

	public Hike setStats(Hikestats stats)
	{
		this.stats = stats;
		return this;
	}

	public Hikeratings getRatings()
	{
		return ratings;
	}

	public Hike setRatings(Hikeratings ratings)
	{
		this.ratings = ratings;
		return this;
	}
}
