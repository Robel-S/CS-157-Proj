package com.example.cs157proj;

import com.example.cs157proj.dataObjects.DataHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Startup extends Application {

    private Scene mainScene;
    private Scene idScene;

    @Override
    public void start(Stage stage) throws IOException {
        DataHandler dataHandler = new DataHandler();
        dataHandler.initialize();
        Scene scene = getWelcomeScene();
        stage.setTitle("FilmBox!");
        stage.setScene(scene);
        stage.show();
    }
    public Scene getMainScene() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Startup.class.getResource("main-page.fxml"));
        mainScene = new Scene(fxmlLoader.load(), 600, 400);
        return mainScene;
    }
    public Scene getWelcomeScene() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Startup.class.getResource("welcome-page.fxml"));
        mainScene = new Scene(fxmlLoader.load(), 600, 400);
        return mainScene;
    }
//    public Scene getIDScene() throws IOException{
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("id-page.fxml"));
//        idScene = new Scene(fxmlLoader.load(), 600, 400);
//        return idScene;
//    }
    public static void main(String[] args) {
        launch();
    }
}