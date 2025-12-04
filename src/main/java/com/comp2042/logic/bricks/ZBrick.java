package com.comp2042.logic.bricks;

/**
 * Z-shaped brick implementation
 */
final class ZBrick extends AbstractBrick {

    /**
     * Color identifier for this brick type
     */
    private static final int BRICK_COLOR = 7;

    /**
     * Initializes the Z-brick with its two rotation states
     */
    @Override
    protected void initializeShapes() {
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {BRICK_COLOR, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, BRICK_COLOR, 0, 0},
                {BRICK_COLOR, BRICK_COLOR, 0, 0},
                {BRICK_COLOR, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }
}