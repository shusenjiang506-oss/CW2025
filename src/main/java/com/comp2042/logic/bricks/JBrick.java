package com.comp2042.logic.bricks;

/**
 * J-shaped brick implementation
 */
final class JBrick extends AbstractBrick {

    /**
     * Color identifier for this brick type
     */
    private static final int BRICK_COLOR = 2;

    /**
     * Initializes the J-brick with its four rotation states
     */
    @Override
    protected void initializeShapes() {
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {BRICK_COLOR, BRICK_COLOR, BRICK_COLOR, 0},
                {0, 0, BRICK_COLOR, 0},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, 0, 0}
        });
        addRotation(new int[][]{
                {0, 0, 0, 0},
                {0, BRICK_COLOR, 0, 0},
                {0, BRICK_COLOR, BRICK_COLOR, BRICK_COLOR},
                {0, 0, 0, 0}
        });
        addRotation(new int[][]{
                {0, 0, BRICK_COLOR, 0},
                {0, 0, BRICK_COLOR, 0},
                {0, BRICK_COLOR, BRICK_COLOR, 0},
                {0, 0, 0, 0}
        });
    }
}