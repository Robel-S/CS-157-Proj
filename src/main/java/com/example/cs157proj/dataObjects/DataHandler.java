package com.example.cs157proj.dataObjects;

import com.example.cs157proj.model.ConnectDB;
import java.sql.*;
import java.util.ArrayList;

public class DataHandler {
    private ConnectDB db;
    private Connection connection;
    private static Statement statement;

    public DataHandler() {
        db = new ConnectDB();
        connection = db.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Existing method for loading movies
    public static ArrayList<Movie> loadMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movie ORDER BY title");
            while (resultSet.next()) {
                int movieID = resultSet.getInt("movieID");
                String movieTitle = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                int stock = resultSet.getInt("stock");
                double avgRating = resultSet.getDouble("avgRating");
                movies.add(new Movie(movieID, movieTitle, genre, stock, avgRating));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    // Existing method for loading genres
    public static ArrayList<String> loadGenres() {
        ArrayList<String> genres = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT genre FROM movie ORDER BY genre");
            while (resultSet.next()) {
                genres.add(resultSet.getString("genre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genres;
    }
    public static ArrayList<Rental> loadUserRentals(String username){
        ArrayList<Rental> rentals = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rental WHERE username = '" + username + "'");
            while (resultSet.next()) {
                int movieID = resultSet.getInt("movieID");
                String user = resultSet.getString("username");
                String dueDate = resultSet.getString("dueDate");
                rentals.add(new Rental(user, movieID, dueDate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rentals;
    }
    public static void updateStock(int movieID){
        String query = "UPDATE movie SET stock = stock - 1 WHERE movieID = " + movieID;
        try{
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rentalTitle(int movieID){


    }

    // Method to add a new customer to the database
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (username, password, name, age) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getPassword());
            stmt.setString(3, customer.getName());
            stmt.setInt(4, customer.getAge());

            // Execute the insert operation
            stmt.executeUpdate();
            System.out.println("Customer added successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting customer into database");
            e.printStackTrace();
        }
    }

    public Customer getCustomerByUsernameAndPassword(String username, String password) {
        Customer customer = null;

        // Database query to check if username and password exist
        String sql = "SELECT * FROM customer WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the parameters (username and password) in the query
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // If a record is found, create a Customer object
            if (resultSet.next()) {
                System.out.println("Customer found!");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                // Create and return the Customer object
                customer = new Customer(username, password, name, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    // New method to validate customer credentials
    public boolean validateCustomer(String username, String password) {
        String sql = "SELECT * FROM customers WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();  // If a row is returned, the credentials are valid
        } catch (SQLException e) {
            System.out.println("Error validating customer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }




}
