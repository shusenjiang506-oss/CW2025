package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Interface defining the basic contract for all brick types
 */
public interface Brick {

    /**
     * Gets the shape matrices for all rotations of the brick
     *
     * @return list of 2D matrices representing different rotation states
     */
    List<int[][]> getShapeMatrix();
}