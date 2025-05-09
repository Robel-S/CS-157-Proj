package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.Movie;
import com.example.cs157proj.dataObjects.DataHandler;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class mainPageController implements Initializable
{
    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private TableColumn<Movie, String> titleCol;
    @FXML
    private TableColumn<Movie, String> genreCol;
    @FXML
    private TableColumn<Movie, String> ratingCol;
    @FXML
    private TableColumn<Movie, Integer> stockCol;
    @FXML
    private TableColumn<Movie, Void> rentCol;
    @FXML
    private TextField searchBox;
    @FXML
    private ChoiceBox<String> genreFilter;

    private DataHandler dataHandler;
    private ArrayList<Movie> movies;
    private ArrayList<String> genres;
    private FilteredList<Movie> filteredMovies;
    private final usernameHolder username = usernameHolder.getInstance();

    @Override //on page start up table gets populated with the values from our movie database
    public void initialize(URL location, ResourceBundle resources){
        //creates data handler object to handle data from database
        dataHandler = new DataHandler();
        //assigns each column an attribute to take in from the movie objects
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("avgRating"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        loadMovies();
        addButtons();
        //creates a filteredList based on the ArrayList of movies
        filteredMovies = new FilteredList<Movie>(FXCollections.observableArrayList(movies),p -> true);
        loadGenres();
        genreFilter();
        searchMovies();
    }
    //loads arraylist of movies from database and gives them to the table
    public void loadMovies(){
        movies = dataHandler.loadMovies();
            movieTable.setItems(FXCollections.observableArrayList(movies));
    }
    //loads arraylist of genres and puts them into our genreFilterBox
    public void loadGenres(){
        genreFilter.getItems().add("Choose Genre");
        genres = dataHandler.loadGenres();
        genreFilter.setItems(FXCollections.observableArrayList(genres));
        // genreFilter.getItems().addFirst("Choose Genre");
        genreFilter.setValue("Choose Genre");
    }
    /*reads values inputted into the searchbar and compares the new value to the titles
    * of the movies in the movie ArrayList and those that match get filtered into the filteredList
    * and the table gets set to show the items in the filteredList
     */
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
    /* whenever a genre is chosen in the genreFilter choicebox we compare that genre
     * to the genres of the movies in our movieArrayList and those that match stay in the FilteredList
     * then the table gets set to show the items in the filteredList
     */
    public void genreFilter(){
        genreFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Choose Genre")) {
                loadMovies();
                movieTable.setItems(FXCollections.observableArrayList(movies));
            }
            else {
                movies = dataHandler.loadMoviesByGenre(newValue);
                movieTable.setItems(FXCollections.observableArrayList(movies));

            }
        });
    }
    //code to add rent buttons to the last column of the table
    private void addButtons() {
        //creates cell factory object that defines how the column is set up
        //specifies that column will hold a movie object and wont display a specific datatype hence void
        Callback<TableColumn<Movie, Void>, TableCell<Movie, Void>> cellFactory = new Callback<>() {
            //calls following code once for each row the table has
            @Override
            public TableCell<Movie, Void> call(final TableColumn<Movie, Void> param) {
                //creates a table cell subclass that each has its own rent button
                return new TableCell<>() {
                    private final Button rentBtn = new Button("Rent");
                    {
                        //once the button is pressed it gets the movie object of the row the button is in
                        rentBtn.setOnAction(event -> {
                            Movie movie = getTableView().getItems().get(getIndex());
                            /*checks if movie is out of stock then checks if user has already rented the movie
                             if they have then an alert pops up, if not then insert rental method is called.
                             */
                            if (movie.getStock() > 0) {
                                if(dataHandler.rentalExists(username.getUsername(), movie.getMovieID())) {
                                    if(dataHandler.insertRental(username.getUsername(), movie.getMovieID())) {
                                        alert("Rented " + movie.getTitle(), "rental succesful");
                                    }
                                    else {
                                        alert("Was unable to rent movie", "Rental Failed");
                                    }
                                    loadMovies();
                                }
                                else{
                                    alert("Already rented movie", "Rental Failed");
                                }
                            } else {
                                //if movie stock is 0 then out of stock alert pops up
                                alert("Movie is out Stock", "Rental Failed");
                            }
                        });
                    }
                    //places the button objects that were created inside the cell
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : rentBtn);
                    }
                };
            }
        };
        //sets the rent column cell factory to the cellfactory object we created
        rentCol.setCellFactory(cellFactory);
    }
    //method to create an Alert with the given message and title
    private void alert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        // Clear the current user
        // usernameHolder.getInstance().clearUsername();
        System.out.println("Trying to log out");
        // Navigate back to the welcome page
        Parent welcomePage = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/welcome-page.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(welcomePage));
        stage.show();

        // Print a message for confirmation
        System.out.println("Logged out successfully");
    }

    @FXML //method to navigate to rental page
    public void navigateToRentalPage(ActionEvent event) throws IOException {
        Parent rentalPage = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/rental-page.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(rentalPage));
        stage.show();

    }
}
