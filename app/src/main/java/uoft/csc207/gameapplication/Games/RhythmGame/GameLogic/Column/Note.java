package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column;

/**
 * A note in the game.
 */
public class Note {
    private int y;  // vertical position

    Note(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    void moveUp(int unit) {
        y -= unit;
    }
}
