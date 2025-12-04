package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class managing the game score with observable property support
 */
public final class Score {

    /**
     * Observable property for the current score
     */
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Gets the score property for binding
     *
     * @return the score integer property
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds points to the current score
     *
     * @param i the points to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets the score to zero
     */
    public void reset() {
        score.setValue(0);
    }
}