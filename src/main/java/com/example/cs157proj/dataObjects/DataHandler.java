package com.example.cs157proj.dataObjects;

import com.example.cs157proj.model.ConnectDB;
import com.example.cs157proj.model.DatabaseController;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataHandler {
    private ConnectDB db;
    private Connection connection;
    private static Statement statement;
    LocalDate date = LocalDate.now();

    public DataHandler() {
        //connects to database then stores the connection and uses it to create a statement variable
        db = new ConnectDB();
        connection = db.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    //creates database and initializes schema files
    public void initialize(){
        DatabaseController databaseController = new DatabaseController();
    }
    // Existing method for loading movies
    public ArrayList<Movie> loadMovies() {
        updateMovies();
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            //query to load all attributes from movie table in order by title attribute
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movie ORDER BY title");
            while (resultSet.next()) {
                //each attribute gets put in a variable then stored in a movie object an added to a movie array
                int movieID = resultSet.getInt("movieID"); //gets movieID column from current row
                String movieTitle = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                int stock = resultSet.getInt("stock");
                double avgRating = resultSet.getDouble("avgRating");
                movies.add(new Movie(movieID, movieTitle, genre, stock, avgRating));
            }
        } catch (SQLException e) {
            System.out.println("error loading movies");
            throw new RuntimeException(e);
        }
        //returns array of movie objects
        return movies;
    }
    public ArrayList<Movie> loadMoviesByGenre(String genre) {
        updateMovies();
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            //query to load all attributes from movie table that have a specified genre in order by title attribute
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movie WHERE genre = '" + genre +"' ORDER BY title");
            while (resultSet.next()) {
                //each attribute gets put in a variable then stored in a movie object an added to a movie array
                int movieID = resultSet.getInt("movieID"); //gets movieID column from current row
                String movieTitle = resultSet.getString("title");
                genre = resultSet.getString("genre");
                int stock = resultSet.getInt("stock");
                double avgRating = resultSet.getDouble("avgRating");
                movies.add(new Movie(movieID, movieTitle, genre, stock, avgRating));
            }
        } catch (SQLException e) {
            System.out.println("error loading movies");
            throw new RuntimeException(e);
        }
        //returns array of movie objects
        return movies;
    }
    public ArrayList<Movie> loadMoviesBySearch(String search) {
        updateMovies();
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            //query to load all attributes from movie table that have a specified genre in order by title attribute
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movie WHERE title LIKE '%" + search +"%' ORDER BY title");
            while (resultSet.next()) {
                //each attribute gets put in a variable then stored in a movie object an added to a movie array
                int movieID = resultSet.getInt("movieID"); //gets movieID column from current row
                String movieTitle = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                int stock = resultSet.getInt("stock");
                double avgRating = resultSet.getDouble("avgRating");
                movies.add(new Movie(movieID, movieTitle, genre, stock, avgRating));
            }
        } catch (SQLException e) {
            System.out.println("error loading movies");
            throw new RuntimeException(e);
        }
        //returns array of movie objects
        return movies;
    }

    public void updateMovies() {
        String query  = "UPDATE movie SET avgRating = COALESCE((SELECT AVG(r.rating) FROM rating r WHERE r.movieID = movie.movieID), -1);";
        try{
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("error updating movie");
            throw new RuntimeException(e);
        }
    }
    // Existing method for loading genres
    public ArrayList<String> loadGenres() {
        ArrayList<String> genres = new ArrayList<>();
        try {
            //query to get all genres from movie table
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT genre FROM movie ORDER BY genre");
            while (resultSet.next()) {
                //adds each genre to an arraylist one by one from resultSet
                genres.add(resultSet.getString("genre"));
            }
        } catch (SQLException e) {
            System.out.println("error loading genres");
            throw new RuntimeException(e);
        }
        return genres;
    }
    //stores the rentals of a specific user in an Array and returns it
    public ArrayList<Rental> loadUserRentals(String username){
        ArrayList<Rental> rentals = new ArrayList<>();
        try {
            //query to get all rentals from the rental table that have a given username ordered by due date
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rental WHERE username = '" + username + "' ORDER BY dueDate");
            while (resultSet.next()) {
                //adds each rental into an ArrayList one by one from the resultSet
                int movieID = resultSet.getInt("movieID");
                String dueDate = resultSet.getString("dueDate");
                rentals.add(new Rental(username, movieID, dueDate));
            }
        } catch (SQLException e) {
            System.out.println("error loading user rentals");
            throw new RuntimeException(e);
        }
        return rentals;
    }

    //method to see if user has already rented a specific movie
    public boolean rentalExists(String username, int movieID){
        //query to get row from rental table with a specfic username and movieID
        String query = "SELECT * FROM rental WHERE username = ? AND movieID = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setInt(2, movieID);
            ResultSet rs = stmt.executeQuery();
            //checks if the resultSet has a row if it does that means the user has already rented the movie and returns true
            if(!rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("error checking rental");
            throw new RuntimeException(e);
        }
        //if the resultSet doesnt have a row it means that the user hasn't rented the movie and returns false
        return false;
    }
    //inserts a row into the rentalTable based on given username and movieID and substracts the stock from that movie
    public boolean insertRental(String username, int movieID){
        //inserts a row into the rental table based on given values
        String update = "INSERT INTO rental(username, movieID, dueDate) VALUES (?,?,?)";
        //subtracts one from the stock of a movie based on given movieID
        String update2 = "UPDATE movie SET stock = stock - 1 WHERE movieID = ?";
        //creates a date variable that stores a date two wekks away from the curent date
        LocalDate twoWeeks = date.plusWeeks(2);
        try{
            PreparedStatement stmt = connection.prepareStatement(update);
            PreparedStatement stmt2 = connection.prepareStatement(update2);
            stmt.setString(1, username);
            stmt.setInt(2, movieID);
            stmt.setString(3, twoWeeks.toString());
            stmt2.setInt(1, movieID);
            //sets auto commit to false and executes 2 updates and commits only if both of them go through
            connection.setAutoCommit(false);
            stmt.executeUpdate();
            stmt2.executeUpdate();
            connection.commit();
            //sets autocommit back to true and returns true if transaction goes through
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            try {
                //rolls back updates if one of them fails, sets auto commit to true then returns false
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            } catch (SQLException ex) {
                System.out.println("rollback failed");
                throw new RuntimeException(ex);
            }
        }
    }
    //gets title of movie from a rental's movieID
    public String getRentalTitle(int movieID){
        //query to get title of a movie based on the given movieID
        String query = "SELECT title FROM movie WHERE movieID = ?";
        String title = "empty";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, movieID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                //since movieID is a primary key in the movie table only one row is returned which is stored in title and returned
                title = rs.getString("title");
            }
        } catch (SQLException e) {
            System.out.println("error getting rental title");
            throw new RuntimeException(e);
        }
        return title;
    }
    //Method to delete a rental from the Database
    public boolean deleteRental(String username, int movieID){
        //deletes a row in the rental table based on given values
        String update = "DELETE FROM rental WHERE username = ? AND movieID = ?";
        //subtracts one from the stock of a movie based on given movieID
        String update2 = "UPDATE movie SET stock = stock + 1 WHERE movieID = ?";
        try{
            PreparedStatement statement=  connection.prepareStatement(update);
            PreparedStatement statement2=  connection.prepareStatement(update2);
            statement.setString(1, username);
            statement.setInt(2, movieID);
            statement2.setInt(1, movieID);
            //sets auto commit to false and executes 2 updates and commits only if both of them go through
            connection.setAutoCommit(false);
            statement.executeUpdate();
            statement2.executeUpdate();
            connection.commit();
            //sets autocommit back to true and returns true if transaction goes through
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                //rolls back updates if one of them fails, sets auto commit to true then returns false
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println(e.getMessage());
                return false;
            } catch (SQLException ex) {
                System.out.println("rollback failed");
                throw new RuntimeException(ex);
            }
        }
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


    public void closeConnection() {
        db.closeConnection();
    }
    // Get list of movies the user rented but hasn't rated yet
    public ArrayList<Movie> getUnratedRentedMovies(String username) {
        ArrayList<Movie> movies = new ArrayList<>();
        String query = "SELECT m.* FROM movie m " +
                "JOIN rental r ON m.movieID = r.movieID " +
                "WHERE r.username = ? AND NOT EXISTS (" +
                "SELECT 1 FROM rating rt WHERE rt.username = ? AND rt.movieID = m.movieID)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movieID"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("stock"),
                        rs.getDouble("avgRating")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching unrated rented movies", e);
        }
        return movies;
    }

    // Submit a rating
    public void submitRating(String username, int movieID, double rating) {
        String insert = "INSERT INTO rating (username, movieID, rating) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insert)) {
            stmt.setString(1, username);
            stmt.setInt(2, movieID);
            stmt.setDouble(3, rating);
            stmt.executeUpdate();
            updateAverageRating(movieID);
        } catch (SQLException e) {
            throw new RuntimeException("Error submitting rating", e);
        }
    }

    // Update average rating after new rating submitted
    private void updateAverageRating(int movieID) {
        String update = "UPDATE movie SET avgRating = (SELECT ROUND(AVG(rating), 1) FROM rating WHERE movieID = ?) WHERE movieID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(update)) {
            stmt.setInt(1, movieID);
            stmt.setInt(2, movieID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating average rating", e);
        }
    }

    // Checks if a user has already rated a specific movie
    public boolean hasUserRatedMovie(String username, int movieID) {
        String query = "SELECT 1 FROM rating WHERE username = ? AND movieID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setInt(2, movieID);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if there's already a rating
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if user has rated movie", e);
        }
    }

}
