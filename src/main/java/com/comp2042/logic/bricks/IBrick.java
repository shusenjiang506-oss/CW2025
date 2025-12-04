package com.comp2042.logic.bricks;

/**
 * I-shaped brick implementation
 */
public final class IBrick extends AbstractBrick {

    /**
     * Color identifier for this brick type
     */
    private static final int BRICK_COLOR = 1;

    /**
     * Initializes the I-brick with its two rotation states
     */
    @Override
    protected void initializeShapes() {
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {BRICK_COLOR, BRICK_COLOR, BRICK_COLOR, BRICK_COLOR},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, 0, 0}
        });
    }
}