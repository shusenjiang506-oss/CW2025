package com.comp2042;

/**
 * Immutable data class containing view information for rendering the game
 */
public final class ViewData {

    /**
     * Matrix data of the current brick
     */
    private final int[][] brickData;

    /**
     * X-position of the current brick
     */
    private final int xPosition;

    /**
     * Y-position of the current brick
     */
    private final int yPosition;

    /**
     * Matrix data of the next brick to be spawned
     */
    private final int[][] nextBrickData;

    /**
     * Creates a new view data instance
     *
     * @param brickData the current brick matrix
     * @param xPosition the x-position of the brick
     * @param yPosition the y-position of the brick
     * @param nextBrickData the next brick matrix
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    /**
     * Gets a copy of the current brick matrix
     *
     * @return copy of the brick data
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets the x-position of the brick
     *
     * @return the x-position
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Gets the y-position of the brick
     *
     * @return the y-position
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Gets a copy of the next brick matrix
     *
     * @return copy of the next brick data
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}