package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.Customer;
import com.example.cs157proj.dataObjects.DataHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private PasswordField passwordField;

    private DataHandler dataHandler;

    public SignUpController() {
        dataHandler = new DataHandler();
    }

    @FXML
    public void signUp() {
        // Get the values from the input fields
        String username = usernameField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        String ageText = ageField.getText();

        // Validate that age is a valid integer
        int age = 0;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            showError("Age must be a valid number");
            return;
        }

        // Ensure the fields are not empty
        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || ageText.isEmpty()) {
            showError("Please fill all the fields.");
            return;
        }

        // Create a new Customer object
        Customer newCustomer = new Customer(username, password, name, age);

        // Insert the customer into the database using DataHandler
        dataHandler.addCustomer(newCustomer);

        // Show a success message or navigate to the next screen (if needed)
        showSuccess("Account created successfully!");

        // After successful sign-up, navigate to main-page.fxml
        try {
            navigateToMainPage();
        } catch (IOException e) {
            // Handle the IOException if loading the main-page.fxml fails
            System.err.println("Error loading main-page.fxml: " + e.getMessage());
            // Optionally, you can show a user-friendly message to the user here
        }
    }

    private void navigateToMainPage() throws IOException {
        // Load the main-page.fxml
        Parent mainPage = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/main-page.fxml"));

        // Get the current stage (window)
        Stage stage = (Stage) usernameField.getScene().getWindow();

        // Set the new scene to the stage (window)
        stage.setScene(new Scene(mainPage));
        stage.show();
    }

    // Helper method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show success messages
    private void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
