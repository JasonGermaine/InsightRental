package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class AdminOperations
{
	private Connection conn = null;
	private ResultSet rset = null;
	private PreparedStatement stmt = null;
	private DBConnection db;

	public AdminOperations()
	{
		db = new DBConnection();
		conn = db.openDB();
	}

	
	//Deleting Admin Table
	public void deleteAdmin(int id)
	{
		try
		{
			conn.setAutoCommit(false);
			String cmd = "SELECT user_id, admin_num FROM Admin WHERE user_id = " + id;
			stmt = conn.prepareStatement(cmd, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE); // create empty SQL statement
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				rset.deleteRow(); // delete the row from the database and result set
				conn.commit();
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}


	//Adding Admin Table
	public void addAdmin(int id)
	{

		try
		{
				String insertString = "INSERT INTO Admin(user_id, adminNum)values("+id+", anum_seq.nextVal)";
				stmt = conn.prepareStatement(insertString);
				stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
}
