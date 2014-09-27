package model;

public class Movie
{

	private int id, transCount, year, runTime;
	private double amountSales;
	private String name,genre,rating,desc;
	
	public Movie(String name, String genre, int runTime, String rating, String desc, int year)
	{
		this.setName(name);
		this.setGenre(genre);
		this.setRunTime(runTime);
		this.setRating(rating);
		this.setDesc(desc);
		this.setYear(year);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getTransCount()
	{
		return transCount;
	}

	public void setTransCount(int transCount)
	{
		this.transCount = transCount;
	}

	public int getRunTime()
	{
		return runTime;
	}

	public void setRunTime(int runTime)
	{
		this.runTime = runTime;
	}

	public double getAmountSales()
	{
		return amountSales;
	}

	public void setAmount(double amount)
	{
		this.amountSales = amount;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public String getRating()
	{
		return rating;
	}

	public void setRating(String rating)
	{
		this.rating = rating;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}
	
}
