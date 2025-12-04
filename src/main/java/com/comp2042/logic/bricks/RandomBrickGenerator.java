package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Random brick generator that generates bricks randomly from all available brick types
 */
public class RandomBrickGenerator implements BrickGenerator {

    /**
     * List of all available brick types
     */
    private final List<Brick> brickList;

    /**
     * Queue storing the upcoming bricks
     */
    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Initializes the generator with all brick types and pre-generates two bricks
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    /**
     * Gets the current brick and generates a new one if needed
     *
     * @return the current brick to be used
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }

    /**
     * Gets the next brick without removing it from the queue
     *
     * @return the next brick to be used
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}