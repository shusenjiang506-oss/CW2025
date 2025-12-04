package com.comp2042;

/**
 * Immutable data class containing information after a brick moves down
 */
public final class DownData {

    /**
     * Information about cleared rows
     */
    private final ClearRow clearRow;

    /**
     * Updated view data for rendering
     */
    private final ViewData viewData;

    /**
     * Creates a new DownData instance
     *
     * @param clearRow information about cleared rows
     * @param viewData updated view data
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Gets the clear row information
     *
     * @return clear row data
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Gets the view data
     *
     * @return view data for rendering
     */
    public ViewData getViewData() {
        return viewData;
    }
}