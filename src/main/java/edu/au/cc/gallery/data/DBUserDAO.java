package edu.au.cc.gallery;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUserDAO implements UserDAO {
    
    private DB db;

    public static Connection connection;

    public DBUserDAO() throws SQLException {
        db = new DB();
        db.connect();
    }

    public void listUsers() throws SQLException {

        ResultSet rs;
        rs = db.execute("select username, password, full_name from users");
        System.out.println("Username\tPassword\tFull Name");
        System.out.println("--------\t--------\t---------");
        while(rs.next()) {
            System.out.printf("%-15s %-15s %-15s %n", rs.getString(1)
                    , rs.getString(2)
                    , rs.getString(3));
     
               }
              rs.close();
     
         } 

         public void addUser(String username, String password, String full_name) throws SQLException {
            try {
            db.execute("insert into users values(?,?,?)", new String[] {username, 
                password, full_name});
            } catch (SQLException ex) {
                System.out.println("ERR: User " + username + " already exists in the database!");
            }
      
         }

         public boolean checkExist(String username) throws SQLException {    
            PreparedStatement ps = connection.prepareStatement("select username from users where username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
      }
      
      public void editUser (String username, String password, String full_name) throws SQLException {
        String stmt = "";    
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
    
    public void deleteUser(String username) throws SQLException {    
             PreparedStatement ps = connection.prepareStatement("delete from users where username = (?)");
                ps.setString(1, username);
                    ps.executeUpdate();
            
    }	


}