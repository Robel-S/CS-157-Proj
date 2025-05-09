package com.example.cs157proj.model;
import java.sql.*;

public class ConnectDB {

    private static Connection connection;

    //method to get connection to SQLite database
    public static Connection getConnection(){
        final String dbName = "jdbc:sqlite:RentalDB.db";
        //checks if we already have a connection before making a new one
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(dbName);
                System.out.println("Connected to database");
            } catch (SQLException e) {
                System.out.println("Error connecting to database");
                e.printStackTrace();
            }
        }
        return connection;
    }

    //closes connection to the database
    public static void closeConnection(){
        try{
            if(connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
            } catch (SQLException e){
                System.out.println("Error closing connection");
                e.printStackTrace();
            }
        }
    }

