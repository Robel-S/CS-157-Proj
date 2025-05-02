package com.example.cs157proj;
import com.example.cs157proj.model.ConnectDB;
import java.sql.*;

public class DatabaseController {
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS movie (movieID INTEGER PRIMARY KEY, title VARCHAR(50), genre VARCHAR(50))";
                statement.executeUpdate(createTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


