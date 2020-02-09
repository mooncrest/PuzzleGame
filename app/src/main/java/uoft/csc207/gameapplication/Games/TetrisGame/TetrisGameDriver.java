package uoft.csc207.gameapplication.Games.TetrisGame;

import android.graphics.Canvas;

import uoft.csc207.gameapplication.Games.GameDriver;
import uoft.csc207.gameapplication.Games.TetrisGame.Controller.Request;
import uoft.csc207.gameapplication.Games.TetrisGame.Controller.TetrisGameController;
import uoft.csc207.gameapplication.Games.TetrisGame.GameLogic.Board;
import uoft.csc207.gameapplication.Games.TetrisGame.GameLogic.PieceGenerator;
import uoft.csc207.gameapplication.Games.TetrisGame.GameLogic.TetrisGame;
import uoft.csc207.gameapplication.Games.TetrisGame.Presenter.TetrisGamePresenter;

/**
 * A facade class for the Tetris game, serving as a concrete interface for managing touch inputs and
 * drawing the game to the screen.
 */
public class TetrisGameDriver extends GameDriver {

    /**
     * A representation of a game of Tetris, responsible for handling the logic of the game.
     */
    private TetrisGame game;

    /**
     * A class responsible translating user input to controls for the Tetris game.
     */
    private TetrisGameController controller;

    /**
     * A class responsible for presenting the Tetris game to the screen.
     */
    private TetrisGamePresenter presenter;

    /**
     * Initialize this TetrisGameDriver object.
     */
    @Override
    public void init() {
        game = new TetrisGame(new Board(10, 20), new PieceGenerator());
        System.out.println(getColourScheme());
        presenter = new TetrisGamePresenter(getColourScheme());
        controller = new TetrisGameController(screenWidth, screenHeight);
    }

    /**
     * Return true if and only if this game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    @Override
    public boolean getGameIsOver() {
        return game.getGameIsOver();
    }

    /**
     * Return the points scored.
     *
     * @return The points scored.
     */
    @Override
    public int getPoints() {
        return game.getPoints();
    }

    /**
     * Respond to the start of a touch event.
     *
     * @param x The x coordinate of the touch event.
     * @param y The y coordinate of the touch event.
     */
    @Override
    public void touchStart(float x, float y) {
        controller.touchStart(x, y);
    }

    /**
     * Respond to the movement of a touch event.
     *
     * @param x The x coordinate of the touch event.
     * @param y The y coordinate of the touch event.
     */
    @Override
    public void touchMove(float x, float y) {
        run(controller.touchMove(x, y));
    }

    /**
     * Respond to the end of a touch event.
     */
    @Override
    public void touchUp() {
        run(controller.touchUp());
    }

    /**
     * Execute a player-input request.
     *
     * @param request The player movement request to be executed.
     */
    private void run(Request request) {
        switch (request) {
            case MOVE_LEFT:
                game.moveLeft();
                break;
            case MOVE_RIGHT:
                game.moveRight();
                break;
            case MOVE_DOWN:
                game.moveDown();
                break;
            case DROP_DOWN:
                game.dropDown();
                break;
            case ROTATE_CLOCKWISE:
                game.rotateClockwise();
                break;
            case ROTATE_COUNTERCLOCKWISE:
                game.rotateCounterclockwise();
                break;
            default:
                break;
        }
    }

    /**
     * Update the state of the game by one frame.
     */
    @Override
    public void timeUpdate() {
        game.update();
    }

    /**
     * Refresh the frame onscreen.
     *
     * @param canvas The canvas to be drawn on.
     */
    @Override
    public void draw(Canvas canvas) {
        presenter.draw(canvas, bitmap, game.getGrid());
    }
}
