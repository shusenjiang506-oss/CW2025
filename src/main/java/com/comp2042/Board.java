package com.comp2042;

/**
 * Interface defining the core functionality of the game board
 */
public interface Board {

    /**
     * Moves the current brick down by one position
     *
     * @return true if the move was successful, false otherwise
     */
    boolean moveBrickDown();

    /**
     * Moves the current brick left by one position
     *
     * @return true if the move was successful, false otherwise
     */
    boolean moveBrickLeft();

    /**
     * Moves the current brick right by one position
     *
     * @return true if the move was successful, false otherwise
     */
    boolean moveBrickRight();

    /**
     * Rotates the current brick counterclockwise
     *
     * @return true if the rotation was successful, false otherwise
     */
    boolean rotateLeftBrick();

    /**
     * Creates a new brick on the board
     *
     * @return true if the brick was created successfully, false if game over
     */
    boolean createNewBrick();

    /**
     * Gets the current state of the board matrix
     *
     * @return 2D array representing the board state
     */
    int[][] getBoardMatrix();

    /**
     * Gets the view data for rendering the game
     *
     * @return view data containing board and brick information
     */
    ViewData getViewData();

    /**
     * Merges the current brick into the background board
     */
    void mergeBrickToBackground();

    /**
     * Clears completed rows from the board
     *
     * @return information about cleared rows
     */
    ClearRow clearRows();

    /**
     * Gets the current score
     *
     * @return the current score object
     */
    Score getScore();

    /**
     * Starts a new game, resetting the board state
     */
    void newGame();

    /**
     * Drops the current brick to the lowest possible position instantly
     *
     * @return the number of rows the brick dropped
     */
    int hardDrop();
}