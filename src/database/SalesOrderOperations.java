package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.SalesOrder;

public class SalesOrderOperations
{

	private Connection conn = null;
	private ResultSet rset = null;
	private PreparedStatement stmt = null;
	private DBConnection db;

	public SalesOrderOperations()
	{
		db = new DBConnection();
		conn = db.openDB();
	}
	
	
	public int newSale(SalesOrder so, int id)
	{
		try
		{
			String insertString = "INSERT INTO Sales(so_id,so_amount,so_date, mem_id)" + "values(soid_seq.nextVal,?,?,?)";
			stmt = conn.prepareStatement(insertString);
	
			stmt.setDouble(1, so.getAmount());
			stmt.setString(2, so.getDate());
			stmt.setInt(3,id);
			stmt.executeUpdate();
			
			
			String sql = "SELECT COUNT(so_id) FROM Sales";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if(rset.next())
			{
				return (rset.getInt(1)-1);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}
	
	
	//Getting the sales for a certain month
	public double getSales(int month, int year)
	{
		double total = 0.0;
		try
		{
			String sql = "SELECT SUM(so_amount) FROM Sales WHERE so_date LIKE '"+month+"%' AND so_date LIKE '%" + year + "'";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if(rset.next())
			{
				total = rset.getDouble(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return total; // Returning the sales value
	}
	
	
	//Getting the total count for a certain month
	public int getCount(int month, int year)
	{
		int count = 0;
		try
		{
			String sql = "SELECT COUNT(*) FROM Sales WHERE so_date LIKE '"+month+"%' AND so_date LIKE '%" + year + "'";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if(rset.next())
			{
				count = rset.getInt(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return count;// Returning the count value
	}
}
