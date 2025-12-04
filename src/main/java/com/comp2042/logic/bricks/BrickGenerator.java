package com.comp2042.logic.bricks;

/**
 * Interface for generating bricks in the game
 */
public interface BrickGenerator {

    /**
     * Gets the current brick
     *
     * @return the current brick instance
     */
    Brick getBrick();

    /**
     * Gets the next brick to be used
     *
     * @return the next brick instance
     */
    Brick getNextBrick();
}