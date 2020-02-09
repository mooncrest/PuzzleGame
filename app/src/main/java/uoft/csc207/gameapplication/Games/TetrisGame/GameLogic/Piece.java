package uoft.csc207.gameapplication.Games.TetrisGame.GameLogic;

/**
 * A representation of a Tetris piece.
 */
class Piece {

    /**
     * The x coordinate of this piece.
     */
    private int x;

    /**
     * The y coordinate of this piece.
     */
    private int y;

    /**
     * An integer representation for the rotation of this piece.
     */
    private int rotation;

    /**
     * 2D string array representations of this piece, for each possible rotation.
     */
    private String[][] sprites;

    /**
     * Construct a new Piece object.
     */
    Piece(int x, int y, int rotation, String[][] sprites) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.sprites = sprites;
    }

    /**
     * Return the x coordinate of this piece.
     *
     * @return The x coordinate of this piece.
     */
    int getX() {
        return x;
    }

    /**
     * Return the y coordinate of this piece.
     *
     * @return The y coordinate of this piece.
     */
    int getY() {
        return y;
    }

    /**
     * Return the integer representation for the rotation of this piece.
     *
     * @return The integer representation for the rotation of this piece.
     */
    int getRotation() {
        return rotation;
    }

    /**
     * Return 2D string array representations of this piece.
     *
     * @return The 2D string array representations of this piece.
     */
    String[][] getSprites() {
        return sprites;
    }

    /**
     * Move this piece in the desired direction.
     *
     * @param adjX The number of units to be moved in the x direction.
     * @param adjY The number of units to be moved in the y direction.
     */
    void move(int adjX, int adjY) {
        x += adjX;
        y += adjY;
    }

    /**
     * Rotate this piece in the desired direction.
     *
     * @param direction The direction to be rotated.
     */
    void rotate(int direction) {
        rotation = (rotation + direction) % sprites.length;
    }
}
