package com.comp2042;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Panel displayed when the game is over
 */
public class GameOverPanel extends BorderPane {

    /**
     * Creates a new game over panel with centered text
     */
    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }

}