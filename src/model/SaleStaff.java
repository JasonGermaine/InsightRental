package model;

public class SaleStaff extends User
{
	
	private int staffNum;


	public SaleStaff(String fname, String lname, String dob,
			String address, String phone, String password)
	{
		super(fname, lname, dob, address, phone, password);
	}

	public int getStaffNum()
	{
		return staffNum;
	}

	public void setStaffNum(int staffNum)
	{
		this.staffNum = staffNum;
	}

}
