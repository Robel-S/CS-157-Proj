package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.DataHandler;
import com.example.cs157proj.dataObjects.Rental;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RentalPageController implements Initializable {
    @FXML
    private TableView<Rental> rentalTable;

    @FXML
    private TableColumn<Rental, String> titleCol;

    @FXML
    private TableColumn<Rental, Integer> dueDateCol;

    @FXML
    private TableColumn<Rental, Void> returnCol;

    private DataHandler dataHandler;
    private final UsernameHolder username = UsernameHolder.getInstance();
    private ArrayList<Rental> rentals;

    @Override //on page start the rentalTable is populated with values from the rental table in the database
    public void initialize(URL location, ResourceBundle resources){
        dataHandler = new DataHandler();
        //assigns each column an attribute to take in from the rental objects
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        loadRentals();
        addButtons();

    }
    //populates rental tables with the existing rentals of the current user that is logged in
    public void loadRentals(){
        rentals = dataHandler.loadUserRentals(username.getUsername());
        rentalTable.setItems(FXCollections.observableArrayList(rentals));
    }
    //method to add return buttons to the last column of the table
    private void addButtons() {
        //creates cell factory object that defines how the column is set up
        //specifies that column will hold a movie object and wont display a specific datatype hence void
        Callback<TableColumn<Rental, Void>, TableCell<Rental, Void>> cellFactory = new Callback<>() {
            //calls following code once for each row the table has
            @Override
            public TableCell<Rental, Void> call(final TableColumn<Rental, Void> param) {
                //creates a table cell subclass that each has its own return button
                return new TableCell<>() {
                    private final Button returnBtn = new Button("Return");
                    {
                        returnBtn.setOnAction(event -> {
                            //when return button is pressed gets the rental object of the row the button is in
                            Rental rental = getTableView().getItems().get(getIndex());
                            //calls methode to delete specific rental and allerts the user if the delte was succesful or not
                            if(dataHandler.deleteRental(rental.getUsername(),rental.getMovieID())){
                                alert("return succesful", "SUCESS");
                            }
                            else{
                                alert("return failed", "ERROR");
                            }
                            loadRentals();
                        });
                    }
                    //places the button objects that were created inside the cell
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : returnBtn);
                    }
                };
            }
        };
        //sets the return column cell factory to the cellfactory object we created
        returnCol.setCellFactory(cellFactory);
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
    public void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/main-page.fxml"));
            Stage stage = (Stage) rentalTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
