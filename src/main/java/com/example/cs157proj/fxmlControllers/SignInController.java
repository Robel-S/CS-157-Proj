package com.example.cs157proj.fxmlControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.cs157proj.dataObjects.DataHandler;

import java.io.IOException;

public class SignInController {

    private DataHandler dataHandler;

    @FXML
    private javafx.scene.control.TextField usernameField;
    @FXML
    private javafx.scene.control.PasswordField passwordField;

    public SignInController() {
        dataHandler = new DataHandler();  // Initialize DataHandler
    }

    @FXML
    private void handleSignIn(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate credentials using DataHandler
        if (dataHandler.validateCustomer(username, password)) {
            // If credentials are valid, load the main page
            Parent mainPage = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/main-page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainPage));
            stage.show();
        } else {
            // If invalid credentials, display an error message
            System.out.println("Invalid username or password");
        }
    }
}
