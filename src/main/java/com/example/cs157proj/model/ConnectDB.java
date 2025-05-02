package com.example.cs157proj.model;
import java.sql.*;

public class ConnectDB {

    private Connection connection;

    public ConnectDB(){
        final String dbName = "jdbc:sqlite:RentalDB.db";
        try{
            connection = DriverManager.getConnection(dbName);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
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

