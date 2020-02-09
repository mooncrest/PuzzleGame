package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column;

import java.util.ArrayList;
import java.util.List;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGamePointsSystem;

/**
 * A column of the rhythm, which consists of a target area
 * and the notes to hit within the column.
 */
public class Column {
    private int height;

    private Target target;
    private List<Note> notes;

    private RhythmGamePointsSystem pointsSystem;
    private ColumnMessage message = new ColumnMessage("");

    /**
     * Constructs a column with the given height.
     * @param height the height of a level
     * @param pointsSystem the points system of a level
     */
    public Column(int height, RhythmGamePointsSystem pointsSystem) {
        this.height = height;
        this.target = new Target(height/5, 4);
        this.notes = new ArrayList<>();
        this.pointsSystem = pointsSystem;
    }

    /**
     * Updates the state of the column by one unit time.
     */
    public void timeUpdate() {
        // Moves each note up by one and removes notes outside the column.
        ArrayList<Note> notesCopy = new ArrayList<>(notes);
        for (Note note : notesCopy) {
            note.moveUp(1);

            if (note.getY() < 0) {
                notes.remove(note);
                pointsSystem.update(RhythmGamePointsSystem.NoteEvent.MISSED);
            }
        }

        // Deletes old messages
        if (!message.getMessage().equals("")) {
            message.incrementNumIterationsExisted();
            if (message.getNumIterExisted() >= 20){
                message = new ColumnMessage("");
            }
        }
    }

    /**
     * Generates a note at the bottom of the column.
     */
    public void generateNote() {
        if (lowestNoteHighEnough()) {
            notes.add(new Note(height));
        }
    }

    /**
     * Called when tapped, checks how accurately a note is tapped in the target, if any.
     * Pre-condition: the notes are sorted in ascending order of y-value..
     */
    public void tap() {
        ArrayList<Note> notesCopy = new ArrayList<>(notes);
        for (int i = 0; i < notesCopy.size(); i++) {
            if (notes.get(i).getY() > target.getY() + target.getAllowedError()) {
                // if the highest note (smallest y-value) is below the target
                pointsSystem.update(RhythmGamePointsSystem.NoteEvent.BAD);
                this.message = new ColumnMessage("Bad Hit!");
                return;
            } else if (target.contains(notes.get(i))) {
                int distFromTarget = Math.abs(target.getY() - notes.get(i).getY());

                // Determines the accuracy of the tap
                if (distFromTarget < target.getAllowedError() / (float) 3) {
                    pointsSystem.update(RhythmGamePointsSystem.NoteEvent.PERFECT);
                    this.message = new ColumnMessage("Perfect!");
                } else if (distFromTarget < 2 * target.getAllowedError() / (float) 3) {
                    pointsSystem.update(RhythmGamePointsSystem.NoteEvent.GREAT);
                    this.message = new ColumnMessage("Great!");
                } else {
                    pointsSystem.update(RhythmGamePointsSystem.NoteEvent.GOOD);
                    this.message = new ColumnMessage("Good!");
                }

                // Removes the note contained in the target
                notes.remove(i);
                return;
            }
        }
    }

    private boolean lowestNoteHighEnough() {
        if (notes.size() == 0)
            return true;
        return this.height - notes.get(notes.size()-1).getY() > 2 * target.getAllowedError();
    }

    public Target getTarget() {
        return target;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
