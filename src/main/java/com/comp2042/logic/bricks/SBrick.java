package com.comp2042.logic.bricks;

/**
 * S-shaped brick implementation
 */
final class SBrick extends AbstractBrick {

    /**
     * Color identifier for this brick type
     */
    private static final int BRICK_COLOR = 5;

    /**
     * Initializes the S-brick with its two rotation states
     */
    @Override
    protected void initializeShapes() {
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {BRICK_COLOR, BRICK_COLOR, 0, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {BRICK_COLOR, 0, 0, 0},
                {BRICK_COLOR, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, 0, 0, 0}
        });
    }
}