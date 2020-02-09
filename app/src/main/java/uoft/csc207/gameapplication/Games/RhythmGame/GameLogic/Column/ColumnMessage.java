package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column;

/**
 * A message associated with a column that keeps track of its own length of existence.
 */
public class ColumnMessage {
    private String message;

    // the number of iterations it has existed in the column
    private int numIterExisted = 0;

    ColumnMessage(String msg) {
        this.message = msg;
    }

    String getMessage() {
        return message;
    }

    void incrementNumIterationsExisted() {
        numIterExisted += 1;
    }

    int getNumIterExisted() {
        return numIterExisted;
    }

}
