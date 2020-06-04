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

    private Connection connection;	    

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

   public void close() throws SQLException {
	   connection.close();
   }

   public static void demo() throws Exception {
	   DB db = new DB();
	   db.connect();
	   ResultSet rs = db.execute("select  username,password, full_Name from users");
	   while(rs.next()) {
		   System.out.println("user: "+rs.getString(1));
	   }
	   db.close();
   }

}

