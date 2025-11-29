package com.comp2042.logic.bricks;

final class SBrick extends AbstractBrick {

    private static final int BRICK_COLOR = 5;

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