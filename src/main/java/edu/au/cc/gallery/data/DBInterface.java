package edu.au.cc.gallery;

import java.sql.SQLException;

public class DBInterface {
    public static UserDAO getUserDAO() throws SQLException {
        return new DBUserDAO();
    }
}