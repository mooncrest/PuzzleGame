package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.NoteGenerator;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column.Column;

/**
 * Generates notes in the Rhythm columns.
 */
public abstract class NoteGenerator {
    private Column[] columns;

    NoteGenerator(Column[] columns) {
        this.columns = columns;
    }
    public abstract void timeUpdate();

    public abstract void start();

    public abstract void stop();

    public abstract boolean getIsOver();

    Column[] getColumns() {
        return columns;
    }
}
