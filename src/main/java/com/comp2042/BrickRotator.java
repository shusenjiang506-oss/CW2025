package com.comp2042;

import com.comp2042.logic.bricks.Brick;

public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /*
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }
        */

    public NextShapeInfo getNextShape() {
        // Fixed: Added null check to prevent NullPointerException
        if (brick == null) {
            throw new IllegalStateException("No brick has been set");
        }
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }


    /*
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }
    */

    public int[][] getCurrentShape() {
        // Fixed: Added null check to prevent NullPointerException
        if (brick == null) {
            throw new IllegalStateException("No brick has been set");
        }
        return brick.getShapeMatrix().get(currentShape);
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /*
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }
        */

    public void setBrick(Brick brick) {
        // Fixed: Added null validation to prevent setting null brick
        if (brick == null) {
            throw new IllegalArgumentException("Brick cannot be null");
        }
        this.brick = brick;
        currentShape = 0;
    }


}