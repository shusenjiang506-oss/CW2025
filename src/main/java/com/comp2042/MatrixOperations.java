package com.comp2042;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing matrix operations for the game board and bricks
 */
public class MatrixOperations {


    /**
     * Private constructor to prevent instantiation of utility class
     */
    private MatrixOperations(){

    }

    /**
     * Checks if a brick intersects with the board or goes out of bounds
     *
     * @param matrix the game board matrix
     * @param brick the brick matrix
     * @param x the x-position of the brick
     * @param y the y-position of the brick
     * @return true if there is an intersection or out of bounds, false otherwise
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }
        */

    // Fixed: Added null check and reordered bounds checking to prevent ArrayIndexOutOfBoundsException
    /**
     * Checks if the given coordinates are out of bounds of the matrix
     *
     * @param matrix the matrix to check against
     * @param targetX the x-coordinate
     * @param targetY the y-coordinate
     * @return true if out of bounds, false otherwise
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        if (matrix == null || matrix.length == 0) {
            return true;
        }
        if (targetX < 0 || targetY < 0 || targetY >= matrix.length) {
            return true;
        }
        return targetX >= matrix[targetY].length;
    }

    /**
     * Creates a deep copy of a 2D integer array
     *
     * @param original the original matrix to copy
     * @return a deep copy of the matrix
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a brick into the game board matrix at the specified position
     *
     * @param filledFields the current board state
     * @param brick the brick matrix to merge
     * @param x the x-position to merge at
     * @param y the y-position to merge at
     * @return a new matrix with the brick merged
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
     * Checks for and removes completed rows from the matrix
     *
     * @param matrix the game board matrix
     * @return information about cleared rows including the new matrix and score bonus
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }

    /**
     * Creates a deep copy of a list of 2D integer arrays
     *
     * @param list the list to copy
     * @return a deep copy of the list
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}