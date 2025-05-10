package com.example.cs157proj.fxmlControllers;

import com.example.cs157proj.dataObjects.Customer;
import com.example.cs157proj.dataObjects.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;

public class SignInController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private DataHandler dataHandler;

    public SignInController() {
        dataHandler = new DataHandler();
    }
    private final UsernameHolder user = UsernameHolder.getInstance();

    @FXML
    public void signIn(ActionEvent event) throws IOException {
        // Get the values from the input fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate that no field is empty
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill all the fields.");
            return;
        }

        // Validate username length
        if (username.length() > 10) {
            showError("Username must be 10 characters or less.");
            return;
        }

        // Validate password length
        if (password.length() > 50) {
            showError("Password must be 50 characters or less.");
            return;
        }

        // Call the method to get customer by username and password
        Customer customer = dataHandler.getCustomerByUsernameAndPassword(username, password);

        // Check if customer exists
        if (customer != null) {
            // If the customer exists, show a success message and navigate to the main page
            showSuccess("Account signed into successfully!");

            // Set username in the singleton
            user.setUsername(username);
            System.out.println(user.getUsername() + " is signed in.");

            // Navigate to the main-page.fxml
            try {
                navigateToMainPage();
            } catch (IOException e) {
                System.err.println("Error loading main-page.fxml: " + e.getMessage());
                showError("Failed to load the main page. Please try again.");
            }
        } else {
            showError("Invalid username or password!");
            System.out.println("Customer not found for username: " + username);
        }
    }


    // Navigate to the main-page.fxml
    private void navigateToMainPage() throws IOException {
        // Load the main-page.fxml
        Parent mainPage = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/main-page.fxml"));

        // Get the current stage (window)
        Stage stage = (Stage) usernameField.getScene().getWindow();

        // Set the new scene to the stage (window)
        stage.setScene(new Scene(mainPage));
        stage.show();
    }

    // Helper method to show success messages
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign in successful!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
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
