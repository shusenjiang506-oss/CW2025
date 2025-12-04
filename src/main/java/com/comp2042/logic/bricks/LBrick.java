package com.comp2042.logic.bricks;

/**
 * L-shaped brick implementation
 */
final class LBrick extends AbstractBrick {

    /**
     * Color identifier for this brick type
     */
    private static final int BRICK_COLOR = 3;

    /**
     * Initializes the L-brick with its four rotation states
     */
    @Override
    protected void initializeShapes() {
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, BRICK_COLOR},
                {0, BRICK_COLOR, 0, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {0, 0, BRICK_COLOR, 0},
                {0, 0, BRICK_COLOR, 0}
        });
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {0, 0, BRICK_COLOR, 0},
                {BRICK_COLOR, BRICK_COLOR, BRICK_COLOR, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {0, 0, 0, 0}
        });
    }
}