package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateRental
{
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private DBConnection db;
	private ResultSet rset;

	public CreateRental()
	{
		db = new DBConnection();
		conn = db.openDB();
	}

	public void CreateRentalTable()
	{
	
		try
		{
			// Dropping Rental id Sequence
			String dropseq = "drop sequence roid_seq";
			stmt = conn.prepareStatement(dropseq);
			stmt.executeUpdate();
						
			// Dropping Rental Table
			String drop = "DROP TABLE Rental cascade constraints";
			stmt = conn.prepareStatement(drop);
			stmt.executeUpdate();

			
			// Creating Rental Table
			String create = "CREATE TABLE RENTAL"+ 
							"(ro_id NUMBER PRIMARY KEY," +
							"ro_amount DECIMAL(10,2)," +
							"ro_date VARCHAR (15),"+
							"ro_numDays NUMBER," +
							"mem_id NUMBER, " +
							"FOREIGN KEY (mem_id) REFERENCES MEMBER)";
			
			stmt = conn.prepareStatement(create);
			stmt.executeUpdate(create);
			
			
			// Creating Rental id Sequence
			String createseq = "create sequence roid_seq increment by 1 start with 1";
			stmt = conn.prepareStatement(createseq);
			stmt.executeUpdate(createseq);
			
			String insertString = "INSERT INTO RENTAL(ro_id,ro_amount,ro_date,ro_numDays, mem_id)" +
									"values(?,?,?,?,?)";
			stmt = conn.prepareStatement(insertString);

			stmt.setInt(1, 0);
			stmt.setDouble(2, 55.0);
			stmt.setString(3,"02-26-2013");
			stmt.setInt(4,5);
			stmt.setInt(5,0);
			stmt.executeUpdate();
			
		} catch (SQLException ex)
		{
			System.out.println("SQL Exception" + ex);
			System.exit(1);
		}
		
	}
	
	public void queryRental()
	{	
		try{
			String queryString = "SELECT * FROM Rental";			     
		    stmt = conn.prepareStatement(queryString); 
		    rset = stmt.executeQuery();
		    System.out.println("Rental Table");   
		    
		while (rset.next())
		{
			System.out.println(rset.getInt(1) +" " + rset.getDouble(2)+" "+rset.getString(3)+" "+rset.getInt(4)+" "+rset.getInt(5));	
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
