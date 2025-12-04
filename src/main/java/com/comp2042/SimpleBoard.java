package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;

import java.awt.*;

/**
 * Simple implementation of the game board managing brick placement and game state
 */
public class SimpleBoard implements Board {

    /**
     * Width of the game board
     */
    private final int width;

    /**
     * Height of the game board
     */
    private final int height;

    /**
     * Generator for creating new bricks
     */
    private final BrickGenerator brickGenerator;

    /**
     * Handler for brick rotation logic
     */
    private final BrickRotator brickRotator;

    /**
     * Current state of the game board matrix
     */
    private int[][] currentGameMatrix;

    /**
     * Current position offset of the active brick
     */
    private Point currentOffset;

    /**
     * Score tracker for the game
     */
    private final Score score;

    /**
     * Creates a new simple board with specified dimensions
     *
     * @param width the board width
     * @param height the board height
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    /**
     * Moves the current brick down by one position
     *
     * @return true if the move was successful, false if blocked
     */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


    /**
     * Moves the current brick left by one position
     *
     * @return true if the move was successful, false if blocked
     */
    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * Moves the current brick right by one position
     *
     * @return true if the move was successful, false if blocked
     */
    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * Rotates the current brick counterclockwise
     *
     * @return true if the rotation was successful, false if blocked
     */
    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    // Fix Bug 1: Game Over triggered prematurely due to incorrect brick spawn position
    /*
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(4, 10);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }
    */

    /**
     * Creates a new brick at the top of the board
     *
     * @return true if game over (brick cannot be placed), false otherwise
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(4, 0);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }


    /**
     * Gets the current board matrix
     *
     * @return the board matrix
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * Gets the current view data for rendering
     *
     * @return view data containing brick and position information
     */
    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    /**
     * Merges the current brick into the background board
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Clears completed rows from the board
     *
     * @return information about cleared rows
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    /**
     * Gets the current score object
     *
     * @return the score
     */
    @Override
    public Score getScore() {
        return score;
    }


    /**
     * Starts a new game by resetting the board and score
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }

    // New Feature 4: hard landing method
    /**
     * Instantly drops the brick to the lowest possible position
     *
     * @return the number of rows the brick dropped
     */
    @Override
    public int hardDrop() {
        int dropDistance = 0;
        while (moveBrickDown()) {
            dropDistance++;
        }
        return dropDistance;
    }
}