package com.comp2042.logic.bricks;

import com.comp2042.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractBrick implements Brick {

    protected final List<int[][]> brickMatrix = new ArrayList<>();

    protected AbstractBrick() {
        initializeShapes();
    }

    protected abstract void initializeShapes();

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

    protected void addRotation(int[][] shape) {
        brickMatrix.add(shape);
    }
}