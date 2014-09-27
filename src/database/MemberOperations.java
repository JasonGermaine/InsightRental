package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Member;

public class MemberOperations
{

	private Connection conn = null;
	private ResultSet rset = null;
	private PreparedStatement stmt = null;
	private DBConnection db;

	public MemberOperations()
	{
		db = new DBConnection();
		conn = db.openDB();
	}

	
	// Adding Member Table
	public int addMember(Member m)
	{

		int num = 0; // variable to store the user id
		try
		{
			// Checking if last name and phone number already exist
			String queryString = "SELECT * FROM Member WHERE mem_lname like '"
					+ m.getLname() + "' AND mem_phoneNum like '"
					+ m.getPhone() + "'";
			stmt = conn.prepareStatement(queryString);
			rset = stmt.executeQuery(queryString);
			if (rset.next())
			{
				return num;
			} else
			{
				String sql = "INSERT INTO Member(mem_id,mem_fname,mem_lname,mem_dob,"
						+ "mem_address,mem_phoneNum,mem_loyaltyPts,mem_transCount,mem_balance)"
						+ " values(mid_seq.nextVal,?,?,?,?,?,?,?,?)";

				// Create a Prepared statement
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, m.getFname());
				stmt.setString(2, m.getLname());
				stmt.setString(3, m.getDob());
				stmt.setString(4, m.getAddress());
				stmt.setString(5, m.getPhone());
				stmt.setInt(6, m.getPoints());
				stmt.setInt(7, m.getTransCount());
				stmt.setDouble(8, m.getBalance());

				stmt.executeUpdate();
			}
			queryString = "SELECT mem_id FROM Member WHERE mem_lname like '"
					+ m.getLname() + "' AND mem_phoneNum like '"
					+ m.getPhone() + "'";
			stmt = conn.prepareStatement(queryString);
			rset = stmt.executeQuery(queryString);
			if (rset.next())
			{
				num = rset.getInt(1); // Getting the member id
			}
		} catch (Exception se)
		{
			System.out.println(se);
		}

		return num; // Returning user id so it can be printed in a message
	}


	public void updateTrans(Member m)
	{
		try
		{
			String sql = "SELECT mem_transCount FROM Member WHERE mem_id =" + m.getId();
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				rset.updateInt(1, (rset.getInt(1)+1) );
				rset.updateRow();
				conn.commit();
			}
		}
		catch(SQLException ex)
		{
			
		}
	}
	
	public Member findMember(String name, String number)
	{
		Member m = null;
		try
		{
			String sql = "SELECT * FROM Member WHERE mem_lname like '" + name	+ "' AND mem_phoneNum like '" + number + "'";

			// Create a prepared statement
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery(sql);

			int id = 0;
			String fname = "";
			String lname = "";
			String dob = "";
			String address = "";
			String pnumber = "";
			int points = 0;
			int transCount = 0;
			double balance = 0;

			if (rset.next())
			{
				// Assigning table values to the variables
				id = rset.getInt(1);
				fname = rset.getString(2);
				lname = rset.getString(3);
				dob = rset.getString(4);
				address = rset.getString(5);
				pnumber = rset.getString(6);
				points = rset.getInt(7);
				transCount = rset.getInt(8);
				balance = rset.getDouble(9);

				// Create a Member object
				m = new Member(id, fname, lname, dob, address, pnumber, points,
						transCount, balance);
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}

		return m; // Returning the Member Object
	}

	// Searching for a member using member id
	public Member searchID(int mID)
	{
		Member m = null;
		try
		{
			String sql = "SELECT * FROM Member WHERE mem_id = " + mID;

			// Create a prepared statement
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery(sql);

			int id = 0;
			String fname = "";
			String lname = "";
			String dob = "";
			String address = "";
			String pnumber = "";
			int points = 0;
			int transCount = 0;
			double balance = 0;

			if (rset.next())
			{
				// Assigning table values to the variables
				id = rset.getInt(1);
				fname = rset.getString(2);
				lname = rset.getString(3);
				dob = rset.getString(4);
				address = rset.getString(5);
				pnumber = rset.getString(6);
				points = rset.getInt(7);
				transCount = rset.getInt(8);
				balance = rset.getDouble(9);

				// Create a Member object
				m = new Member(id, fname, lname, dob, address, pnumber, points,
						transCount, balance);
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}

		return m;
	}

	// Finding the best member
	public Member getBest()
	{
		int id = 0;
		int count = 0;
		Member m = null;
		try
		{
			String sql = "SELECT mem_transCount, mem_id FROM Member WHERE mem_id != 0";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			while (rset.next())
			{
				if (rset.getInt(1) > count) // Finding the highest transaction
											// count
				{
					count = rset.getInt(1);
					id = rset.getInt(2);
				}
			}

			m = searchID(id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return m;
	}

	public void updatePoints(Member m,int amount)
	{
		try
		{
			String sql = "SELECT mem_loyaltyPts, mem_balance FROM Member WHERE mem_id =" + m.getId();
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				int curPts = rset.getInt(1);
				int newPts = curPts + 100;
				if( newPts >= 1000)
				{
					newPts = newPts - 1000;
					double newBal = rset.getDouble(2) + 5.0;
					rset.updateDouble(2,newBal);
				}
				rset.updateInt(1, newPts);
				rset.updateRow();
				conn.commit();
			}
		}
		catch(SQLException ex)
		{
			
		}
	}

	public void updateDetails(int id, String add, String ph)
	{
		try
		{
			String sql = "SELECT mem_address, mem_phoneNum FROM Member WHERE mem_id =" + id;
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				rset.updateString(1, add);
				rset.updateString(2, ph);
				rset.updateRow();
				conn.commit();
			}
		}
		catch(SQLException ex)
		{
			
		}
	}

	public void updateBalance(int id, double bal)
	{
		try
		{
			String sql = "SELECT mem_balance FROM Member WHERE mem_id =" + id;
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				rset.updateDouble(1,bal);
				rset.updateRow();
				conn.commit();
			}
		}
		catch(SQLException e)
		{
			
		}
		
	}
	
	public double getBalance(int id)
	{
		double bal = 0;
		try
		{
			String sql = "SELECT mem_balance FROM Member WHERE mem_id =" + id;
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				return rset.getDouble(1);
			}
		}catch(SQLException e)
		{
			
		}
		return bal;
	}

}
