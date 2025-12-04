package com.comp2042;

/**
 * Immutable data class containing information about the next rotation shape
 */
public final class NextShapeInfo {

    /**
     * Matrix representing the next shape
     */
    private final int[][] shape;

    /**
     * Index position of the next shape in rotation sequence
     */
    private final int position;

    /**
     * Creates a new next shape info instance
     *
     * @param shape the shape matrix
     * @param position the position index
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Gets a copy of the shape matrix
     *
     * @return copy of the shape matrix
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the position index of the shape
     *
     * @return the position index
     */
    public int getPosition() {
        return position;
    }
}