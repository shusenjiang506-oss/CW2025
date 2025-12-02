package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.IBrick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrickRotatorTest {

    private BrickRotator rotator;

    @BeforeEach
    void setUp() {
        rotator = new BrickRotator();
    }

    @Test
    void testSetBrick() {
        Brick brick = new IBrick();
        rotator.setBrick(brick);

        assertNotNull(rotator.getCurrentShape());
    }

    @Test
    void testSetBrickNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            rotator.setBrick(null);
        });
    }

    @Test
    void testGetNextShape() {
        Brick brick = new IBrick();
        rotator.setBrick(brick);

        NextShapeInfo nextShape = rotator.getNextShape();

        assertNotNull(nextShape);
        assertNotNull(nextShape.getShape());
    }

    @Test
    void testRotationCycles() {
        Brick brick = new IBrick();
        rotator.setBrick(brick);

        NextShapeInfo next1 = rotator.getNextShape();
        assertEquals(1, next1.getPosition());

        rotator.setCurrentShape(next1.getPosition());
        NextShapeInfo next2 = rotator.getNextShape();
        assertEquals(0, next2.getPosition());
    }
}
