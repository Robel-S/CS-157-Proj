package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.DataHandler;
import com.example.cs157proj.dataObjects.Rental;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<Rental, String> returnCol;

    private DataHandler dataHandler;
    private final usernameHolder username = usernameHolder.getInstance();
    private ArrayList<Rental> rentals;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        dataHandler = new DataHandler();
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        loadRentals();

    }
    public void loadRentals(){
        rentals = dataHandler.loadUserRentals("Robel");
        rentalTable.setItems(FXCollections.observableArrayList(rentals));
    }

}
