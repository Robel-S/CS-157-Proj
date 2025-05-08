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

public class SignInController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private DataHandler dataHandler;

    public SignInController() {
        dataHandler = new DataHandler();
    }
    private final usernameHolder user = usernameHolder.getInstance();

    @FXML
    public void signIn(ActionEvent event) throws IOException {
        // Get the values from the input fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Call the method to get customer by username and password
        Customer customer = dataHandler.getCustomerByUsernameAndPassword(username, password);

        // Check if customer exists
        if (customer != null) {
            // If the customer exists
            // Show a success message or navigate to the next screen (if needed)
            showSuccess("Account created successfully!");

            //sets usernameController to signed-in username so it can be used in other pages
            user.setUsername(username);

            // navigate to the main-page.fxml
            navigateToMainPage();
        } else {
            showError("Invalid username or password!");
            System.out.println("customer was null");
            System.out.println("Username we got: " + customer.getUsername() + ", pasword: " + customer.getPassword());
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

}
