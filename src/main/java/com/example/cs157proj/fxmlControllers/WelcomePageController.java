package com.example.cs157proj.fxmlControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePageController {

    // Method for Sign Up button
    public void handleSignUp(ActionEvent event) throws IOException {
        // Load the sign-up page FXML
        Parent signUpPage = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/sign-up.fxml"));
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Set the new scene to the stage
        stage.setScene(new Scene(signUpPage));
        stage.show();
    }

    // Method for Sign In button
    public void handleSignIn(ActionEvent event) throws IOException {
        // Load the sign-in page FXML
        Parent signInPage = FXMLLoader.load(getClass().getResource("/com/example/cs157proj/sign-in.fxml"));
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Set the new scene to the stage
        stage.setScene(new Scene(signInPage));
        stage.show();
    }
}
