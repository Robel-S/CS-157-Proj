package com.example.cs157proj.model;
import java.sql.*;

public class DatabaseController {
    public DatabaseController() {
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS movie (movieID INTEGER PRIMARY KEY, " +
                        "title VARCHAR(50), genre VARCHAR(50), stock INTEGER, avgRating REAL)";
                statement.executeUpdate(createTable);
                 createTable = "CREATE TABLE IF NOT EXISTS customer (customerID INTEGER PRIMARY KEY, " +
                        "username VARCHAR(50), password VARCHAR(50), name VARCHAR(50), age INTEGER)";
                statement.executeUpdate(createTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}



