package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.Customer;
import com.example.cs157proj.dataObjects.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
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
    private final UsernameHolder user = UsernameHolder.getInstance();

    @FXML
    public void signUp() {
        // Get the values from the input fields
        String username = usernameField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        String ageText = ageField.getText();

        // Validate that no field is empty
        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || ageText.isEmpty()) {
            showError("Please fill all the fields.");
            return;
        }

        // Validate username length
        if (username.length() > 10) {
            showError("Username must be 10 characters or less.");
            return;
        }

        // Validate name length
        if (name.length() > 20) {
            showError("Name must be 20 characters or less.");
            return;
        }

        // Validate password length
        if (password.length() > 50) {
            showError("Password must be 50 characters or less.");
            return;
        }

        // Validate age is an integer and within range
        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age < 1 || age > 200) {
                showError("Age must be between 1 and 200.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Age must be a valid number.");
            return;
        }

        // Create a new Customer object
        Customer newCustomer = new Customer(username, password, name, age);

        // Insert the customer into the database using DataHandler
        dataHandler.addCustomer(newCustomer);

        // Show a success message
        showSuccess("Account created successfully!");

        // Set the username in the singleton
        user.setUsername(username);
        System.out.println(user.getUsername() + " is signed in.");

        // After successful sign-up, navigate to main-page.fxml
        try {
            navigateToMainPage();
        } catch (IOException e) {
            System.err.println("Error loading main-page.fxml: " + e.getMessage());
            showError("Failed to load the main page. Please try again.");
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

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent welcomePage = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/welcome-page.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(welcomePage));
        stage.show();
    }

}
