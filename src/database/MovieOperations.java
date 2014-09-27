package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Movie;

public class MovieOperations
{

	private Connection conn = null;
	private ResultSet rset = null;
	private PreparedStatement stmt = null;
	private DBConnection db;
	private MovieCopyOperations mco = new MovieCopyOperations();

	public MovieOperations()
	{
		db = new DBConnection();
		conn = db.openDB();
	}

	
	public void updateTrans(int id)
	{
		try
		{
			String sql = "SELECT prod_transCount FROM Movie WHERE prod_id =" + id;
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
	
	public int getCount()
	{
		int count = 0;
		try
		{
			String sql = "SELECT COUNT(prod_id) From Movie";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				count = rset.getInt(1);
			}
		}
		catch(SQLException e)
		{
			
		}
		return count;
	}
	
	
	public String getName(int id)
	{
		String name = null;
		try
		{
			String queryString = "SELECT prod_name FROM Movie WHERE prod_id = "
					+ id;
			stmt = conn.prepareStatement(queryString);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				name = rset.getString(1);

			}
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return name;

	}

	public int addMovie(Movie m, int qty)
	{
		int id = 0;
		try
		{
			// Checking if last name and phone number already exist
			String queryString = "SELECT * FROM Movie WHERE prod_name like '%"+ m.getName() + "%' AND prod_year = " + m.getYear();
			stmt = conn.prepareStatement(queryString);
			rset = stmt.executeQuery(queryString);
			if (rset.next())
			{
				return id;
			} else
			{
				String sql = "INSERT INTO Movie(prod_id,prod_name,prod_genre,prod_runTime,prod_ageRating,"
						+ "prod_desc, prod_year, prod_transCount)"
						+ "values(mvid_seq.nextVal,?,?,?,?,?,?,?)";

				// Create a Prepared statement
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, m.getName());
				stmt.setString(2, m.getGenre());
				stmt.setInt(3, m.getRunTime());
				stmt.setString(4, m.getRating());
				stmt.setString(5, m.getDesc());
				stmt.setInt(6, m.getYear());
				stmt.setDouble(7, 0);

				stmt.executeUpdate();
			}
			queryString = "SELECT prod_id FROM Movie WHERE prod_name like '%"+ m.getName() + "%' AND prod_year = " + m.getYear();
			stmt = conn.prepareStatement(queryString);
			rset = stmt.executeQuery(queryString);
			if (rset.next())
			{
				id = rset.getInt(1); // Getting the movie id
			}

			mco.addExisting(id, qty,m.getYear());
		} catch (Exception se)
		{
			System.out.println(se);
		}
		return id;
	}

	// Finding the movie with the highest transaction count
	public String getBest()
	{
		String name = null;
		int count = 0;
		try
		{
			String sql = "SELECT prod_transcount, prod_name FROM Movie";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery(sql);
			while (rset.next())
			{
				if (rset.getInt(1) > count) // Finding the highest count
				{
					count = rset.getInt(1);
					name = rset.getString(2);
				}

			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return name; // Returning the name of the movie with the highest count
	}


	public int getYear(int id)
	{
		int year = 0;
		try
		{
			String sql = "SELECT prod_year FROM Movie WHERE prod_id =" + id;
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery(sql);
			if (rset.next())
			{
				year = rset.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return year; 
	}


	public ResultSet returnRset()
	{
	try{
	String queryString = "SELECT prod_id, prod_name,prod_runTime,prod_year,prod_genre,prod_ageRating,prod_desc FROM Movie";
	   stmt = conn.prepareStatement(queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);       
	   rset = stmt.executeQuery();
	}catch(Exception e){
	System.out.println(e);
	}
	return rset;
	 
	}
}
