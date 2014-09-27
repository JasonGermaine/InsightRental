package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import model.Admin;
import model.SaleStaff;

public class UserOperations
{
	private Connection conn = null;
	private ResultSet rset = null;
	private PreparedStatement stmt = null;
	private DBConnection db;
	private AdminOperations ao = new AdminOperations();
	private SaleStaffOperations sso = new SaleStaffOperations();

	public UserOperations()
	{
		db = new DBConnection();
		conn = db.openDB();
	}
	
	public boolean login(int id, String password)
	{
		boolean exist = false;
		try
		{
			
			conn.setAutoCommit(false);
			String cmd = "SELECT * FROM User1 WHERE user_id = " + id + " AND user_password = '" + password + "'";
			stmt = conn.prepareStatement(cmd); 
			rset = stmt.executeQuery();
			if (rset.next())
			{
				return true;
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

		return exist; // Returning if the user exists

	}
	
	public void findUser(int id)
	{
		try
		{
			String cmd = "SELECT user_fname,user_lname FROM User1 WHERE user_id =" + id;
			stmt = conn.prepareStatement(cmd);
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				int c = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + rset.getString(1) + " " + rset.getString(2) + " ?","Confirmation",JOptionPane.YES_NO_OPTION);
				if(c == JOptionPane.YES_OPTION)
				{
					deleteUser(id);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Cancelled");
				}
				
			}
			else
			{
				JOptionPane.showMessageDialog(null, "User does not exist");
				
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	//Deleting the user from the table
	public void deleteUser(int id)
	{
		try
		{
			sso.deleteSaleStaff(id);
			conn.setAutoCommit(false);
			String cmd = "SELECT user_id,user_fname,user_lname,user_dob, user_address,user_phoneNum,user_password FROM User1 WHERE user_id = " + id;
			stmt = conn.prepareStatement(cmd, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE); // create empty SQL statement
			rset = stmt.executeQuery();
			if (rset.next()) // try to go to row
			{
				rset.deleteRow(); // delete the row from the database and result set
				JOptionPane.showMessageDialog(null, "User deleted");
				conn.commit();
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	//Adding an admin user
	public int addAdminUser(Admin a)
	{
		int num =0; // variable to store the user id
		try
		{
			//Checking if last name and phone number already exist
			String queryString = "SELECT * FROM User1 WHERE user_lname like '"+a.getLname()+"' AND user_phoneNum like '"+a.getPhone()+"'";
			stmt = conn.prepareStatement(queryString);
			rset = stmt.executeQuery(queryString);
			if (rset.next())
			{
				return num;
			}
			else
			{
				String insertString = "INSERT INTO User1(user_id,user_fname,user_lname,user_dob," +
						"user_address,user_phoneNum,user_password) values(uid_seq.nextVal,?,?,?,?,?,?)";

			// Create a Prepared statement
 			stmt = conn.prepareStatement(insertString);
	
			stmt.setString(1, a.getFname());
			stmt.setString(2, a.getLname());
			stmt.setString(3, a.getDob());
			stmt.setString(4, a.getAddress());
			stmt.setString(5, a.getPhone());
			stmt.setString(6, a.getPassword());

			stmt.executeUpdate();
			}
			queryString = "SELECT * FROM User1 WHERE user_lname like '"+a.getLname()+"' AND user_phoneNum like '"+a.getPhone()+"'";
			stmt = conn.prepareStatement(queryString);
			rset = stmt.executeQuery(queryString);
			if (rset.next())
			{
				num = rset.getInt(1);	 // Setting the user id
				ao.addAdmin(num); //Calling the add admin table
			}
		}
		catch(Exception se){
			System.out.println(se);
		}
		
		return num; //Returning user id so it can be printed in a message
	}

	
	
	//Adding a SalesStaff
		public int addStaffUser(SaleStaff ss)
		{
			int num =0;// variable to store the user id
			try
			{
				//Checking if last name and phone number already exist
				String queryString = "SELECT * FROM User1 WHERE user_lname like '"+ss.getLname()+"' AND user_phoneNum like '"+ss.getPhone()+"'";
				stmt = conn.prepareStatement(queryString);
				rset = stmt.executeQuery(queryString);
				if (rset.next())
				{
					return num;
				}
				else
				{
					String insertString = "INSERT INTO User1(user_id,user_fname,user_lname,user_dob," +
							"user_address,user_phoneNum,user_password) values(uid_seq.nextVal,?,?,?,?,?,?)";

				// Create a Prepared statement
	 			stmt = conn.prepareStatement(insertString);
		
				stmt.setString(1, ss.getFname());
				stmt.setString(2, ss.getLname());
				stmt.setString(3, ss.getDob());
				stmt.setString(4, ss.getAddress());
				stmt.setString(5, ss.getPhone());
				stmt.setString(6, ss.getPassword());

				stmt.executeUpdate();
				
				
				}
				queryString = "SELECT * FROM User1 WHERE user_lname like '"+ss.getLname()+"' AND user_phoneNum like '"+ss.getPhone()+"'";
				stmt = conn.prepareStatement(queryString);
				rset = stmt.executeQuery(queryString);
				if (rset.next())
				{
					num = rset.getInt(1);// Setting the user id
					sso.addSaleStaff(num); //Calling the add SaleStaff table
				}
			}
			catch(Exception se){
				System.out.println(se);
			}
			
			return num;	//Returning user id so it can be printed in a message
		}


}
