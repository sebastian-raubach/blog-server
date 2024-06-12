package blog.raubach.pojo;

public class SiteFacilities
{
	private Boolean toilets;
	private Boolean showers;
	private Boolean shop;
	private Boolean restaurant;
	private Boolean cafe;
	private Boolean electricHookup;
	private Boolean localDogWalk;

	public Boolean getToilets()
	{
		return toilets;
	}

	public SiteFacilities setToilets(Boolean toilets)
	{
		this.toilets = toilets;
		return this;
	}

	public Boolean getShowers()
	{
		return showers;
	}

	public SiteFacilities setShowers(Boolean showers)
	{
		this.showers = showers;
		return this;
	}

	public Boolean getShop()
	{
		return shop;
	}

	public SiteFacilities setShop(Boolean shop)
	{
		this.shop = shop;
		return this;
	}

	public Boolean getRestaurant()
	{
		return restaurant;
	}

	public SiteFacilities setRestaurant(Boolean restaurant)
	{
		this.restaurant = restaurant;
		return this;
	}

	public Boolean getCafe()
	{
		return cafe;
	}

	public SiteFacilities setCafe(Boolean cafe)
	{
		this.cafe = cafe;
		return this;
	}

	public Boolean getElectricHookup()
	{
		return electricHookup;
	}

	public SiteFacilities setElectricHookup(Boolean electricHookup)
	{
		this.electricHookup = electricHookup;
		return this;
	}

	public Boolean getLocalDogWalk()
	{
		return localDogWalk;
	}

	public SiteFacilities setLocalDogWalk(Boolean localDogWalk)
	{
		this.localDogWalk = localDogWalk;
		return this;
	}
}
