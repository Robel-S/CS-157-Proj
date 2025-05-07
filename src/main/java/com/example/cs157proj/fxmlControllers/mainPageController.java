package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.Movie;
import com.example.cs157proj.dataObjects.DataHandler;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class mainPageController implements Initializable
{
    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private TableColumn<Movie, String> titleCol;
    @FXML
    private TableColumn<Movie, String> genreCol;
    @FXML
    private TableColumn<Movie, Double> ratingCol;
    @FXML
    private TableColumn<Movie, Integer> stockCol;
    @FXML
    private TextField searchBox;
    @FXML
    private ChoiceBox<String> genreFilter;

    private DataHandler dataHandler;
    private ArrayList<Movie> movies;
    private ArrayList<String> genres;
    private FilteredList<Movie> filteredMovies;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        dataHandler = new DataHandler();
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("avgRating"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        movies = dataHandler.loadMovies();
        genres = dataHandler.loadGenres();
        loadMovies();
        filteredMovies = new FilteredList<Movie>(FXCollections.observableArrayList(movies),p -> true);
        loadGenres();
        genreFilter();
    }
    public void loadMovies(){

        movieTable.getItems().addAll(movies);
    }
    public void loadGenres(){
        genreFilter.getItems().addAll(genres);
        genreFilter.getItems().addFirst("Choose Genre");
        genreFilter.setValue("Choose Genre");
    }
    public void searchMovies(){
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMovies.setPredicate(movie -> {
                if(newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(movie.getTitle().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else return false;
            });
        });
        movieTable.setItems(filteredMovies);
    }
    public void genreFilter(){
        FilteredList<Movie> filteredGenres = new FilteredList<Movie>(FXCollections.observableArrayList(filteredMovies));
        genreFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("Choose Genre"))
                filteredGenres.setPredicate(p-> true);
            else {
                filteredGenres.setPredicate(genre -> genre.getGenre().equals(newValue));
            }
        });
        movieTable.setItems(filteredGenres);
    }
}
