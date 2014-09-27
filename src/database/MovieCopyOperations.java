package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.MovieCopy;

public class MovieCopyOperations
{

	private Connection conn = null;
	private ResultSet rset = null;
	private ResultSet rset2 = null;
	private PreparedStatement stmt = null;
	private DBConnection db;

	public MovieCopyOperations()
	{
		db = new DBConnection();
		conn = db.openDB();
	}

	public boolean returnFilm(int mId, int copy1)
	{
		try
		{
			String sql = "SELECT ro_id FROM MovieCopy WHERE prod_id = " + mId
					+ " AND copy_num = " + copy1;
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				rset.updateInt(1, 0);
				rset.updateRow();
				conn.commit();
				return true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet getRental(int id)
	{

		try
		{
			String sql = "SELECT prod_id, copy_num FROM MovieCopy WHERE ro_id = "
					+ id;
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rset = stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return rset;
	}

	public Double getPrice(int id)
	{

		double price = 0;
		try
		{
			String queryString = "SELECT copy_price FROM MovieCopy WHERE prod_id = "
					+ id;
			stmt = conn.prepareStatement(queryString);
			rset = stmt.executeQuery();
			if (rset.next())
			{

				price = rset.getDouble(1);
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return price;
	}

	public void sellCopies(int prodID, int soID)
	{
		try
		{
			String sql = "SELECT so_id FROM MovieCopy WHERE so_id = 0 AND ro_id = 0 AND prod_id = "
					+ prodID;
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setMaxRows(1);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				rset.updateInt(1, soID);
				rset.updateRow();
				conn.commit();
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void rentCopies(int id, int roID)
	{
		try
		{
			String sql = "SELECT ro_id FROM MovieCopy WHERE so_id = 0 AND ro_id = 0 AND prod_id = "
					+ id;
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setMaxRows(1);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				rset.updateInt(1, roID);
				rset.updateRow();
				conn.commit();
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public String checkStock()
	{
		String products = "Product IDs:";
		try
		{
			String sql = "SELECT distinct prod_id FROM MovieCopy";
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			while (rset.next())
			{
				int prod_id = rset.getInt(1);
				sql = "SELECT COUNT(*) FROM MovieCopy WHERE prod_id = "
						+ prod_id + " AND so_id = 0";
				stmt = conn.prepareStatement(sql);
				rset2 = stmt.executeQuery(sql);
				if (rset2.next())
				{
					if (rset2.getInt(1) < 10)
					{
						products += "\n" + prod_id; // Adding product id to the
													// string

					}
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return products;
	}

	public boolean addExisting(int id, int qty, int year)
	{
		boolean exist = false;
		try
		{
			MovieCopy mc = new MovieCopy(year);
			int start = 1;
			String query = "SELECT COUNT(*) FROM MovieCopy WHERE prod_id ="
					+ id;
			stmt = conn.prepareStatement(query);
			rset = stmt.executeQuery(query);
			if (rset.next())
			{
				if (rset.getInt(1) != 0)
				{
					start = rset.getInt(1) + 1;
				}

			}
			// Dropping MovieCopy num Sequence
			String dropseq = "drop sequence cpy_seq";
			stmt = conn.prepareStatement(dropseq);
			stmt.executeUpdate();

			// Creating MovieCopy num Sequence
			String createseq = "create sequence cpy_seq increment by 1 start with "
					+ start;
			stmt = conn.prepareStatement(createseq);
			stmt.executeUpdate(createseq);

			for (int i = 0; i < qty; i++)
			{
				String insertString = "INSERT INTO MovieCopy(prod_id,copy_num,copy_price,so_id,ro_id) values(?,cpy_seq.nextVal,?,?,?)";
				stmt = conn.prepareStatement(insertString);

				stmt.setInt(1, id);
				stmt.setDouble(2, mc.getPrice());
				stmt.setInt(3, 0);
				stmt.setInt(4, 0);

				stmt.executeUpdate();
			}
			exist = true;
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Product does not exist");
		}
		return exist;
	}

	public boolean checkID(int id)
	{
		try
		{
			String sql = "SELECT prod_id From MovieCopy WHERE prod_id = " + id;
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			if (rset.next())
			{
				return true;
			}
		} catch (SQLException e)
		{

		}
		return false;
	}

	public int stockEnquiry(int id)
	{
		int stock = -1;
		try
		{
			if (checkID(id) == true)
			{
				String sql = "SELECT COUNT(prod_id) From MovieCopy WHERE so_id = 0 AND ro_id = 0 AND prod_id = "
						+ id;
				stmt = conn.prepareStatement(sql);
				rset = stmt.executeQuery();
				if (rset.next())
				{
					stock = rset.getInt(1);
					return stock;
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return stock;
	}

}
