package uoft.csc207.gameapplication.Games.RhythmGame;

import android.graphics.Canvas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import uoft.csc207.gameapplication.Games.GameDriver;
import uoft.csc207.gameapplication.Games.RhythmGame.Controller.RhythmGameController;
import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGameLevel;
import uoft.csc207.gameapplication.Games.RhythmGame.Presenter.RhythmGamePresenter;
import uoft.csc207.gameapplication.Utility.TryGetJSON;

/**
 * The driver for the entire Rhythm game.
 * It manages the logic of the game.
 * It takes in touch inputs and updates the game accordingly.
 * It draws the game and related statistics.
 */
public class RhythmGameDriver extends GameDriver implements Observer {
    private int totalPoints;
    private int levelIndex;

    private RhythmGameLevel[] levels;
    private RhythmGameController controller;
    private RhythmGamePresenter presenter;

    /**
     * Constructs a Rhythm Game Driver and initializes to start of game.
     */
    public RhythmGameDriver() {
        this.totalPoints = 0;
        this.levelIndex = 0;
    }

    /**
     * Creates and prepares the components of the driver.
     */
    @Override
    public void init() {
        // Creates and initializes the levels of the game.
        try {
            createLevels(getConfigurations().getJSONArray("levels"));
        } catch (JSONException e) {
            this.levels = new RhythmGameLevel[1];
            levels[0] = new RhythmGameLevel(4, 100,
                    "Old Town Road", "SONG", getContext());
        }

        this.controller = new RhythmGameController(levels[levelIndex], getMetrics().widthPixels);

        // Prepares and constructs the presenter //
        char[] shapes = new char[4];
        for (int i = 1; i <= 4; i++) {  // prepares the four shapes of each column
            shapes[i - 1] = TryGetJSON.tryGetString(getConfigurations(), String.format(Locale.CANADA,
                    "shape%d", i), "O").charAt(0);
        }

        String statDrawerMode = TryGetJSON.tryGetString(getConfigurations(), "presenter", "STATS");

        this.presenter = new RhythmGamePresenter(levels[levelIndex], getMetrics(), getContext(),
                shapes, getColourScheme(), statDrawerMode);
    }

    /**
     * Starts the game.
     */
    @Override
    public void start() {
        levels[levelIndex].start();
        presenter.start();
    }

    /**
     * Updates the state of the game by one unit time.
     */
    @Override
    public void timeUpdate() {
        levels[levelIndex].timeUpdate();
    }

    /**
     * Stops the game and thread-related processes.
     */
    @Override
    public void stop() {
        levels[levelIndex].stop();
        presenter.stop();
    }

    /**
     * Called when the game is tapped.
     * @param x the x-coordinate of the event on the screen.
     * @param y the y-coordinate of the event on the screen.
     */
    @Override
    public void touchStart(float x, float y) {
        controller.touchStart(x, y);
    }

    /**
     * Draws the game.
     * @param canvas the canvas to draw on.
     */
    @Override
    public void draw(Canvas canvas) {
        presenter.draw(canvas);
    }

    /**
     * Called when the driver is notified that a level is over.
     * @param observable the level being observed.
     * @param o String, which is the level over message.
     */
    @Override
    public void update(Observable observable, Object o) {
        if (((String) o).equalsIgnoreCase(RhythmGameLevel.LEVEL_OVER_MESSAGE)) {
            if (levelIndex < levels.length - 1) {
                stop();
                this.totalPoints += levels[levelIndex].getPoints();
                levelIndex++;
                controller.setLevel(levels[levelIndex]);
                presenter.setLevel(levels[levelIndex]);
                start();
            }
        }
    }

    /**
     * Returns whether the game is over.
     * @return true if and only if on the last level of the game and the level is over.
     */
    @Override
    public boolean getGameIsOver() {
        return levelIndex == levels.length - 1 && levels[levelIndex].getGameIsOver();
    }

    @Override
    public int getPoints() {
        return totalPoints + levels[levelIndex].getPoints();
    }

    /**
     * Initializes the list of levels of the game.
     *
     * @param levelsArray contains a list of a package of attributes for each level
     */
    private void createLevels(JSONArray levelsArray) {
        this.levels = new RhythmGameLevel[levelsArray.length()];

        JSONObject jsonLevel;
        int numColumns, gameHeight;
        String song, mode;
        for (int i = 0; i < levelsArray.length(); i++) {
            jsonLevel = TryGetJSON.tryGetJSONObject(levelsArray, i, new JSONObject());
            numColumns = TryGetJSON.tryGetInt(jsonLevel, "numColumns", 4);
            gameHeight = TryGetJSON.tryGetInt(jsonLevel,"height", 100);
            song = TryGetJSON.tryGetString(jsonLevel,"song", "Old Town Road");
            mode = TryGetJSON.tryGetString(jsonLevel,"mode", "SONG");
            levels[i] = new RhythmGameLevel(numColumns, gameHeight, song, mode, getContext());
            levels[i].addObserver(this);
        }
    }
}
