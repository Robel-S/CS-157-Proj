package com.example.cs157proj.model;
import java.sql.*;

public class DatabaseController {

    private Statement statement;

    public DatabaseController() {
        ConnectDB db = new ConnectDB(); //create an object for connecting to our database
        Connection connection = db.getConnection(); //gets connection from our ConnectDB Object
        if (connection != null) { //if the database is connected create movie, customer, rental, and rating tables
            try {
                statement = connection.createStatement();
                String movieTable = "CREATE TABLE IF NOT EXISTS movie (movieID INTEGER NOT NULL PRIMARY KEY, " +
                        "title VARCHAR(50), genre VARCHAR(50), stock INTEGER, avgRating DECIMAL (1,1))";
                String customerTable = "CREATE TABLE IF NOT EXISTS customer (customerID INTEGER NOT NULL PRIMARY KEY, " +
                        "username VARCHAR(50), password VARCHAR(50), name VARCHAR(50), age INTEGER)";
                String rentalTable = "CREATE TABLE IF NOT EXISTS rental (customerID INTEGER NOT NULL, " +
                         "movieID INTEGER NOT NULL, dueDate VARCHAR(50), PRIMARY KEY (customerID, movieID))";
                String ratingTable = "CREATE TABLE IF NOT EXISTS rating (customerID INTEGER NOT NULL, " +
                        "movieID INTEGER NOT NULL, rating DECIMAL (1,1), PRIMARY KEY (customerID, movieID))";
                createTable(movieTable);
                createTable(customerTable);
                createTable(rentalTable);
                createTable(ratingTable);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void createTable(String createTable) {
        try {
            statement.executeUpdate(createTable); //use statement to create table from SQL statement in string
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void insertMovie(String title, String genre, int stock, int avgRating) {

    }
    public void insertCustomer(String title, String genre, int stock, int avgRating) {

    }

    public void insertRating(int customerID, int movieID, double rating) {

    }
}



