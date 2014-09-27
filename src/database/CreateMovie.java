package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateMovie
{
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private DBConnection db;
	private ResultSet rset;

	public CreateMovie()
	{
		db = new DBConnection();
		conn = db.openDB();
	}

	public void CreateMovieTable()
	{
	
		try
		{
			// Dropping Movie id Sequence
			String dropseq = "drop sequence mvid_seq";
			stmt = conn.prepareStatement(dropseq);
			stmt.executeUpdate();
		
			// Dropping MovieCopy num Sequence
			dropseq = "drop sequence cpy_seq";
			stmt = conn.prepareStatement(dropseq);
			stmt.executeUpdate();
			
			// Dropping Movie Table
			String drop = "DROP TABLE MOVIE cascade constraints";
			stmt = conn.prepareStatement(drop);
			stmt.executeUpdate();

			// Dropping MovieCopy Table
			drop = "DROP TABLE MovieCopy";
			stmt = conn.prepareStatement(drop);
			stmt.executeUpdate();
			
			// Creating Movie Table
			String create = "CREATE TABLE MOVIE "+ 
							"(prod_id NUMBER PRIMARY KEY," +
							"prod_name VARCHAR(30)," +
							"prod_genre VARCHAR(30),"+
							"prod_runTime NUMBER," +
							"prod_ageRating VARCHAR(10)," +
							"prod_desc VARCHAR(100)," +
							"prod_year Number," +
							"prod_transCount NUMBER)";
			
			stmt = conn.prepareStatement(create);
			stmt.executeUpdate(create);

			
			// Creating MovieCopy Table
			create = "CREATE TABLE MovieCopy "+ 
							"(prod_id NUMBER," +
							"copy_num NUMBER," +
							"copy_price DECIMAL(4,2),"+
							"so_id NUMBER," +
							"ro_id NUMBER,"+
							"PRIMARY KEY(prod_id,copy_num),"+ 
							"FOREIGN KEY (prod_id) REFERENCES Movie," +
							"FOREIGN KEY (so_id) REFERENCES Sales," +
							"FOREIGN KEY (ro_id) REFERENCES Rental)";
			
			stmt = conn.prepareStatement(create);
			stmt.executeUpdate(create);
			
			
			// Creating Movie id Sequence
			String createseq = "create sequence mvid_seq increment by 1 start with 1";
			stmt = conn.prepareStatement(createseq);
			stmt.executeUpdate(createseq);

			// Creating MovieCopy num Sequence
			createseq = "create sequence cpy_seq increment by 1 start with 1";
			stmt = conn.prepareStatement(createseq);
			stmt.executeUpdate(createseq);
			
			String insertString = "INSERT INTO Movie(prod_id,prod_name,prod_genre,prod_runTime,prod_ageRating,prod_desc, prod_year, prod_transCount)" +"values(mvid_seq.nextVal,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(insertString);

			stmt.setString(1,"Harry Potter");
			stmt.setString(2,"Fantasy");
			stmt.setInt(3,126);
			stmt.setString(4,"U");
			stmt.setString(5,"This is a film about a little boy who discovers that he is a wizard.");
			stmt.setInt(6, 2001);
			stmt.setInt(7, 2);
			stmt.executeUpdate();

			for(int i = 0; i<10;i++)
			{
			insertString = "INSERT INTO MovieCopy(prod_id,copy_num,copy_price,so_id,ro_id)" +
									"values(mvid_seq.currVal,cpy_seq.nextVal,?,?,?)";
			stmt = conn.prepareStatement(insertString);

			stmt.setDouble(1, 10.0);
			stmt.setInt(2,0);
			stmt.setInt(3, 0);
			
			stmt.executeUpdate();
			}
		} catch (SQLException ex)
		{
			System.out.println("SQL Exception" + ex);
			System.exit(1);
		}
	}
	
	
	public void queryMovie()
	{	
		try{
			String queryString = "SELECT * FROM Movie";			     
		    stmt = conn.prepareStatement(queryString); 
		    rset = stmt.executeQuery();
		    System.out.println("Movie Table");   
		    
		while (rset.next())
		{
			System.out.println(rset.getInt(1) +" " + rset.getString(2)+" "+rset.getString(3)+" "+rset.getInt(4)+" "+rset.getString(5)+" "+rset.getString(6)+" "+rset.getInt(7)+" "+rset.getInt(8));	
		}	
		
		conn.commit();
		stmt.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void queryMovieCopy()
	{	
		try{
			String queryString = "SELECT * FROM MovieCopy";			     
		    stmt = conn.prepareStatement(queryString); 
		    rset = stmt.executeQuery();
		    System.out.println("MovieCopy Table");   
		    
		while (rset.next())
		{
			System.out.println(rset.getInt(1) +" " + rset.getInt(2)+" "+rset.getDouble(3)+" "+rset.getInt(4)+" "+rset.getInt(5));	
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
