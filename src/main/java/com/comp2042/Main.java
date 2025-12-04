package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main application class that launches the Tetris game
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application and initializes the game
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if FXML loading fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 300, 510);
        primaryStage.setScene(scene);
        primaryStage.show();
        new GameController(c);
    }

    /**
     * Main entry point of the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}