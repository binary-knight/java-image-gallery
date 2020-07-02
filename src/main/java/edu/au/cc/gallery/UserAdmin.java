package edu.au.cc.gallery;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserAdmin {

        public void addUser(String username, String password, String fullName) throws SQLException {

		try {
                	DB db = new DB();
                	db.connect();
                	db.execute("insert into users (username, password, full_name) values (?, ?, ?)", new String[] {username, password, fullName});
			db.close();
		} catch (SQLException e) {
			System.out.println("ERR: User " + username + " already exists in the database!");
		}
        }

        public ArrayList<String> listUsers() throws SQLException {
                ArrayList<String> list = new ArrayList<>();
                DB db = new DB();
                db.connect();
                ResultSet rs = db.execute("select * from users");

                while (rs.next()) {
                        list.add(rs.getString(1));
                }

                return list;

        }


        public boolean editUser(String username, String password, String fullName) throws SQLException {

             try {   
		DB db = new DB();
                db.connect();
                ResultSet rs = db.execute("select * from users where username='"+ username +"'");
                
                if (!rs.isBeforeFirst()) {
                        db.close();
			System.out.println("User doesn't exist.");
			return false;

                } else {
			if (password.isEmpty() && fullName.isEmpty()) {

                                return true;

                        }
                        else if (password.isEmpty()) {
                                db.execute("update users set full_name=? where username=?", new String[] {fullName, username});
                                db.close();
				return true;
                        }
                        else if (fullName.isEmpty()) {

                                db.execute("update users set password=? where username=?", new String[] {password, username});
                                db.close();
				return true;
                        } else {

                                db.execute("update users set password=? , full_name=? where username=?", new String[] {password, fullName, username});
                                db.close();
				return true;
                        }
                }
	     } catch (SQLException e) {
		System.out.println("ERR: Exception in information.");
		return false;
	    }

        }

        public void deleteUser(String username) throws SQLException {

              DB db = new DB();
              db.connect();
	      db.execute("delete from users where username=?", new String[] {username});
              db.close();
        }
}
