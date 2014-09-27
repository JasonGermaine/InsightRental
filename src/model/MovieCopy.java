package model;

public class MovieCopy
{

	private int num;
	private double price;
	
	public MovieCopy(int year)
	{
		if(year > 2010)
		{
			this.setPrice(20);
		}
		else
		{
			this.setPrice(10);
		}
		
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}
	
}
