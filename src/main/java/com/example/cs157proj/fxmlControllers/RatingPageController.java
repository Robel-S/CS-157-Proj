package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.DataHandler;
import com.example.cs157proj.dataObjects.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RatingPageController {

    @FXML
    private ComboBox<Movie> movieComboBox;

    @FXML
    private Spinner<Double> ratingSpinner;

    @FXML
    private Label statusLabel;

    private final DataHandler dataHandler = new DataHandler();

    @FXML
    public void initialize() {
        String username = UsernameHolder.getInstance().getUsername();
        ArrayList<Movie> unratedMovies = dataHandler.getUnratedRentedMovies(username);
        ObservableList<Movie> movieList = FXCollections.observableArrayList(unratedMovies);
        movieComboBox.setItems(movieList);

        // Configure spinner
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 5.0, 3.0, 0.1);
        ratingSpinner.setValueFactory(valueFactory);
    }

    @FXML
    public void submitRating(ActionEvent event) {
        Movie selectedMovie = movieComboBox.getValue();
        if (selectedMovie == null) {
            statusLabel.setText("Please select a movie.");
            return;
        }

        double rating = ratingSpinner.getValue();
        String username = UsernameHolder.getInstance().getUsername();

        // Safety check to ensure the user hasn’t already rated this movie
        if (dataHandler.hasUserRatedMovie(username, selectedMovie.getMovieID())) {
            statusLabel.setText("You have already rated this movie.");
            return;
        }

        dataHandler.submitRating(username, selectedMovie.getMovieID(), rating);
        statusLabel.setText("Rating submitted successfully!");

        // Remove the rated movie from the ComboBox
        movieComboBox.getItems().remove(selectedMovie);
    }


    @FXML
    public void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/main-page.fxml"));
            Stage stage = (Stage) movieComboBox.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
