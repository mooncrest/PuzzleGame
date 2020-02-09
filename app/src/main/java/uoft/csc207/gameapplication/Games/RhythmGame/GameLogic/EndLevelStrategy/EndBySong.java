package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.EndLevelStrategy;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column.Column;
import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.NoteGenerator.NoteGenerator;

/**
 * Determines that the level when the song ends.
 */
public class EndBySong extends EndRhythmLevelStrategy {
    private NoteGenerator noteGenerator;
    private Column[] columns;

    public EndBySong(NoteGenerator noteGenerator, Column[] columns) {
        this.noteGenerator = noteGenerator;
        this.columns = columns;
    }

    /**
     * Returns if the level is over.
     * @return true if and only if the note generator is done generating notes and
     * there are no more notes left in the columns.
     */
    @Override
    public boolean getIsLevelOver() {
        if (noteGenerator.getIsOver()) {
            // If no more notes in all columns, then game is over
            for (Column col : columns) {
                if (!col.getNotes().isEmpty()) return false;
            }
            return true;
        }
        return false;
    }
}
