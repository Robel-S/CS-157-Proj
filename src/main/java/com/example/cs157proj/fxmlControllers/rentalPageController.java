package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.DataHandler;
import com.example.cs157proj.dataObjects.Movie;
import com.example.cs157proj.dataObjects.Rental;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class rentalPageController implements Initializable {
    @FXML
    private TableView<Rental> rentalTable;

    @FXML
    private TableColumn<Rental, String> titleCol;

    @FXML
    private TableColumn<Rental, Integer> dueDateCol;

    @FXML
    private TableColumn<Rental, Void> returnCol;

    private DataHandler dataHandler;
    private final usernameHolder username = usernameHolder.getInstance();
    private ArrayList<Rental> rentals;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        dataHandler = new DataHandler();
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        loadRentals();
        addButtons();

    }
    public void loadRentals(){
        rentals = dataHandler.loadUserRentals(username.getUsername());
        rentalTable.setItems(FXCollections.observableArrayList(rentals));
    }
    //code to add return buttons to the last column of the table
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
                            Rental rental = getTableView().getItems().get(getIndex());
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
        //sets the rent column cell factory to the cellfactory object we created
        returnCol.setCellFactory(cellFactory);
    }
    private void alert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
