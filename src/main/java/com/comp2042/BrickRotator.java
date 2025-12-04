package com.comp2042;

import com.comp2042.logic.bricks.Brick;

/**
 * Handles rotation logic for bricks
 */
public class BrickRotator {

    /**
     * The current brick being rotated
     */
    private Brick brick;

    /**
     * Index of the current rotation state
     */
    private int currentShape = 0;

    /**
     * Gets information about the next rotation state
     *
     * @return next shape information including matrix and index
     * @throws IllegalStateException if no brick has been set
     */
    public NextShapeInfo getNextShape() {
        if (brick == null) {
            throw new IllegalStateException("No brick has been set");
        }
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Gets the matrix of the current rotation state
     *
     * @return 2D array representing the current shape
     * @throws IllegalStateException if no brick has been set
     */
    public int[][] getCurrentShape() {
        if (brick == null) {
            throw new IllegalStateException("No brick has been set");
        }
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current rotation state index
     *
     * @param currentShape the rotation state index to set
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets a new brick and resets rotation to the initial state
     *
     * @param brick the brick to set
     * @throws IllegalArgumentException if brick is null
     */
    public void setBrick(Brick brick) {
        if (brick == null) {
            throw new IllegalArgumentException("Brick cannot be null");
        }
        this.brick = brick;
        currentShape = 0;
    }
}