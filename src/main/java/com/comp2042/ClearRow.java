package com.comp2042;

/**
 * Immutable data class containing information about cleared rows
 */
public final class ClearRow {

    /**
     * Number of lines that were cleared
     */
    private final int linesRemoved;

    /**
     * Updated board matrix after clearing rows
     */
    private final int[][] newMatrix;

    /**
     * Score bonus earned from clearing rows
     */
    private final int scoreBonus;

    /**
     * Creates a new ClearRow instance
     *
     * @param linesRemoved number of lines cleared
     * @param newMatrix updated board matrix
     * @param scoreBonus score bonus earned
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of lines that were cleared
     *
     * @return number of cleared lines
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets a copy of the updated board matrix
     *
     * @return copy of the new matrix after clearing rows
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus earned from clearing rows
     *
     * @return score bonus value
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}