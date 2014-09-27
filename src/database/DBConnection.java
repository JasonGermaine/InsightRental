package database;

import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;

public class DBConnection {
	
	private Connection conn = null;
    
	public Connection openDB()
	{
		try
			{	
				OracleDataSource ods = new OracleDataSource();
				
				  
				// Tallaght
				ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
				ods.setUser("X00090307");
				ods.setPassword("db28Apr93");
				
				// Home Oracle XE
				//ods.setURL("jdbc:oracle:thin:HR/pmagee@localhost:1521:XE");
				//ods.setUser("test");
				//ods.setPassword("test");
				 
				conn = ods.getConnection();
				//System.out.println("connected.");				
			}
		catch (Exception e)
			{
				System.out.print("Unable to load driver " + e);
				System.exit(1);
			}	
		return conn;
	}
	public void closeDB()
	{
		try
			{	
				conn.close();
			}
		catch (SQLException e)
			{
				System.out.print("Could not close connection ");
				e.printStackTrace();
			}
	}
}

