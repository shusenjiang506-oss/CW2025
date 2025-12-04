package com.comp2042;

/**
 * Immutable event class representing a move action in the game
 */
public final class MoveEvent {
    /**
     * Type of the move event
     */
    private final EventType eventType;

    /**
     * Source that triggered the event
     */
    private final EventSource eventSource;

    /**
     * Creates a new move event
     *
     * @param eventType the type of move
     * @param eventSource the source of the event
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the type of the move event
     *
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the source of the event
     *
     * @return the event source
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}