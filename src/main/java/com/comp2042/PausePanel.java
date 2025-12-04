package com.comp2042;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Panel displayed when the game is paused
 */
public class PausePanel extends BorderPane {

    /**
     * Creates a new pause panel with centered text
     */
    public PausePanel() {
        final Label pauseLabel = new Label("PAUSED");
        pauseLabel.getStyleClass().add("gameOverStyle");
        setCenter(pauseLabel);
    }
}