package model;

public class RentalOrder
{

	private int id, days;
	private double amount;
	private String date;

	public RentalOrder(int id,double amount, String date, int days)
	{
		this.setId(id);
		this.setAmount(amount);
		this.setDate(date);
		this.setDays(days);

	}
	
	public RentalOrder(double amount, String date, int days)
	{
		this.setAmount(amount);
		this.setDate(date);
		this.setDays(days);

	}

	public String getReturnDate()
	{
		int day = 0;
		int month = 0;
		int year = 0;
		String[] parts;
		int results[];
		String returnDate;

		parts = date.split("-");
		results = new int[parts.length];
		for (int i = 0; i < parts.length; i++)
		{
			results[i] = Integer.parseInt(parts[i].trim());
		}
		month = results[0];
		day = results[1];
		year = results[2];

		int rDay = day + days;
		int rMonth = month;
		int rYear = year;
		if (month == 2)
		{
			if (rDay > 28)
			{
				rDay = rDay - 28;
				rMonth = 3;
			}
		} else if (month == 9 || month == 4 || month == 6 || month == 11)
		{
			if (rDay > 30)
			{
				rDay = rDay - 30;
				rMonth = month + 1;
			}
		} else
		{
			if (rDay > 31 && rMonth == 12)
			{
				rDay = rDay - 31;
				rMonth = 1;
				rYear = year + 1;
			} else if (rDay > 31 && rMonth != 12)
			{
				rDay = rDay - 31;
				rMonth = month + 1;
			}
		}
		returnDate = (rMonth) + "-" + rDay + "-" + rYear;
		return returnDate;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getDays()
	{
		return days;
	}

	public void setDays(int days)
	{
		this.days = days;
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
