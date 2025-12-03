package com.comp2042;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
    void testInitialScore() {
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
    void testAddScore() {
        score.add(10);
        assertEquals(10, score.scoreProperty().get());
    }

    @Test
    void testAddMultipleTimes() {
        score.add(10);
        score.add(20);
        score.add(5);
        assertEquals(35, score.scoreProperty().get());
    }

    @Test
    void testReset() {
        score.add(100);
        score.reset();
        assertEquals(0, score.scoreProperty().get());
    }
}