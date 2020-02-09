package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.NoteGenerator;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column.Column;
import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGamePointsSystem;

/**
 * At defined intervals, generate a note in a random column.
 */
public class RandomNoteGenerator extends NoteGenerator {
    public enum Difficulty { EASY, NORMAL, HARD, IMPOSSIBLE}
    private RhythmGamePointsSystem pointsSystem;

    private int noteGenerationPeriod;
    private long lastNoteTime;
    private boolean isRunning;

    /**
     * Constructs a note generator that generates notes in columns, randomly
     * @param columns the columns of a level
     * @param pointsSystem the points system of a level
     */
    public RandomNoteGenerator(Column[] columns, RhythmGamePointsSystem pointsSystem) {
        super(columns);
        setDifficulty(Difficulty.EASY);
        this.pointsSystem = pointsSystem;
        lastNoteTime = 0;
        isRunning = false;
    }

    /**
     * Begins and allows note generation.
     */
    @Override
    public void start() {
        lastNoteTime = 0;
        isRunning = true;
    }

    /**
     * Updates it by one unit time.
     */
    @Override
    public void timeUpdate() {
        if (isRunning) {
            lastNoteTime += 30;

            // Every period generate a note at a random column
            if (lastNoteTime >= noteGenerationPeriod) {
                getColumns()[(int) (getColumns().length * Math.random())].generateNote();
                lastNoteTime = 0;
            }

            if (pointsSystem.getPoints() >= 300)
                setDifficulty(Difficulty.HARD);
            else if (pointsSystem.getPoints() >= 100)
                setDifficulty(Difficulty.NORMAL);
            else
                setDifficulty(Difficulty.EASY);
        }
    }

    /**
     * Ends and disallows note generation.
     */
    @Override
    public void stop() {
        isRunning = false;
    }

    /**
     * Returns whether it is done.
     * @return false
     */
    @Override
    public boolean getIsOver() {
        return false;
    }

    /**
     * Sets the difficulty of the game by generating notes more or less frequently
     * @param diff difficulty of the level
     */
    private void setDifficulty(Difficulty diff) {
        switch(diff) {
            case EASY:
                noteGenerationPeriod = 900;
                break;
            case NORMAL:
                noteGenerationPeriod = 700;
                break;
            case HARD:
                noteGenerationPeriod = 500;
                break;
            case IMPOSSIBLE:
                noteGenerationPeriod = 200;
                break;
            default:
                noteGenerationPeriod = 901;
                break;
        }
    }
}
