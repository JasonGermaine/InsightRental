package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateUsers
{
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private DBConnection db;
	private ResultSet rset;

	public CreateUsers()
	{
		db = new DBConnection();
		conn = db.openDB();
	}

	public void CreateUsersTable()
	{
		try
		{
			// Dropping User id Sequence
			String dropseq = "drop sequence uid_seq";
			stmt = conn.prepareStatement(dropseq);
			stmt.executeUpdate();

			// Dropping Admin num Sequence
			dropseq = "drop sequence anum_seq";
			stmt = conn.prepareStatement(dropseq);
			stmt.executeUpdate();
			
			
			// Dropping SaleStaff num Sequence
			dropseq = "drop sequence snum_seq";
			stmt = conn.prepareStatement(dropseq);
			stmt.executeUpdate();
						
			// Dropping Users Table
			String drop = "DROP TABLE User1 cascade constraints";
			stmt = conn.prepareStatement(drop);
			stmt.executeUpdate();

			// Dropping Admin Table
			drop = "DROP TABLE Admin";
			stmt = conn.prepareStatement(drop);
			stmt.executeUpdate();
			
			// Dropping SaleStaff Table
			drop = "DROP TABLE SaleStaff";
			stmt = conn.prepareStatement(drop);
			stmt.executeUpdate();
			
			// Creating Users Table
			String create = "CREATE TABLE User1 "+ 
							"(user_id NUMBER," +
							"user_fname VARCHAR(15)," +
							"user_lname VARCHAR(15),"+ 
							"user_dob VARCHAR (15)," +
							"user_address VARCHAR(30), " +
							"user_phoneNum VARCHAR(30), " +
							"user_password VARCHAR(20)," +
							"PRIMARY KEY (user_id))";
			
			stmt = conn.prepareStatement(create);
			stmt.executeUpdate(create);
			
			// Creating Admin Table
			create = "CREATE TABLE Admin "+ 
							"(user_id NUMBER," +
							"admin_num NUMBER," +
							"PRIMARY KEY(user_id,admin_num),"+ 
							"FOREIGN KEY (user_id) REFERENCES User1)";
			stmt = conn.prepareStatement(create);
			stmt.executeUpdate(create);
			
			// Creating SaleStaff Table
						create = "CREATE TABLE SaleStaff "+ 
										"(user_id NUMBER," +
										"staff_num NUMBER," +
										"PRIMARY KEY(user_id,staff_num),"+ 
										"FOREIGN KEY (user_id) REFERENCES User1)";
						stmt = conn.prepareStatement(create);
						stmt.executeUpdate(create);
			
						
									
			// Creating User id Sequence
			String createseq = "create sequence uid_seq increment by 1 start with 1";
			stmt = conn.prepareStatement(createseq);
			stmt.executeUpdate(createseq);

			// Creating Admin num Sequence
			createseq = "create sequence anum_seq increment by 1 start with 1";
			stmt = conn.prepareStatement(createseq);
			stmt.executeUpdate(createseq);
			
			// Creating SaleStaff num Sequence
			createseq = "create sequence snum_seq increment by 1 start with 1";
			stmt = conn.prepareStatement(createseq);
			stmt.executeUpdate(createseq);
			
			
			String insertString = "INSERT INTO User1(user_id,user_fname,user_lname,user_dob," +
								"user_address,user_phoneNum,user_password) values(uid_seq.nextVal,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(insertString);

			stmt.setString(1, "Jason");
			stmt.setString(2, "Germaine");
			stmt.setString(3,"04-28-1993");
			stmt.setString(4, "Kilnamanagh, Dublin 24");
			stmt.setString(5, "0871234567");
			stmt.setString(6, "1");
			stmt.executeUpdate();
			
			insertString = "INSERT INTO Admin(user_id, admin_num) values(uid_seq.currVal, anum_seq.nextVal)";
			stmt = conn.prepareStatement(insertString);
			stmt.executeUpdate();

			insertString = "INSERT INTO User1(user_id,user_fname,user_lname,user_dob," +
							"user_address,user_phoneNum,user_password) values(uid_seq.nextVal,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(insertString);
			
			stmt.setString(1, "Shane");
			stmt.setString(2, "Galvin");
			stmt.setString(3,"01-11-1993");
			stmt.setString(4, "Raheen, Dublin 24");
			stmt.setString(5, "0861234567");
			stmt.setString(6, "password");
			stmt.executeUpdate();
			
			insertString = "INSERT INTO SaleStaff(user_id, staff_num)values(uid_seq.currVal,snum_seq.nextVal)";
			stmt = conn.prepareStatement(insertString);
			stmt.executeUpdate();
			
		} catch (SQLException ex)
		{
			System.out.println("SQL Exception" + ex);
			System.exit(1);
		}
	}
	public void queryUser()
	{	
		try{
			String queryString = "SELECT * FROM User1";			     
		    stmt = conn.prepareStatement(queryString); 
		    rset = stmt.executeQuery();
		    System.out.println("User Table");   
		while (rset.next()){
			System.out.println(rset.getInt(1) +" " + rset.getString(2)+" "+rset.getString(3)+" "+rset.getString(4)+" "+rset.getString(5)
								+ " " + rset.getString(6) + " " + rset.getString(7));		
		}	
		
		conn.commit();
		stmt.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void queryAdmin()
	{	
		try{
			String queryString = "SELECT * FROM Admin";			     
		    stmt = conn.prepareStatement(queryString); 
		    rset = stmt.executeQuery();
		    System.out.println("Admin Table");   
		while (rset.next()){
			System.out.println(rset.getInt(1) +" " + rset.getInt(2));		
		}	
		
		conn.commit();
		stmt.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void querySaleStaff()
	{	
		try{
			String queryString = "SELECT * FROM SaleStaff";			     
		    stmt = conn.prepareStatement(queryString); 
		    rset = stmt.executeQuery();
		    System.out.println("SaleStaff Table");   
		while (rset.next()){
			System.out.println(rset.getInt(1) +" " + rset.getInt(2));		
		}	
		
		conn.commit();
		stmt.close();
		db.closeDB();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
