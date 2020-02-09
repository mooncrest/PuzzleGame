package uoft.csc207.gameapplication.Games.RhythmGame.Controller;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGameLevel;

/**
 * Gets inputs to interact with a level of the Rhythm game.
 */
public class RhythmGameController {
    private RhythmGameLevel level;
    private int numColumns;
    private int screenWidth;

    /**
     * Constructs the controller with the specified level
     * @param level the level of the game
     * @param screenWidth the width of width of the screen
     */
    public RhythmGameController(RhythmGameLevel level, int screenWidth) {
        this.level = level;
        this.numColumns = level.getNumColumns();
        this.screenWidth = screenWidth;
    }

    /**
     * Called when the touch first encounters the screen
     * @param x the x-coordinate of the event on the screen.
     * @param y the y-coordinate of the event on the screen.
     */
    public void touchStart(float x, float y) {
        // Determines the column number based on the screen width
        int colNumber = (int) (numColumns * x / screenWidth);
        level.tap(colNumber);
    }

    public void setLevel(RhythmGameLevel level) {
        this.level = level;
        this.numColumns = level.getNumColumns();
    }
}
