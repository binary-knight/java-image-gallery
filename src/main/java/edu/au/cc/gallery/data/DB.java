package edu.au.cc.gallery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class DB {
    private static final String dbURL = "jdbc:postgresql://m6-webappdb.c7yjhmuuwbyx.us-east-1.rds.amazonaws.com/";

    public static Connection connection;

    private JSONObject getSecret() {
	String s = Secrets.getSecretImageGallery();
	return new JSONObject(s);
    }
  
    private String getPassword(JSONObject secret) {
	    return secret.getString("password");
    }

    public void connect() throws SQLException {
	    try {
	    Class.forName("org.postgresql.Driver");
    	    JSONObject secret = getSecret();
	    connection = DriverManager.getConnection(dbURL, "image_gallery", getPassword(secret));
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

public ResultSet query(String query) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}


   public void close() throws SQLException {
	   connection.close();
   }

}





