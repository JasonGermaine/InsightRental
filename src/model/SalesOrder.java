package model;


public class SalesOrder
{
	private int id;
	private double amount;
	private String date;
	
	public SalesOrder(double amount, String date)
	{
		this.setAmount(amount);
		this.setDate(date);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}
}
