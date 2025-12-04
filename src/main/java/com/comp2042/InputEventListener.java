package com.comp2042;

/**
 * Interface for handling user input events in the game
 */
public interface InputEventListener {

    /**
     * Handles downward movement events
     *
     * @param event the move event
     * @return data about the move result and any cleared rows
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Handles leftward movement events
     *
     * @param event the move event
     * @return updated view data
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles rightward movement events
     *
     * @param event the move event
     * @return updated view data
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles rotation events
     *
     * @param event the move event
     * @return updated view data
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Creates a new game instance
     */
    void createNewGame();
}