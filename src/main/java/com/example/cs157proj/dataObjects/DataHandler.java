package com.example.cs157proj.dataObjects;

import com.example.cs157proj.model.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataHandler {
    private ConnectDB db;
    private Connection connection;
    private static Statement statement;

    public DataHandler(){
        db = new ConnectDB();
        connection = db.getConnection();
        try {
            statement  = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Movie> loadMovies(){
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
    public static ArrayList<String> loadGenres(){
        ArrayList<String> genres = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT genre FROM movie ORDER BY genre");
            while(resultSet.next()){
                genres.add(resultSet.getString("genre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genres;
    }

}
