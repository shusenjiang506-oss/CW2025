package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ModeHintPanel extends BorderPane {

    public ModeHintPanel() {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("SELECT GAME MODE");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #FFD700; -fx-font-weight: bold;");

        Label hint1 = new Label("Press 1: Classic Mode");
        hint1.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

        Label hint2 = new Label("Press 2: Timed Mode (2 min)");
        hint2.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

        Label hint3 = new Label("Press N to start");
        hint3.setStyle("-fx-font-size: 11px; -fx-text-fill: yellow; -fx-padding: 10 0 0 0;");

        container.getChildren().addAll(titleLabel, hint1, hint2, hint3);
        setCenter(container);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.85);");
    }
}