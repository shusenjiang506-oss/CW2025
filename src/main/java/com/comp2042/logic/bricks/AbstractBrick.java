package com.comp2042.logic.bricks;

import com.comp2042.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract brick class that provides base implementation for all concrete brick types
 */
abstract class AbstractBrick implements Brick {

    /**
     * List storing all rotation matrices of the brick
     */
    protected final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructor that initializes the brick shapes
     */
    protected AbstractBrick() {
        initializeShapes();
    }

    /**
     * Initializes all rotation shapes of the brick, must be implemented by subclasses
     */
    protected abstract void initializeShapes();

    /**
     * Gets a deep copy of all rotation shape matrices
     *
     * @return list containing all rotation shape matrices
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

    /**
     * Adds a rotation shape to the brick matrix list
     *
     * @param shape the rotation shape matrix of the brick
     */
    protected void addRotation(int[][] shape) {
        brickMatrix.add(shape);
    }
}