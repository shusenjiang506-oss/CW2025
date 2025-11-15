package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

// Extract constants, remove unnecessary ResourceBundle, improve naming, add validation

public class Main extends Application {

    private static final int WINDOW_WIDTH = 234;
    private static final int WINDOW_HEIGHT = 510;
    private static final String WINDOW_TITLE = "TetrisJFX";

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        if (location == null) {
            throw new IllegalStateException("Cannot find gameLayout.fxml");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        GuiController controller = fxmlLoader.getController();

        primaryStage.setTitle(WINDOW_TITLE);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        new GameController(controller);
    }

    public static void main(String[] args) {
        launch(args);
    }
}