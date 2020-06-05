package edu.au.cc.gallery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    private static final String dbURL = "jdbc:postgresql://demo-database-1.c7yjhmuuwbyx.us-east-1.rds.amazonaws.com/image_gallery";

    public static Connection connection;	    

    private String getPassword() {
        try(BufferedReader br = new BufferedReader(new FileReader("/home/ec2-user/.sql-passwd"))) {
	String result = br.readLine();
	br.close();
	return result;
    } catch (IOException ex) {
	    System.err.println("Error opening password file.");
	    System.exit(1);
       }
       return null;
    }

    public void connect() throws SQLException {
	    try {
	    Class.forName("org.postgresql.Driver");
	    connection = DriverManager.getConnection(dbURL, "image_gallery", getPassword());
	    } catch (ClassNotFoundException ex) {
		    ex.printStackTrace();
		    System.exit(1);
    }

  }

public ResultSet execute(String query) throws SQLException {
     PreparedStatement stmt = connection.prepareStatement(query);
     ResultSet rs = stmt.executeQuery();
     return rs;
}

  public void execute(String query, String[] values) throws SQLException {
	  PreparedStatement stmt = connection.prepareStatement(query);
	  for(int i=0; i < values.length; i++)
		  stmt.setString(i+1, values[i]);
		  stmt.execute();
	    }


   public void close() throws SQLException {
	   connection.close();
   }

   public static void demo() throws Exception {
	   DB db = new DB();
	   db.connect();
	   db.execute("update users set password=? where username=?",
			   new String[] {"monkey", "fred"});
	   ResultSet rs = db.execute("select username,password,full_name from users");
	   while(rs.next()) System.out.println(rs.getString(1)+","
			   + rs.getString(2)+","
			   + rs.getString(3));
	   //rs.close();
	   db.close();
   }

public static void listUsers() throws SQLException {

   ResultSet rs;
   DB db = new DB();
   db.connect();
   rs = db.execute("select username, password, full_name from users");
   System.out.println("Username\tPassword\tFull Name");
   System.out.println("--------\t--------\t---------");
   while(rs.next()) {
	   System.out.printf("%-15s %-15s %-15s %n", rs.getString(1)
			   , rs.getString(2)
			   , rs.getString(3));

          }
         rs.close();
	 db.close();

    } 

public static void addUser(String username, String password, String full_name) throws SQLException {
      DB db = new DB();
      db.connect();
      try {
      db.execute("insert into users values(?,?,?)", new String[] {username, 
	      password, full_name});
      } catch (SQLException ex) {
	      System.out.println("ERR: User " + username + " already exists in the database!");
      }
      db.close();

   }

public static boolean checkExist(String username) throws SQLException {
      DB db = new DB();
      db.connect();

      PreparedStatement ps = connection.prepareStatement("select username from users where username = ?");
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
	      return true;
      } else {
	      return false;
      }
}

public static void editUser (String username, String password, String full_name) throws SQLException {
	String stmt = "";

	DB db = new DB();
        db.connect();

	if (password.equals("")) {
		stmt = "update users set full_name = (?) where username = (?)";
	} else if (!password.equals("") && !full_name.equals("")) {
		stmt = "update users set password = (?), full_name = (?) where username = (?)";
	} else {
		stmt = "update users set password = (?) where username = (?)";
	}

	try {
		PreparedStatement ps = connection.prepareStatement(stmt);
                if (!password.equals("") && !full_name.equals("")) {
			ps.setString(1, password);
			ps.setString(2, full_name);
			ps.setString(3, username);
			ps.executeUpdate();
		        }
               		else  if (password.equals("")) {
			ps.setString(1, full_name);
			ps.setString(2, username);
			ps.executeUpdate();
			}
			else {
			ps.setString(1, password);
			ps.setString(2, username);
			ps.executeUpdate();
			}
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}

}

public static void deleteUser(String username) throws SQLException {
	 DB db = new DB();
         db.connect();

         PreparedStatement ps = connection.prepareStatement("delete from users where username = (?)");
			ps.setString(1, username);
		        ps.executeUpdate();
		
}	

}





