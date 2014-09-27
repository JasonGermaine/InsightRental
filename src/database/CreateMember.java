package database;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
import java.sql.SQLException;

	public class CreateMember
	{
		private Connection conn = null;
		private PreparedStatement stmt = null;
		private DBConnection db;
		private ResultSet rset;

		
		public CreateMember()
		{
			db = new DBConnection();
			conn = db.openDB();
		}

		public void CreateMemberTable()
		{
			try
			{
				// Dropping Sequence
				String dropseq = "drop sequence mid_seq";
				stmt = conn.prepareStatement(dropseq);
				stmt.executeUpdate();

				// Dropping Table
				String drop = "DROP TABLE Member cascade constraints";
				stmt = conn.prepareStatement(drop);
				stmt.executeUpdate();

				// Creating Table
				String create = "CREATE TABLE Member "+ 
								"(mem_id NUMBER PRIMARY KEY," +
								"mem_fname VARCHAR(15)," +
								"mem_lname VARCHAR(15),"+
								"mem_dob VARCHAR (15)," +
								"mem_address VARCHAR(50)," +
								"mem_phoneNum VARCHAR(30)," +
								"mem_loyaltyPts NUMBER," +
								"mem_transCount NUMBER," +
								"mem_balance DECIMAL (4,2))";
				
				stmt = conn.prepareStatement(create);
				stmt.executeUpdate(create);

				// Creating Sequence
				String createseq = "create sequence mid_seq increment by 1 start with 1";
				stmt = conn.prepareStatement(createseq);
				stmt.executeUpdate(createseq);

				String insertString = "INSERT INTO Member(mem_id,mem_fname,mem_lname,mem_dob," +
									"mem_address,mem_phoneNum,mem_loyaltyPts,mem_transCount,mem_balance) values(mid_seq.nextVal,?,?,?,?,?,?,?,?)";
				stmt = conn.prepareStatement(insertString);
				
				stmt.setString(1, "Jason");
				stmt.setString(2, "Germaine");
				stmt.setString(3,"04-28-1993");
				stmt.setString(4, "Kilnamanagh, Dublin 24");
				stmt.setString(5, "0871234567");
				stmt.setInt(6, 800);
				stmt.setInt(7, 4);
				stmt.setDouble(8, 10.00);
				stmt.executeUpdate();
				
				
				insertString = "INSERT INTO Member(mem_id,mem_fname,mem_lname,mem_dob," +
						"mem_address,mem_phoneNum,mem_loyaltyPts,mem_transCount,mem_balance) values(?,?,?,?,?,?,?,?,?)";
				stmt = conn.prepareStatement(insertString);
			
				stmt.setInt(1,0);
				stmt.setString(2, "Unregistered");
				stmt.setString(3, "Customer");
				stmt.setString(4,"mm-dd-yyyy");
				stmt.setString(5, "Inisight DVD Rental");
				stmt.setString(6, "##########");
				stmt.setInt(7, 0);
				stmt.setInt(8, 0);
				stmt.setDouble(9, 0.00);
				stmt.executeUpdate();
				
			} catch (SQLException ex)
			{
				System.out.println("SQL Exception" + ex);
				System.exit(1);
			}
		}
		
		public void queryMembers()
		{	
			try{
				String queryString = "SELECT * FROM Member";			     
			    stmt = conn.prepareStatement(queryString); 
			    rset = stmt.executeQuery();
			    System.out.println("Member Table");   
			    
			while (rset.next())
			{
				System.out.println(rset.getInt(1) +" " + rset.getString(2)+" "+rset.getString(3)+" "+rset.getString(4)+" "+rset.getString(5)
									+ " " + rset.getString(6)+ " " + rset.getInt(7) + " " + rset.getInt(8) + " " + rset.getDouble(9) );		
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
