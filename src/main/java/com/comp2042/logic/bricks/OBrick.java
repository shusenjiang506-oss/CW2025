package com.comp2042.logic.bricks;

final class OBrick extends AbstractBrick {

    private static final int BRICK_COLOR = 4;

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