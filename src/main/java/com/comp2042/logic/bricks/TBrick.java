package com.comp2042.logic.bricks;

/**
 * T-shaped brick implementation
 */
final class TBrick extends AbstractBrick {

    /**
     * Color identifier for this brick type
     */
    private static final int BRICK_COLOR = 6;

    /**
     * Initializes the T-brick with its four rotation states
     */
    @Override
    protected void initializeShapes() {
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {BRICK_COLOR, BRICK_COLOR, BRICK_COLOR, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, BRICK_COLOR, 0, 0},
                {BRICK_COLOR, BRICK_COLOR, BRICK_COLOR, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, BRICK_COLOR, 0, 0},
                {BRICK_COLOR, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, 0, 0, 0}
        });
    }
}