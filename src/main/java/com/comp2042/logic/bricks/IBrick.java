package com.comp2042.logic.bricks;

public final class IBrick extends AbstractBrick {

    private static final int BRICK_COLOR = 1;

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