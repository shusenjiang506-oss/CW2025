package com.comp2042;

public class GameController implements InputEventListener {
    private static final int BOARD_HEIGHT = 25;
    private static final int BOARD_WIDTH = 10;
    private static final int SOFT_DROP_BONUS = 1;

    //New Feature 2: difficulty level system
    private static final int LINES_PER_LEVEL = 10;
    private static final int MAX_LEVEL = 10;

    //private Board board = new SimpleBoard(25, 10);
    private Board board = new SimpleBoard(BOARD_HEIGHT, BOARD_WIDTH);

    private final GuiController viewGuiController;


    //New Feature 2: difficulty level system
    private int totalLinesCleared = 0;
    private int currentLevel = 1;

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        //New Feature 2: difficulty level system
        viewGuiController.updateGameSpeed(currentLevel);
    }


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

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    //New Feature 2: difficulty level system
    private void updateLevel() {
        int newLevel = Math.min(totalLinesCleared / LINES_PER_LEVEL + 1, MAX_LEVEL);
        if (newLevel > currentLevel) {
            currentLevel = newLevel;
            viewGuiController.updateGameSpeed(currentLevel);
        }
    }

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