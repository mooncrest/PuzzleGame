package uoft.csc207.gameapplication.Games.TetrisGame.GameLogic;

/**
 * A representation of a game of Tetris, responsible for handling the logic of the game.
 */
public class TetrisGame {

    /**
     * A representation of the Tetris board.
     */
    private Board board;

    /**
     * A random piece generator.
     */
    private PieceGenerator pieceGenerator;

    /**
     * The number of lines cleared on the current level.
     */
    private int linesCleared;

    /**
     * The number of frames between each time the current piece falls down.
     */
    private int threshold;

    /**
     * The number of frames since the last time the current piece fell down.
     */
    private int count;

    /**
     * Whether the game is over.
     */
    private boolean gameIsOver;

    /**
     * The number of points scored by the player.
     */
    private int points;

    /**
     * Construct a new TetrisGame object.
     */
    public TetrisGame(Board board, PieceGenerator pieceGenerator) {
        this.pieceGenerator = pieceGenerator;
        this.board = board;
        this.board.setCurrPiece(pieceGenerator.getRandomPiece());
        gameIsOver = false;
        points = 0;
        linesCleared = 0;
        threshold = 25;
        count = 0;
    }

    /**
     * Return a representation of the Tetris board.
     *
     * @return A representation of the Tetris board.
     */
    public char[][] getGrid() {
        return board.getGrid();
    }

    /**
     * Return true if and only if this game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean getGameIsOver() {
        return gameIsOver;
    }

    /**
     * Return the points scored.
     *
     * @return The points scored.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Move the current piece left, if possible.
     */
    public void moveLeft() {
        board.moveLeft();
    }

    /**
     * Move the current piece right, if possible.
     */
    public void moveRight() {
        board.moveRight();
    }

    /**
     * Move the current piece down, if possible.
     */
    public void moveDown() {
        board.moveDown();
        if (board.getCurrPiece() == null) {
            reset();
        }
    }

    /**
     * Drop the current piece down, if possible.
     */
    public void dropDown() {
        board.dropDown();
        reset();
    }

    /**
     * Rotate the current piece clockwise, if possible.
     */
    public void rotateClockwise() {
        board.rotateClockwise();
    }

    /**
     * Rotate the current piece counterclockwise, if possible.
     */
    public void rotateCounterclockwise() {
        board.rotateCounterClockwise();
    }

    /**
     * Reset to a new current piece, if possible, and update points. Otherwise, end the game.
     */
    private void reset() {
        int lines = board.clearLines();
        linesCleared += lines;
        if (lines == 4) {
            points += 800;
        } else if (lines == 3) {
            points += 500;
        } else {
            points += lines * 100;
        }
        if (!board.setCurrPiece(pieceGenerator.getRandomPiece())) {
            gameIsOver = true;
        }
    }

    /**
     * Move the current piece down, periodically.
     */
    public void update() {
        if (count == threshold) {
            moveDown();
            count = 0;
            if (linesCleared >= 10) {
                threshold = Math.max(5, threshold - 10);
                linesCleared = linesCleared % 10;
            }
        } else {
            count += 1;
        }
    }
}
