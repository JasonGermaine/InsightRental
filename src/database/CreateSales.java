package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateSales
{
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private DBConnection db;
	private ResultSet rset;

	public CreateSales()
	{
		db = new DBConnection();
		conn = db.openDB();
	}

	public void CreateSalesTable()
	{
	
		try
		{
			// Dropping Sales id Sequence
			String dropseq = "drop sequence soid_seq";
			stmt = conn.prepareStatement(dropseq);
			stmt.executeUpdate();
						
			// Dropping Sales Table
			String drop = "DROP TABLE Sales cascade constraints";
			stmt = conn.prepareStatement(drop);
			stmt.executeUpdate();

			
			// Creating Sales Table
			String create = "CREATE TABLE Sales "+ 
							"(so_id NUMBER PRIMARY KEY," +
							"so_amount DECIMAL(10,2)," +
							"so_date VARCHAR (15),"+ 
							"mem_id NUMBER," +
							"FOREIGN KEY (mem_id) REFERENCES MEMBER)";
			
			stmt = conn.prepareStatement(create);
			stmt.executeUpdate(create);
			
			// Creating Sales id Sequence
			String createseq = "create sequence soid_seq increment by 1 start with 1";
			stmt = conn.prepareStatement(createseq);
			stmt.executeUpdate(createseq);
			
			String insertString = "INSERT INTO Sales(so_id,so_amount,so_date, mem_id)" +
					"values(?,?,?,?)";
				stmt = conn.prepareStatement(insertString);
				
				stmt.setInt(1,0);
				stmt.setDouble(2, 0.0);
				stmt.setString(3,"Default Sale");
				stmt.setInt(4,0);
				stmt.executeUpdate();
			
			insertString = "INSERT INTO Sales(so_id,so_amount,so_date, mem_id)" +
									"values(soid_seq.nextVal,?,?,?)";
			stmt = conn.prepareStatement(insertString);

			stmt.setDouble(1, 55.0);
			stmt.setString(2,"4-26-2013");
			stmt.setInt(3,0);
			stmt.executeUpdate();

			stmt.setDouble(1, 30.0);
			stmt.setString(2,"3-26-2013");
			stmt.setInt(3,0);
			stmt.executeUpdate();
			
			stmt.setDouble(1, 98.0);
			stmt.setString(2,"5-26-2013");
			stmt.setInt(3,0);
			stmt.executeUpdate();
			
			stmt.setDouble(1, 10.0);
			stmt.setString(2,"9-26-2013");
			stmt.setInt(3,0);
			stmt.executeUpdate();
			
			stmt.setDouble(1, 70.0);
			stmt.setString(2,"3-26-2013");
			stmt.setInt(3,0);
			stmt.executeUpdate();
			
			stmt.setDouble(1, 90.0);
			stmt.setString(2,"12-26-2013");
			stmt.setInt(3,1);
			stmt.executeUpdate();
			
		} catch (SQLException ex)
		{
			System.out.println("SQL Exception" + ex);
			System.exit(1);
		}
	}
	
	public void querySales()
	{	
		try{
			String queryString = "SELECT * FROM Sales";			     
		    stmt = conn.prepareStatement(queryString); 
		    rset = stmt.executeQuery();
		    System.out.println("Sales Table");   
		    
		while (rset.next())
		{
			System.out.println(rset.getInt(1) +" " + rset.getDouble(2)+" "+rset.getString(3)+" "+rset.getInt(4));	
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
