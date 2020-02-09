package uoft.csc207.gameapplication.Games.TetrisGame.GameLogic;

import java.util.Arrays;

/**
 * A representation of the Tetris board.
 */
public class Board {

    /**
     * The width of this board.
     */
    private int width;

    /**
     * The height of this board.
     */
    private int height;

    /**
     * A 2D array representation of this board.
     */
    private char[][] grid;

    /**
     * The current piece.
     */
    private Piece currPiece;

    /**
     * Construct a new Board object.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(grid[i], '.');
        }
    }

    /**
     * Return the 2D array representation of this board.
     *
     * @return The 2D array representation of this board.
     */
    char[][] getGrid() {
        return grid.clone();
    }

    /**
     * Return the current piece.
     *
     * @return The current piece.
     */
    Piece getCurrPiece() {
        return currPiece;
    }

    /**
     * Set the current piece.
     *
     * @param piece The new current piece.
     * @return True if this piece can be placed on this board, false otherwise.
     */
    boolean setCurrPiece(Piece piece) {
        currPiece = piece;
        if (canMove(0, 0)) {
            drawCurrPiece();
            return true;
        }
        return false;
    }

    /**
     * Draw the current piece on this board.
     */
    private void drawCurrPiece() {
        for (int y = 0; y < currPiece.getSprites()[0].length; y++) {
            for (int x = 0; x < currPiece.getSprites()[0][0].length(); x++) {
                if (currPiece.getSprites()[currPiece.getRotation()][y].charAt(x) != '.') {
                    grid[currPiece.getY() + y][currPiece.getX() + x] =
                            currPiece.getSprites()[currPiece.getRotation()][y].charAt(x);
                }
            }
        }
    }

    /**
     * Erase the current piece from this board.
     */
    private void eraseCurrPiece() {
        for (int y = 0; y < currPiece.getSprites()[0].length; y++) {
            for (int x = 0; x < currPiece.getSprites()[0][0].length(); x++) {
                if (currPiece.getSprites()[currPiece.getRotation()][y].charAt(x) != '.') {
                    grid[currPiece.getY() + y][currPiece.getX() + x] = '.';
                }
            }
        }
    }

    /**
     * Return true if and only if the current piece can be moved.
     *
     * @param adjX The number of units to be moved in the x direction.
     * @param adjY The number of units to be moved in the y direction.
     * @return True if the piece can be moved, false otherwise.
     */
    private boolean canMove(int adjX, int adjY) {
        for (int y = 0; y < currPiece.getSprites()[0].length; y++) {
            for (int x = 0; x < currPiece.getSprites()[0][0].length(); x++) {
                if (currPiece.getSprites()[currPiece.getRotation()][y].charAt(x) != '.') {
                    try {
                        if (grid[currPiece.getY() + y + adjY][currPiece.getX() + x + adjX] != '.') {
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Move the current piece left, if possible.
     */
    void moveLeft() {
        eraseCurrPiece();
        if (canMove(-1, 0)) {
            currPiece.move(-1, 0);
        }
        drawCurrPiece();
    }

    /**
     * Move the current piece right, if possible.
     */
    void moveRight() {
        eraseCurrPiece();
        if (canMove(1, 0)) {
            currPiece.move(1, 0);
        }
        drawCurrPiece();
    }

    /**
     * Move the current piece down, if possible.
     */
    void moveDown() {
        eraseCurrPiece();
        if (canMove(0, 1)) {
            currPiece.move(0, 1);
            drawCurrPiece();
        } else {
            drawCurrPiece();
            currPiece = null;
        }
    }

    /**
     * Drop the current piece down, if possible.
     */
    void dropDown() {
        eraseCurrPiece();
        while (canMove(0, 1)) {
            currPiece.move(0, 1);
        }
        drawCurrPiece();
        currPiece = null;
    }

    /**
     * Return true if and only if the current piece can be rotated.
     *
     * @param direction The direction to be rotated.
     * @return True if the piece can be rotated, false otherwise.
     */
    private boolean canRotate(int direction) {
        for (int y = 0; y < currPiece.getSprites()[0].length; y++) {
            for (int x = 0; x < currPiece.getSprites()[0][0].length(); x++) {
                int newRot = (currPiece.getRotation() + direction) % currPiece.getSprites().length;
                if (currPiece.getSprites()[newRot][y].charAt(x) != '.') {
                    try {
                        if (grid[currPiece.getY() + y][currPiece.getX() + x] != '.') {
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Rotate the current piece clockwise, if possible.
     */
    void rotateClockwise() {
        eraseCurrPiece();
        if (canRotate(1)) {
            currPiece.rotate(1);
        }
        drawCurrPiece();
    }

    /**
     * Rotate the current piece counterclockwise, if possible.
     */
    void rotateCounterClockwise() {
        eraseCurrPiece();
        if (canRotate(currPiece.getSprites().length - 1)) {
            currPiece.rotate(currPiece.getSprites().length - 1);
        }
        drawCurrPiece();
    }

    /**
     * Return true if and only if line n is full.
     *
     * @param n The index of the line to be inspected.
     * @return True if line n is completely full, false otherwise.
     */
    private boolean isFull(int n) {
        for (int x = 0; x < width; x++) {
            if (grid[n][x] == '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * Clear line n and shift all lines above down by one.
     *
     * @param n The index of the line to be cleared.
     */
    private void clearLine(int n) {
        for (int y = n; y > 0; y--) { // updates lines 1-n
            grid[y] = grid[y - 1].clone();
        }
        Arrays.fill(grid[0], '.'); // updates line 0
    }

    /**
     * Clear any lines, if possible, and return the number of lines cleared.
     *
     * @return The number of lines cleared.
     */
    int clearLines() {
        int linesCleared = 0;
        for (int y = 0; y < height; y++) {
            if (isFull(y)) {
                clearLine(y);
                linesCleared += 1;
            }
        }
        return linesCleared;
    }
}
