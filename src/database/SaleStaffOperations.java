package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SaleStaffOperations
{
	
	private Connection conn = null;
	private ResultSet rset = null;
	private PreparedStatement stmt = null;
	private DBConnection db;
	private AdminOperations ao = new AdminOperations();

	public SaleStaffOperations()
	{
		db = new DBConnection();
		conn = db.openDB();
	}
	
	//Deleting sales staff table
	public void deleteSaleStaff(int id)
	{
		try
		{
			conn.setAutoCommit(false);
			String cmd = "SELECT user_id, staff_num FROM SaleStaff WHERE user_id = " + id;
			stmt = conn.prepareStatement(cmd, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE); // create empty SQL statement
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				rset.deleteRow(); // delete the row from the database and result set
				conn.commit();
			}
			else
			{
				ao.deleteAdmin(id);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	//Adding staff table
	public void addSaleStaff(int id)
	{
		try
		{
				String insertString = "INSERT INTO SaleStaff(user_id, staff_num)values("+id+", snum_seq.nextVal)";
				stmt = conn.prepareStatement(insertString);
				stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	
	//Finding Sales Staff
	public boolean findSaleStaff(int id)
	{
		boolean admin = true;
		try
		{
			
			conn.setAutoCommit(false);
			String cmd = "SELECT * FROM SaleStaff WHERE user_id = " + id;
			stmt = conn.prepareStatement(cmd); 
			rset = stmt.executeQuery();
			if (rset.next())
			{
				admin = false;
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

		return admin; // Returning if the user exists
	}
}
