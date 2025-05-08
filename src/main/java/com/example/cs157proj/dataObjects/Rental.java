package com.example.cs157proj.dataObjects;

public class Rental {
    private String username;
    private int movieID;
    private String dueDate;
    public Rental(String username, int movieID, String dueDate){
        this.username = username;
        this.movieID = movieID;
        this.dueDate = dueDate;
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

//    public String getMovieTitle(){
//
//    }
}
