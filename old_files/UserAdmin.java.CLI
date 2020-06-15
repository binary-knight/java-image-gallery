package edu.au.cc.gallery.tools;

import edu.au.cc.gallery.DB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Scanner;

public class UserAdmin {

private static DB db;

public static void displayMenu() {
  System.out.println("\n1. List Users"
		  + "\n2. Add User"
		  + "\n3. Edit User"
		  + "\n4. Delete User"
		  + "\n5. Quit Program");
  System.out.print("Selection: ");
}

public static void main() throws SQLException {
  Scanner sc = new Scanner(System.in);
  int input = -1;
  String username = "";
  String password = "";
  String full_name = "";

  while (input != 5) {
	  displayMenu();
          input = sc.nextInt();
	  sc.nextLine();
          
	  switch (input) {
		  case 1:
			  DB.listUsers();
			  break;
		  case 2:
			  System.out.println("Adding New User");
			  System.out.print("Enter Username: ");
			  username = sc.nextLine();
			  System.out.print("Enter Password: ");
			  password = sc.nextLine();
			  System.out.print("Enter Full Name: ");
			  full_name = sc.nextLine();

			  DB.addUser(username, password, full_name);
			  System.out.println(username + " added to database.");
			  break;
		  case 3:
			  System.out.println("Editing User, press enter for no change.");
			  System.out.print("User to edit: ");
			  username = sc.nextLine();
			  if (!DB.checkExist(username)) {
				  System.out.println(username 
						  + " does not exist.");
				  break;
			  } else {
			  System.out.print("Password for user: ");
			  password = sc.nextLine();
			  System.out.print("Full Name for user: ");
			  full_name = sc.nextLine();
			  if (password.equals("") && full_name.equals("")) {
				  System.out.println("No changes made.");
			  } else {
			  DB.editUser(username, password, full_name);
			  }
			  break;
			  }
		  case 4:
			  System.out.println("Deleting User");
			  System.out.println("User to delete: ");
			  username = sc.nextLine();
			  if (!DB.checkExist(username)) {
				  System.out.println(username
						  + " does not exist.");
				  break;
			  } else {
		          System.out.print("Please input user's username again to confirm removal from database: ");
			  String confirm = sc.nextLine();
			  if (!confirm.matches(username)) {
				 System.out.print("Username didn't match, aborting.");
			  break;
			  } else {
				  DB.deleteUser(username);
				  System.out.print(username + " removed from database.");
				  break;
			  }
			  }
		  case 5: 
			  System.out.println("Session Terminated.");
			  break;
		  default:
			  break;
                          }


                       }             

          }             

}
