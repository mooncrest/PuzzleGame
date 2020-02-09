package uoft.csc207.gameapplication.Games;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import org.json.JSONObject;

import java.util.Map;

/**
 * Takes in touch inputs for a game.
 * Draws the game to canvas.
 */
public abstract class GameDriver {
    // To draw on
    protected Bitmap bitmap;
    protected Canvas newCanvas;

    // Screen Dimensions
    protected int screenHeight;
    protected int screenWidth;

    private DisplayMetrics metrics;
    private Context context;
    private JSONObject configurations;
    private Map<String, Integer> colourScheme;

    public void init() {}

    /**
     * Called when the touch first encounters the screen.
     * @param x the x-coordinate of the event on the screen.
     * @param y the y-coordinate of the event on the screen.
     */
    public void touchStart(float x, float y) {}

    /**
     * Called as the touch moves around, still in contact with the screen.
     * @param x the x-coordinate of the event on the screen.
     * @param y the y-coordinate of the event on the screen.
     */
    public void touchMove(float x, float y) {}

    /**
     * Called when the touch is lifted off the screen.
     */
    public void touchUp() {}

    /**
     * Updates the games by one unit time
     */
    public void timeUpdate(){}

    /**
     * Starts thread-related processes
     */
    public void start() {}

    /**
     * Ends thread-related processes
     */
    public void stop() {}

    /**
     * Draws the game to canvas.
     * @param canvas the canvas to draw on.
     */
    public abstract void draw(Canvas canvas);

    public abstract boolean getGameIsOver();

    public abstract int getPoints();

    public DisplayMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(DisplayMetrics metrics) {
        this.metrics = metrics;
        screenHeight = metrics.heightPixels - 40;
        screenWidth = metrics.widthPixels;
        bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        newCanvas = new Canvas(bitmap);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setColourScheme(Map<String, Integer> colourScheme){this.colourScheme = colourScheme;}

    public JSONObject getConfigurations() {
        return configurations;
    }

    public void setConfigurations(JSONObject configurations) {
        this.configurations = configurations;
    }

    public Map<String, Integer> getColourScheme() {
        return colourScheme;
    }
}
