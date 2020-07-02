package edu.au.cc.gallery;

import java.sql.SQLException;

public interface UserDAO {

    void listUsers() throws SQLException;

    void addUser(String username, String password, String full_name) throws SQLException;

    boolean checkExist(String username) throws SQLException;

    void editUser (String username, String password, String full_name) throws SQLException;

    void deleteUser(String username) throws SQLException;

}