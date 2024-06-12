package blog.raubach.pojo;

public class SiteRating
{
	private Integer scenery;
	private Integer location;
	private Integer price;
	private Integer facilities;

	public Integer getScenery()
	{
		return scenery;
	}

	public SiteRating setScenery(Integer scenery)
	{
		this.scenery = scenery;
		return this;
	}

	public Integer getLocation()
	{
		return location;
	}

	public SiteRating setLocation(Integer location)
	{
		this.location = location;
		return this;
	}

	public Integer getPrice()
	{
		return price;
	}

	public SiteRating setPrice(Integer price)
	{
		this.price = price;
		return this;
	}

	public Integer getFacilities()
	{
		return facilities;
	}

	public SiteRating setFacilities(Integer facilities)
	{
		this.facilities = facilities;
		return this;
	}
}
