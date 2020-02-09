package uoft.csc207.gameapplication.Games.MazeGame.Controller;

public class SwipeController extends MazeController{
    /**
     * Called when the user stops screen press and updates and resets classes start and end (x,y)
     * coordinates and returns a integer mapped to a class movement dependent on driver.
     * @return return 1 is move up, 2 is move right, 3 is move down, 4 is move left
     */
    @Override
    public int touchUp() {
        int xDiff = (int)(endX - startX);
        int yDiff = (int)(endY - startY);
        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff > 0) {
                return 2;
            }
            return 4;
        }
        else {
            if (yDiff > 0) {
                return 3;
            }
            return 1;
        }
    }

}
