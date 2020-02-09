package uoft.csc207.gameapplication.Games.GameWrapper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

import uoft.csc207.gameapplication.Games.GameDriver;
import uoft.csc207.gameapplication.Games.MazeGame.MazeGameDriver;
import uoft.csc207.gameapplication.Games.RhythmGame.RhythmGameDriver;
import uoft.csc207.gameapplication.Games.TetrisGame.TetrisGameDriver;

/**
 * Plays the sub-games in consecutive order.
 */
public class GameWrapperDriver extends GameDriver{
    private GameDriver gameDriver;

    private int points;
    private Paint pointsPaint = new Paint();
    private int gamesPlayed;

    private boolean gameIsOver; // whether the entire game is over

    public GameWrapperDriver() {
        points = 0;
        gameIsOver = false;
        gamesPlayed = 0;
        pointsPaint.setColor(Color.rgb(255, 141, 54));
        pointsPaint.setTextSize(60);
    }

    /**
     * Called when the touch first encounters the screen.
     * @param x the x-coordinate of the event on the screen.
     * @param y the y-coordinate of the event on the screen.
     */
    @Override
    public void touchStart(float x, float y) {
        gameDriver.touchStart(x, y);
    }

    /**
     * Called as the touch moves around still in contact with the screen.
     * @param x the x-coordinate of the event on the screen.
     * @param y the y-coordinate of the event on the screen.
     */
    @Override
    public void touchMove(float x, float y) {
        gameDriver.touchMove(x, y);
    }

    /**
     * Called when the touch is lifted off the screen.
     */
    @Override
    public void touchUp() {
        gameDriver.touchUp();
    }

    /**
     * Draws the current game selected
     * @param canvas the canvas to draw the game on
     */
    @Override
    public void draw(Canvas canvas) {
        int currentGamePoints = gameDriver.getPoints();
        gameDriver.draw(canvas);
        canvas.drawText(String.valueOf(points + currentGamePoints), 10, 80, pointsPaint);
    }

    /**
     * Initializes the first game.
     */
    @Override
    public void init() {
        gameDriver = new TetrisGameDriver();
        trySetGameDriveConfiguration("tetris");

        gameDriver.setMetrics(getMetrics());
        gameDriver.setContext(getContext());
        gameDriver.setColourScheme(getColourScheme());
        gameDriver.init();
    }

    /**
     * Starts the game.
     */
    @Override
    public void start() {
        gameDriver.start();
    }

    /**
     * Updates the current game by one unit time or goes to the next game.
     */
    public void timeUpdate() {
        gameDriver.timeUpdate();

        // Select next driver when game is over
        if (gameDriver.getGameIsOver()) {
            gamesPlayed++;
            points += gameDriver.getPoints();   // saves points
            if (gamesPlayed == 1) {
                gameDriver = new RhythmGameDriver();
                trySetGameDriveConfiguration("rhythm");
            } else if (gamesPlayed == 2) {
                gameDriver = new MazeGameDriver();
                trySetGameDriveConfiguration("maze");
            } else {
                gameIsOver = true;
            }

            gameDriver.setMetrics(getMetrics());
            gameDriver.setContext(getContext());
            gameDriver.setColourScheme(getColourScheme());
            gameDriver.init();
            gameDriver.start();
        }
    }

    /**
     * Stops the game.
     */
    @Override
    public void stop() {
        gameDriver.stop();
    }

    /**
     * Returns if all games are over.
     * @return true if and only if all games are over.
     */
    @Override
    public boolean getGameIsOver() {
        return gameIsOver;
    }

    @Override
    public int getPoints() {
        return points;
    }

    // Sets the configurations for a driver or empty configuration if not configurations found
    private void trySetGameDriveConfiguration(String game) {
        try {
            gameDriver.setConfigurations(getConfigurations().getJSONObject(game));
        } catch (JSONException e) {
            gameDriver.setConfigurations(new JSONObject());
        }
    }
}
