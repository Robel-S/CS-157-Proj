package com.example.cs157proj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Scene startScene;

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = getStartScene();
        stage.setTitle("FilmBox!");
        stage.setScene(scene);
        stage.show();
    }
    public Scene getStartScene() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        startScene= new Scene(fxmlLoader.load(), 600, 400);
        return startScene;
    }
    public static void main(String[] args) {
        launch();
    }
}