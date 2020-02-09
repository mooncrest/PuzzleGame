package uoft.csc207.gameapplication.Games.MazeGame;

import android.graphics.Canvas;
import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;


import uoft.csc207.gameapplication.Games.GameDriver;
import uoft.csc207.gameapplication.Games.MazeGame.Controller.MazeController;
import uoft.csc207.gameapplication.Games.MazeGame.Controller.SwipeController;
import uoft.csc207.gameapplication.Games.MazeGame.Controller.TapController;
import uoft.csc207.gameapplication.Games.MazeGame.GameLogic.MazeGame;
import uoft.csc207.gameapplication.Games.MazeGame.Presenter.MazePresenter;


public class MazeGameDriver extends GameDriver {
    /**
     * specifies the controller type by default it would be tap
     */
    private String controllerType = "Tap";

    /**
     * used to reset the timer in maze game at every new level.
     */
    private boolean startTimer = true;

    /**
     * The game logic, controller and presenter.
     */
    private MazeGame mazeGame;
    private MazeController mazeController;
    private MazePresenter mazePresenter;


    /**
     * creates the MazeGameDriver that accesses the MazeGame
     *
     */
    public MazeGameDriver() {
        mazeGame = new MazeGame();
    }

    /**
     * calls the controllers touch method and invokes a the movement integer representation
     *
     * @param x coordinate of press
     * @param y coordinate of press
     */
    public void touchStart(float x, float y) {
        if (mazePresenter.isGameInitialized()) {
            executeCommand(mazeController.touchStart(x, y));
        }
    }

    /**
     * calls the controllers touch method and invokes a the movement integer representation
     * @param x where the x position of the cursor when moved
     * @param y where the y position of the cursor when moved
     */
    public void touchMove(float x, float y) {
        if (mazePresenter.isGameInitialized()) {
            executeCommand(mazeController.touchMove(x, y));
        }
    }

    /**
     * calls controllers movement methods and invokes a the movement integer representation
     */
    public void touchUp() {
        if (mazePresenter.isGameInitialized()) {
            executeCommand(mazeController.touchUp());
        }
    }

    /**
     * Invokes the movement method in the game logic based of a integer ie. 1 is up, 2 is right,
     * 3 is down, 4 is left.
     * @param control the integer representation of the game logic movement
     */
    private void executeCommand(int control) {
        switch (control) {
            case 1:
                mazeGame.moveUp();
                break;
            case 2:
                mazeGame.moveRight();
                break;
            case 3:
                mazeGame.moveDown();
                break;
            case 4:
                mazeGame.moveLeft();
                break;
            default:
                break;
        }
    }

    /**
     * updates the mazeGame time so scores decrement the scores of the user the longer they take
     */
    @Override
    public void timeUpdate() {
        if (mazePresenter.isGameInitialized()) {
            if (startTimer) {
                mazeGame.resetTimer();
                startTimer = false;
            }
            mazeGame.update();
        }
    }

    /**
     * draws onto the canvas
     *
     * @param canvas the place where the picture will be drawn on
     */
    public void draw(Canvas canvas) {
        newCanvas.save();
        newCanvas.drawColor(Color.WHITE);
        newCanvas.scale(1.01f, 1.01f);

        if (mazeGame.getInitializationStatus()) {
            mazePresenter.beginInitialization();
            startTimer = true;
        }

        Character[][] mazeArrayRepresentation = mazeGame.getMaze();
        mazePresenter.draw(newCanvas, mazeArrayRepresentation, mazeGame.getCharacterPos());

        canvas.drawBitmap(bitmap, 0, 0, null);

        newCanvas.restore();
    }

    /**
     * @return whether the game is over or not
     */
    public boolean getGameIsOver() {
        return mazeGame.getGameIsOver();
    }

    /**
     *
     * @return the total points accumulated in the mazegame
     */
    public int getPoints() {
        return mazeGame.getPoints();
    }

    /**
     * Initializes the controller and the maze game presenter
     */
    @Override
    public void init() {
        if (controllerType.equalsIgnoreCase("tap")) {
            mazeController = new TapController(screenWidth, screenHeight);
        }
        else if (controllerType.equalsIgnoreCase("swipe")) {
            mazeController = new SwipeController();
        }
        else {
            mazeController = new TapController(screenWidth, screenHeight);
        }
        mazePresenter = new MazePresenter(screenWidth, screenHeight);
    }

    /**
     * reads the configuration parameter to customize this game drivers control if customization
     * parameters is invalid invoke default controller.
     * @param configurations a json object containing controls
     */
    @Override
    public void setConfigurations(JSONObject configurations) {
        super.setConfigurations(configurations);

        try {
            controllerType = configurations.getString("controls");
        } catch (JSONException e) {

        }
    }
}
