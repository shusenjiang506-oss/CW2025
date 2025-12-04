package com.comp2042;

/**
 * Enum representing the source of game events
 */
public enum EventSource {
    /**
     * Event triggered by user input
     */
    USER,

    /**
     * Event triggered by background thread (e.g., automatic brick descent)
     */
    THREAD
}