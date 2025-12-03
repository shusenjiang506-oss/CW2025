package com.comp2042;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
    void testCopy() {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copy = MatrixOperations.copy(original);

        assertArrayEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    void testIntersectNoConflict() {
        int[][] board = new int[5][5];
        int[][] brick = {{1, 1}, {1, 1}};

        boolean result = MatrixOperations.intersect(board, brick, 1, 1);
        assertFalse(result);
    }

    @Test
    void testIntersectWithConflict() {
        int[][] board = new int[5][5];
        board[2][2] = 1;
        int[][] brick = {{1, 1}, {1, 1}};

        boolean result = MatrixOperations.intersect(board, brick, 1, 1);
        assertTrue(result);
    }

    @Test
    void testIntersectOutOfBounds() {
        int[][] board = new int[5][5];
        int[][] brick = {{1, 1}, {1, 1}};

        boolean result = MatrixOperations.intersect(board, brick, -1, 0);
        assertTrue(result);
    }

    @Test
    void testMerge() {
        int[][] board = new int[5][5];
        int[][] brick = {{1, 1}, {0, 0}};

        int[][] result = MatrixOperations.merge(board, brick, 1, 1);

        assertEquals(1, result[1][1]);
        assertEquals(1, result[1][2]);
    }
}
