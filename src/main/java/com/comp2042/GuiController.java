package com.comp2042;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;
    //New Feature 2: difficulty level system
    private static final int BASE_GAME_SPEED_MS = 500;
    private static final int MIN_GAME_SPEED_MS = 100;
    //New Feature 3: Timed Mode
    private static final int TIMED_MODE_SECONDS = 120;
    private static final int LAYOUT_Y_OFFSET = -42;
    private static final int DISPLAY_ROW_OFFSET = 2;

    private static final Color[] BRICK_COLORS = {
            Color.TRANSPARENT,
            Color.AQUA,
            Color.BLUEVIOLET,
            Color.DARKGREEN,
            Color.YELLOW,
            Color.RED,
            Color.BEIGE,
            Color.BURLYWOOD
    };

    @FXML
    private Label levelLabel;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    // New Feature 1: P - Pause
    private PausePanel pausePanel;
    //New Feature 3: Timed Mode
    private ModeHintPanel modeHintPanel;
    private Timeline timerTimeline;
    private int currentTimeSeconds = 0;
    private GameMode selectedMode = GameMode.CLASSIC;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    //New Feature 2: difficulty level system
    public void updateGameSpeed(int level) {
        int newSpeed = Math.max(
                BASE_GAME_SPEED_MS - (level - 1) * 40,
                MIN_GAME_SPEED_MS
        );

        NotificationPanel levelUpPanel = new NotificationPanel("Level " + level + "!");
        groupNotification.getChildren().add(levelUpPanel);
        levelUpPanel.showScore(groupNotification.getChildren());

        timeLine.stop();
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(newSpeed),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                }

                //New Feature 3: Timed Mode - Mode selection
                if (keyEvent.getCode() == KeyCode.DIGIT1) {
                    selectedMode = GameMode.CLASSIC;
                    System.out.println("Selected: Classic Mode");
                    keyEvent.consume();
                }

                if (keyEvent.getCode() == KeyCode.DIGIT2) {
                    selectedMode = GameMode.TIMED;
                    System.out.println("Selected: Timed Mode (2 minutes)");
                    keyEvent.consume();
                }

                // New Feature 1: P - Pause
                if (keyEvent.getCode() == KeyCode.P) {
                    togglePause();
                    keyEvent.consume();
                }

                if (keyEvent.getCode() == KeyCode.N) {
                    //New Feature 3: Timed Mode
                    modeHintPanel.setVisible(false);
                    groupNotification.getChildren().remove(modeHintPanel);
                    isPause.setValue(Boolean.FALSE);
                    newGame(null);
                }
            }
        });

        // New Feature 1: P - Pause
        pausePanel = new PausePanel();
        pausePanel.setVisible(false);

        //New Feature 3: Timed Mode
        modeHintPanel = new ModeHintPanel();
        modeHintPanel.setVisible(true);
        groupNotification.getChildren().add(modeHintPanel);

        gameOverPanel.setVisible(false);

        isPause.setValue(Boolean.TRUE);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }
    // New Feature 1: P - Pause
    private void togglePause() {
        if (isGameOver.getValue() == Boolean.TRUE) {
            return;
        }

        if (isPause.getValue() == Boolean.TRUE) {
            timeLine.play();
            //New Feature 3: Timed Mode
            if (timerTimeline != null) {
                timerTimeline.play();
            }
            pausePanel.setVisible(false);
            isPause.setValue(Boolean.FALSE);
        } else {
            timeLine.pause();
            //New Feature 3: Timed Mode
            if (timerTimeline != null) {
                timerTimeline.pause();
            }
            pausePanel.setVisible(true);
            isPause.setValue(Boolean.TRUE);
        }
        gamePanel.requestFocus();
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = DISPLAY_ROW_OFFSET; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - DISPLAY_ROW_OFFSET);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(LAYOUT_Y_OFFSET + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);


        timeLine = new Timeline(new KeyFrame(
                Duration.millis(BASE_GAME_SPEED_MS),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private Paint getFillColor(int colorIndex) {
        if (colorIndex >= 0 && colorIndex < BRICK_COLORS.length) {
            return BRICK_COLORS[colorIndex];
        }
        return Color.WHITE;
    }


    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(LAYOUT_Y_OFFSET + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = DISPLAY_ROW_OFFSET; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
    }

    public void gameOver() {
        timeLine.stop();
        //New Feature 3: Timed Mode
        if (timerTimeline != null) {
            timerTimeline.stop();
        }
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        //New Feature 3: Timed Mode
        if (timerTimeline != null) {
            timerTimeline.stop();
        }

        gameOverPanel.setVisible(false);
        // New Feature 1: P - Pause
        pausePanel.setVisible(false);
        //New Feature 3: Timed Mode
        modeHintPanel.setVisible(false);

        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);

        //New Feature 3: Timed Mode - Start timer if timed mode selected
        if (selectedMode == GameMode.TIMED) {
            startTimer(TIMED_MODE_SECONDS);
        }
    }

    public void pauseGame(ActionEvent actionEvent) {
        // New Feature 1: P - Pause
        togglePause();
    }

    //New Feature 3: Timed Mode
    public void startTimer(int totalSeconds) {
        currentTimeSeconds = totalSeconds;

        if (timerTimeline != null) {
            timerTimeline.stop();
        }

        timerTimeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                event -> {
                    currentTimeSeconds--;
                    updateTimerDisplay(currentTimeSeconds);

                    if (currentTimeSeconds <= 0) {
                        timerTimeline.stop();
                        gameOver();
                    }
                }
        ));
        timerTimeline.setCycleCount(totalSeconds);
        timerTimeline.play();

        updateTimerDisplay(currentTimeSeconds);
    }

    private void updateTimerDisplay(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String timeText = String.format("Time: %d:%02d", minutes, secs);
        System.out.println(timeText);
    }
}