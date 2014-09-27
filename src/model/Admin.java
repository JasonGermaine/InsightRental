package model;


public class Admin extends User
{
	private int adminNum;
	
	
	public Admin(String fname, String lname, String dob,String address, String phone, String password)
	{
		super(fname, lname, dob, address, phone, password);
	}

	public int getAdminNum()
	{
		return adminNum;
	}

	public void setAdminNum(int adminNum)
	{
		this.adminNum = adminNum;
	}
}
