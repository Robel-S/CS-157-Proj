package com.example.cs157proj.model;
import java.sql.*;

public class ConnectDB {

    private static Connection connection;

    public static Connection getConnection(){
        final String dbName = "jdbc:sqlite:RentalDB.db";
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

