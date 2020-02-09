package uoft.csc207.gameapplication.Games.MazeGame.Controller;

/**
 * All classes that inherit from this must conform to the top level driver's game movement guidelines
 * ie. -1 is do nothing, 1 is move up, 2 is move right, 3 is move down, 4 is move left
 */
public abstract class MazeController {
    /**
     * used for the start and end coordinates of the key presses
     */
    float startX;
    float startY;
    float endX;
    float endY;
    /**
     * Called when user touches the screen sets the start and end coordinates.
     * @param x coordinate of the press
     * @param y coordinate of the press
     * @return The integer representation of the move to be executed in driver
     * ie. -1 is do nothing, 1 is move up, 2 is move right, 3 is move down, 4 is move left
     */
    public int touchStart(float x, float y) {
        startX = x;
        startY = y;
        endX = x;
        endY = y;
        return -1;
    }

    /**
     * Called when user swipes across the screen and updates the end coordinate
     * @param x coordinate of the press
     * @param y coordinate of the press
     * @return The integer representation of the move to be executed in driver
     * ie. -1 is do nothing, 1 is move up, 2 is move right, 3 is move down, 4 is move left
     */
    public int touchMove(float x, float y) {
        endX = x;
        endY = y;
        return -1;
    }

    /**
     * Called when the user lets go of the screen press
     * @return The integer representation of the move to be executed in driver
     * ie. -1 is do nothing, 1 is move up, 2 is move right, 3 is move down, 4 is move left
     */
    public int touchUp() {return -1;}
}
