package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.RentalOrder;

public class RentalOrderOperations
{
	private Connection conn = null;
	private ResultSet rset = null;
	private PreparedStatement stmt = null;
	private DBConnection db;

	public RentalOrderOperations()
	{
		db = new DBConnection();
		conn = db.openDB();
	}
	
	public int getRentalMember(int rid)
	{
		int id = 0;
		try
		{
			String sql = "SELECT mem_id FROM Rental WHERE ro_id =" + rid;
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				return rset.getInt(1);
			}
		}
		catch(SQLException ex)
		{
			
		}
		return id;
	}
	
	public int getRentalId(int id)
	{
		int ro_id = -1;
		try
		{
			String sql = "SELECT ro_id FROM Rental WHERE mem_id = " + id;
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				ro_id = rset.getInt(1);
				return ro_id;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return ro_id;
	}

	public boolean checkReturn(int id)
	{
		try
		{
			String sql = "SELECT * FROM Rental WHERE ro_id = " + id
					+ " AND mem_id != 0";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				return true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void returned(int id)
	{
		try
		{
			String sql = "SELECT mem_id FROM Rental WHERE ro_id = " + id;
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				rset.updateInt(1, 0);
				rset.updateRow();
				conn.commit();
				JOptionPane.showMessageDialog(null, "Rental fully returned");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public String getName(int id)
	{
		String name = null;
		try
		{
			String sql = "SELECT prod_name FROM Movie WHERE prod_id = " + id;
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				name = rset.getString(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return name;
	}

	public String getReturnDate(int id)
	{
		String date = null;
		RentalOrder ro;
		try
		{
			String sql = "SELECT ro_id, ro_amount, ro_date, ro_numDays FROM Rental WHERE ro_id = "
					+ id;
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				int ro_id = rset.getInt(1);
				double amount = rset.getDouble(2);
				String d = rset.getString(3);
				int days = rset.getInt(4);
				ro = new RentalOrder(ro_id, amount, d, days);
				date = ro.getReturnDate();
				return date;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return date;
	}

	public boolean checkRental(int id)
	{

		try
		{
			String sql = "SELECT mem_id FROM Rental WHERE ro_id != 0 AND mem_id ="
					+ id;
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				return true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	// Getting the sales for a certain month
	public double getSales(int month, int year)
	{
		double total = 0.0;
		try
		{
			String sql = "SELECT SUM(ro_amount) FROM Rental WHERE ro_date LIKE '"
					+ month + "%' AND ro_date LIKE '%" + year + "'";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				total = rset.getDouble(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return total; // Returning the sales value
	}

	// Getting the total count for a certain month
	public int getCount(int month, int year)
	{
		int count = 0;
		try
		{
			String sql = "SELECT COUNT(*) FROM Rental WHERE ro_date LIKE '"
					+ month + "%' AND ro_date LIKE '%" + year + "'";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				count = rset.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return count; // Returning the count value
	}

	public int newRental(RentalOrder ro, int id)
	{
		try
		{
			String insertString = "INSERT INTO Rental(ro_id,ro_amount,ro_date, ro_numDays, mem_id)"
					+ "values(roid_seq.nextVal,?,?,?,?)";
			stmt = conn.prepareStatement(insertString);

			stmt.setDouble(1, ro.getAmount());
			stmt.setString(2, ro.getDate());
			stmt.setInt(3, ro.getDays());
			stmt.setInt(4, id);
			stmt.executeUpdate();

			String sql = "SELECT COUNT(ro_id) FROM Rental";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				return (rset.getInt(1) - 1);
			}
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}
}
