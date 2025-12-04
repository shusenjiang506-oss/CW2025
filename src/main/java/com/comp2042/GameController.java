package com.comp2042;

/**
 * Main game controller that handles game logic and coordinates between board and view
 */
public class GameController implements InputEventListener {
    /**
     * Height of the game board in cells
     */
    private static final int BOARD_HEIGHT = 25;

    /**
     * Width of the game board in cells
     */
    private static final int BOARD_WIDTH = 10;

    /**
     * Score bonus for soft drop (user-initiated down movement)
     */
    private static final int SOFT_DROP_BONUS = 1;

    //New Feature 2: difficulty level system
    /**
     * Number of lines required to advance to next level
     */
    private static final int LINES_PER_LEVEL = 10;

    /**
     * Maximum difficulty level
     */
    private static final int MAX_LEVEL = 10;

    //private Board board = new SimpleBoard(25, 10);
    /**
     * The game board instance
     */
    private Board board = new SimpleBoard(BOARD_HEIGHT, BOARD_WIDTH);

    // New Feature 4: hard landing method
    /**
     * Score multiplier per row for hard drop
     */
    private static final int HARD_DROP_BONUS = 2;

    /**
     * GUI controller for view updates
     */
    private final GuiController viewGuiController;


    //New Feature 2: difficulty level system
    /**
     * Total number of lines cleared in current game
     */
    private int totalLinesCleared = 0;

    /**
     * Current difficulty level
     */
    private int currentLevel = 1;

    /**
     * Creates a new game controller and initializes the game
     *
     * @param c the GUI controller for view management
     */
    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        //New Feature 2: difficulty level system
        viewGuiController.updateGameSpeed(currentLevel);
    }

    /**
     * Handles down movement events
     *
     * @param event the move event containing source information
     * @return data about cleared rows and updated view
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
                //New Feature 2: difficulty level system
                totalLinesCleared += clearRow.getLinesRemoved();
                updateLevel();
            }

            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                //board.getScore().add(1);
                board.getScore().add(SOFT_DROP_BONUS);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Handles left movement events
     *
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Handles right movement events
     *
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Handles rotation events
     *
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    // New Feature 4: hard landing method
    /**
     * Handles hard drop events where brick instantly drops to bottom
     *
     * @param event the move event
     * @return data about cleared rows and updated view
     */
    public DownData onHardDropEvent(MoveEvent event) {
        int dropDistance = board.hardDrop();
        board.getScore().add(dropDistance * HARD_DROP_BONUS);

        board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();
        if (clearRow.getLinesRemoved() > 0) {
            board.getScore().add(clearRow.getScoreBonus());
        }

        if (board.createNewBrick()) {
            viewGuiController.gameOver();
        }

        viewGuiController.refreshGameBackground(board.getBoardMatrix());

        return new DownData(clearRow, board.getViewData());
    }

    //New Feature 2: difficulty level system
    /**
     * Updates the current difficulty level based on lines cleared
     */
    private void updateLevel() {
        int newLevel = Math.min(totalLinesCleared / LINES_PER_LEVEL + 1, MAX_LEVEL);
        if (newLevel > currentLevel) {
            currentLevel = newLevel;
            viewGuiController.updateGameSpeed(currentLevel);
        }
    }

    /**
     * Creates and initializes a new game
     */
    @Override
    public void createNewGame() {
        board.newGame();
        //New Feature 2: difficulty level system
        totalLinesCleared = 0;
        currentLevel = 1;
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        viewGuiController.updateGameSpeed(currentLevel);
    }
}