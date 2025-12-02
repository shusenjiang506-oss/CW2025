package com.comp2042.logic.bricks;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomBrickGeneratorTest {

    private RandomBrickGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new RandomBrickGenerator();
    }

    @Test
    void testGetBrick() {
        Brick brick = generator.getBrick();
        assertNotNull(brick);
    }

    @Test
    void testGetNextBrick() {
        Brick nextBrick = generator.getNextBrick();
        assertNotNull(nextBrick);
    }

    @Test
    void testGetBrickReturnsValidBrick() {
        Brick brick = generator.getBrick();
        assertNotNull(brick.getShapeMatrix());
        assertFalse(brick.getShapeMatrix().isEmpty());
    }

    @Test
    void testMultipleCalls() {
        Brick brick1 = generator.getBrick();
        Brick brick2 = generator.getBrick();
        Brick brick3 = generator.getBrick();
        
        assertNotNull(brick1);
        assertNotNull(brick2);
        assertNotNull(brick3);
    }
}
