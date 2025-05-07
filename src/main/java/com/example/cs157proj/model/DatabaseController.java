package com.example.cs157proj.model;
import java.sql.*;

public class DatabaseController {

    private Statement statement;
    public DatabaseController() {
        ConnectDB db = new ConnectDB(); //create an object for connecting to our databse
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
                 String m1 = "INSERT OR IGNORE INTO movie(movieID, title, genre, stock, avgRating) " +
                         "VALUES (12345, 'Thunderbolts', 'Action', 3, 5.0)";
                String m2 = "INSERT OR IGNORE INTO movie(movieID, title, genre, stock, avgRating) " +
                        "VALUES (23456, 'Sinners', 'Horror', 2, 5.0)";
                String m3 = "INSERT OR IGNORE INTO movie(movieID, title, genre, stock, avgRating) " +
                        "VALUES (34567, 'Wicked', 'Fantasy', 4, 4.5)";
                String m4 = "INSERT OR IGNORE INTO movie(movieID, title, genre, stock, avgRating) " +
                        "VALUES (45678, 'Ted', 'Comedy', 2, 3.0)";
                String m5 = "INSERT OR IGNORE INTO movie(movieID, title, genre, stock, avgRating) " +
                        "VALUES (56789, 'La La Land', 'Romance', 1, 5.0)";
                insertMovie(m1);
                insertMovie(m2);
                insertMovie(m3);
                insertMovie(m4);
                insertMovie(m5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void createTable(String createTable) { //use statement to create table from SQL statement in string
        try {
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void insertMovie(String insertMovie) { //use statement to insert into movie table using SQL statement from string
       try{
           statement.executeUpdate(insertMovie);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
    public void insertCustomer(String insertCustomer) {

    }
}



