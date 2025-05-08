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
