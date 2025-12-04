package com.comp2042.logic.bricks;

/**
 * O-shaped (square) brick implementation
 */
final class OBrick extends AbstractBrick {

    /**
     * Color identifier for this brick type
     */
    private static final int BRICK_COLOR = 4;

    /**
     * Initializes the O-brick with its single rotation state
     */
    @Override
    protected void initializeShapes() {
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {0, 0, 0, 0}
        });
    }
}