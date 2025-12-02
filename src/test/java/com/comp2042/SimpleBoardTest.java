package com.comp2042;

import com.comp2042.Score;
import com.comp2042.SimpleBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    private SimpleBoard board;

    @BeforeEach
    void setUp() {
        board = new SimpleBoard(25, 10);
        board.createNewBrick();
    }

    @Test
    void testCreateNewBrick() {
        boolean gameOver = board.createNewBrick();
        assertFalse(gameOver);
    }

    @Test
    void testMoveBrickDown() {
        boolean canMove = board.moveBrickDown();
        assertTrue(canMove);
    }

    @Test
    void testHardDrop() {
        int dropDistance = board.hardDrop();
        assertTrue(dropDistance > 0);
    }

    @Test
    void testGetScore() {
        Score score = board.getScore();
        assertNotNull(score);
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
    void testNewGame() {
        board.getScore().add(100);
        board.newGame();
        assertEquals(0, board.getScore().scoreProperty().get());
    }
}
