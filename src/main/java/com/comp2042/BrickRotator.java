package com.comp2042;

import com.comp2042.logic.bricks.Brick;


public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    public NextShapeInfo getNextShape() {
        validateBrick();
        int nextShape = (currentShape + 1) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }


    public int[][] getCurrentShape() {
        validateBrick();
        return brick.getShapeMatrix().get(currentShape);
    }

    public void setCurrentShape(int currentShape) {
        validateBrick();
        if (currentShape < 0 || currentShape >= brick.getShapeMatrix().size()) {
            throw new IllegalArgumentException(
                    "Invalid shape index: " + currentShape +
                            ". Must be between 0 and " + (brick.getShapeMatrix().size() - 1)
            );
        }
        this.currentShape = currentShape;
    }

    public void setBrick(Brick brick) {
        if (brick == null) {
            throw new IllegalArgumentException("Brick cannot be null");
        }
        if (brick.getShapeMatrix() == null || brick.getShapeMatrix().isEmpty()) {
            throw new IllegalArgumentException("Brick must have at least one shape");
        }
        this.brick = brick;
        this.currentShape = 0;
    }

    private void validateBrick() {
        if (brick == null) {
            throw new IllegalStateException("Brick must be set before performing operations");
        }
    }


    public int getCurrentShapeIndex() {
        return currentShape;
    }

    public int getShapeCount() {
        validateBrick();
        return brick.getShapeMatrix().size();
    }
}
