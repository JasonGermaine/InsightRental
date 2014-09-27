package model;


public class Member
{
	private int id, transCount, points;
	private String fname, lname, address, phone, dob;
	private double balance;

	public Member(String fname, String lname, String dob, String address, String phone)
	{
		this.setFname(fname);
		this.setLname(lname);
		this.setAddress(address);
		this.setDob(dob);
		this.setPhone(phone);
		setTransCount(0);
		setPoints(0);
		setBalance(0.0);
	}

	public Member(int id,String fname, String lname,  String dob, String address,String phone, int points, int transCount, double balance)
	{
		this.setId(id);
		this.setFname(fname);
		this.setLname(lname);
		this.setAddress(address);
		this.setDob(dob);
		this.setPhone(phone);
		this.points = points;
		this.transCount = transCount;
		this.balance = balance;
	}
	
	public String getFname()
	{
		return fname;
	}

	public void setFname(String fname)
	{
		this.fname = fname;
	}

	public String getLname()
	{
		return lname;
	}

	public void setLname(String lname)
	{
		this.lname = lname;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getDob()
	{
		return dob;
	}

	public void setDob(String dob)
	{
		this.dob = dob;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public int getTransCount()
	{
		return transCount;
	}

	public void setTransCount(int transCount)
	{
		this.transCount = transCount;
	}

	public double getBalance()
	{
		return balance;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

}
