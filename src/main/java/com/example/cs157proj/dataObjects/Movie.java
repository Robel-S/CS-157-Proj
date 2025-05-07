package com.example.cs157proj.dataObjects;

public class Movie {
    private int movieID;
    private String title;
    private String genre;
    private int stock;
    private double avgRating;
    public Movie(int movieID, String title, String genre, int stock, double avgRating) {
        this.movieID = movieID;
        this.title = title;
        this.genre = genre;
        this.stock = stock;
        this.avgRating = avgRating;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getStock() {
        return stock;
    }

    public double getAvgRating() {
        return avgRating;
    }


}
