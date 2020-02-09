package uoft.csc207.gameapplication.Games.TetrisGame.Controller;

import static java.lang.Thread.sleep;

/**
 * A class responsible translating user input to controls for the Tetris game.
 */
public class TetrisGameController {

    /**
     * The pixel width of the screen.
     */
    private int screenWidth;

    /**
     * The pixel height of the screen.
     */
    private int screenHeight;

    /**
     * The current x coordinate since a touch event.
     */
    private int xEnd;

    /**
     * The current y coordinate since a touch event.
     */
    private int yEnd;

    /**
     * The initial x coordinate of a touch event.
     */
    private int xStart;

    /**
     * The initial y coordinate of a touch event.
     */
    private int yStart;

    /**
     * Construct a new TetrisGameController object.
     *
     * @param screenWidth The pixel width of the screen.
     * @param screenHeight The pixel height of the screen.
     */

    public TetrisGameController(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Respond to the start of a touch event.
     *
     * @param x The x coordinate of the touch event.
     * @param y The y coordinate of the touch event.
     */
    public void touchStart(float x, float y) {
        xEnd = (int) x;
        yEnd = (int) y;
        xStart = (int) x;
        yStart = (int) y;
    }

    /**
     * Respond to the movement of a touch event. Move the current piece right if the player swipes
     * right. Move left if the player swipes left. Move down if the player swipes down.
     *
     * @param x The x coordinate of the touch event.
     * @param y The y coordinate of the touch event.
     */
    public Request touchMove(float x, float y) {
        try {
            sleep(40);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            int xDistance = (int) x - xEnd;
            int yDistance = (int) y - yEnd;
            xEnd = (int) x;
            yEnd = (int) y;
            if (xDistance > 20) {
                return Request.MOVE_RIGHT;
            } else if (xDistance < -20) {
                return Request.MOVE_LEFT;
            } else if (yDistance > 20) {
                return Request.MOVE_DOWN;
            }
            return Request.VOID;
        }
    }

    /**
     * Respond to the end of a touch event. If the touch event is a tap on the right, then rotate
     * the current piece clockwise. If the touch event is a tap on the left, rotate the current
     * piece counterclockwise. If the touch event is a tap on the bottom, hard drop the current
     * piece.
     */
    public Request touchUp() {
        if (Math.abs(xEnd - xStart) < 10 && Math.abs(yEnd - yStart) < 10) {
            if (yStart > screenHeight * 0.85) {
                return Request.DROP_DOWN;
            } else if (xStart < screenWidth * 0.5) {
                return Request.ROTATE_COUNTERCLOCKWISE;
            } else {
                return Request.ROTATE_CLOCKWISE;
            }
        }
        return Request.VOID;
    }
}
