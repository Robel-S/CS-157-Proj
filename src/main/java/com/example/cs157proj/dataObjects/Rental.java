package com.example.cs157proj.dataObjects;

public class Rental {
    private String username;
    private int movieID;
    private String dueDate;
    private String title;

    public Rental(String username, int movieID, String dueDate) {
        this.username = username;
        this.movieID = movieID;
        this.dueDate = dueDate;
        DataHandler dataHandler = new DataHandler();
        this.title = dataHandler.getRentalTitle(movieID);
    }

    public String getUsername() {
        return username;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTitle() {return title;}

}
