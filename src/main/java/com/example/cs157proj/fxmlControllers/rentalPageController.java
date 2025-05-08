package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.Rental;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class rentalPageController {
    @FXML
    private TableView<Rental> rentalTable;

    @FXML
    private TableColumn<Rental, String> titleCol;

    @FXML
    private TableColumn<Rental, Integer> dueDateCol;

    @FXML
    private TableColumn<Rental, String> returnCol;

    private ArrayList<Rental> rentals;



}
