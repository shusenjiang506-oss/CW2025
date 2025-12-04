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

/**
 * Main GUI controller that handles view rendering and user input
 */
public class GuiController implements Initializable {

    /**
     * Size of each brick cell in pixels
     */
    private static final int BRICK_SIZE = 20;

    //New Feature 2: difficulty level system
    /**
     * Base game speed in milliseconds for level 1
     */
    private static final int BASE_GAME_SPEED_MS = 500;

    /**
     * Minimum game speed in milliseconds at highest level
     */
    private static final int MIN_GAME_SPEED_MS = 100;

    //New Feature 3: Timed Mode
    /**
     * Duration of timed mode in seconds
     */
    private static final int TIMED_MODE_SECONDS = 120;

    /**
     * Y-axis offset for brick panel layout
     */
    private static final int LAYOUT_Y_OFFSET = -42;

    /**
     * Row offset for display matrix to hide top rows
     */
    private static final int DISPLAY_ROW_OFFSET = 2;

    /**
     * Array of colors for different brick types
     */
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
    /**
     * Panel displayed when game is paused
     */
    private PausePanel pausePanel;

    //New Feature 3: Timed Mode
    /**
     * Panel showing mode selection hints
     */
    private ModeHintPanel modeHintPanel;

    /**
     * Timeline for countdown timer in timed mode
     */
    private Timeline timerTimeline;

    /**
     * Current remaining time in seconds for timed mode
     */
    private int currentTimeSeconds = 0;

    /**
     * Currently selected game mode
     */
    private GameMode selectedMode = GameMode.CLASSIC;

    //New Feature 5: UI Display
    /**
     * Label displaying current game mode
     */
    private Label modeLabel;

    /**
     * Label displaying current level
     */
    private Label levelDisplayLabel;

    /**
     * Label displaying current score
     */
    private Label scoreLabel;

    /**
     * Label displaying remaining time in timed mode
     */
    private Label timerLabel;

    /**
     * Matrix of rectangles representing the game board
     */
    private Rectangle[][] displayMatrix;

    /**
     * Listener for input events
     */
    private InputEventListener eventListener;

    /**
     * Matrix of rectangles representing the current brick
     */
    private Rectangle[][] rectangles;

    /**
     * Timeline controlling automatic brick descent
     */
    private Timeline timeLine;

    /**
     * Property tracking pause state
     */
    private final BooleanProperty isPause = new SimpleBooleanProperty();

    /**
     * Property tracking game over state
     */
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();


    // New Feature 4: hard landing method
    /**
     * Handles hard drop action where brick instantly drops to bottom
     *
     * @param event the move event
     */
    private void hardDrop(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = ((GameController)eventListener).onHardDropEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    //New Feature 2: difficulty level system
    /**
     * Updates game speed based on current level
     *
     * @param level the current difficulty level
     */
    public void updateGameSpeed(int level) {
        int newSpeed = Math.max(
                BASE_GAME_SPEED_MS - (level - 1) * 40,
                MIN_GAME_SPEED_MS
        );

        NotificationPanel levelUpPanel = new NotificationPanel("Level " + level + "!");
        groupNotification.getChildren().add(levelUpPanel);
        levelUpPanel.showScore(groupNotification.getChildren());

        //New Feature 5: UI Display - Update level
        if (levelDisplayLabel != null) {
            levelDisplayLabel.setText("Level: " + level);
        }

        timeLine.stop();
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(newSpeed),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

    }

    /**
     * Initializes the controller after FXML loading
     *
     * @param location URL location
     * @param resources resource bundle
     */
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

                    // New Feature 4: hard landing method
                    if (keyEvent.getCode() == KeyCode.SPACE) {
                        hardDrop(new MoveEvent(EventType.DOWN, EventSource.USER));
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

        //New Feature 5: UI Display
        createInfoLabels();
    }

    // New Feature 1: P - Pause
    /**
     * Toggles pause state of the game
     */
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

    /**
     * Initializes the game view with board matrix and initial brick
     *
     * @param boardMatrix the game board matrix
     * @param brick initial brick view data
     */
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

    /**
     * Gets the fill color for a brick based on color index
     *
     * @param colorIndex index of the color
     * @return the corresponding paint color
     */
    private Paint getFillColor(int colorIndex) {
        if (colorIndex >= 0 && colorIndex < BRICK_COLORS.length) {
            return BRICK_COLORS[colorIndex];
        }
        return Color.WHITE;
    }


    /**
     * Refreshes the brick display with updated view data
     *
     * @param brick updated brick view data
     */
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

    /**
     * Refreshes the game background with updated board state
     *
     * @param board the updated board matrix
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = DISPLAY_ROW_OFFSET; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Sets the visual properties of a rectangle based on color code
     *
     * @param color the color code
     * @param rectangle the rectangle to update
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    /**
     * Handles brick moving down and updates display
     *
     * @param event the move event
     */
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

    /**
     * Sets the event listener for input handling
     *
     * @param eventListener the event listener to set
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Binds score property to UI display
     *
     * @param integerProperty the score property to bind
     */
    public void bindScore(IntegerProperty integerProperty) {
        //New Feature 5: UI Display - Bind score
        if (scoreLabel != null) {
            integerProperty.addListener((obs, oldVal, newVal) -> {
                scoreLabel.setText("Score: " + newVal);
            });
        }
    }

    /**
     * Handles game over state
     */
    public void gameOver() {
        timeLine.stop();
        //New Feature 3: Timed Mode
        if (timerTimeline != null) {
            timerTimeline.stop();
        }
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Starts a new game
     *
     * @param actionEvent the action event triggering new game
     */
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

        //New Feature 5: UI Display - Update mode display
        if (modeLabel != null) {
            if (selectedMode == GameMode.CLASSIC) {
                modeLabel.setText("Mode: Classic");
                timerLabel.setVisible(false);
            } else {
                modeLabel.setText("Mode: Timed (2 min)");
                timerLabel.setVisible(true);
                timerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #FF6B6B; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 5;");
            }
        }

        //New Feature 5: UI Display - Reset displays
        if (levelDisplayLabel != null) {
            levelDisplayLabel.setText("Level: 1");
        }
        if (scoreLabel != null) {
            scoreLabel.setText("Score: 0");
        }

        //New Feature 3: Timed Mode - Start timer if timed mode selected
        if (selectedMode == GameMode.TIMED) {
            startTimer(TIMED_MODE_SECONDS);
        }
    }

    /**
     * Pauses the game
     *
     * @param actionEvent the action event
     */
    public void pauseGame(ActionEvent actionEvent) {
        // New Feature 1: P - Pause
        togglePause();
    }

    //New Feature 3: Timed Mode
    /**
     * Starts the countdown timer for timed mode
     *
     * @param totalSeconds total duration in seconds
     */
    public void startTimer(int totalSeconds) {
        currentTimeSeconds = totalSeconds;

        //New Feature 5: UI Display - Show timer
        if (timerLabel != null) {
            timerLabel.setVisible(true);
        }

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

    /**
     * Updates the timer display with remaining time
     *
     * @param seconds remaining seconds
     */
    private void updateTimerDisplay(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String timeText = String.format("Time: %d:%02d", minutes, secs);

        //New Feature 5: UI Display - Update timer display
        if (timerLabel != null) {
            timerLabel.setText(timeText);

            if (seconds <= 30) {
                timerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #FF0000; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 5; -fx-font-weight: bold;");
            }
        }

        System.out.println(timeText);
    }

    //New Feature 5: UI Display - Create info labels
    /**
     * Creates and initializes the information display labels
     */
    private void createInfoLabels() {
        modeLabel = new Label("Mode: Classic");
        modeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 5;");
        modeLabel.setLayoutX(10);
        modeLabel.setLayoutY(-180);
        groupNotification.getChildren().add(modeLabel);

        levelDisplayLabel = new Label("Level: 1");
        levelDisplayLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFD700; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 5;");
        levelDisplayLabel.setLayoutX(10);
        levelDisplayLabel.setLayoutY(-155);
        groupNotification.getChildren().add(levelDisplayLabel);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #00FF00; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 5;");
        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(-130);
        groupNotification.getChildren().add(scoreLabel);

        timerLabel = new Label("Time: 2:00");
        timerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #FF6B6B; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 5;");
        timerLabel.setLayoutX(10);
        timerLabel.setLayoutY(-105);
        timerLabel.setVisible(false);
        groupNotification.getChildren().add(timerLabel);
    }
}