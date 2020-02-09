package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic;

/**
 * The points and statistics tracker for a level of the Rhythm game.
 */
public class RhythmGamePointsSystem {
    public enum NoteEvent {PERFECT, GREAT, GOOD, BAD, MISSED}

    private int numPerfect, numGreat, numGood, numBad, numMissed = 0;
    private int points = 0;

    /**
     * Updates the points system.
     * @param event a significant change in state of a note.
     */
    public void update(NoteEvent event) {
        switch(event) {
            case PERFECT:
                numPerfect++;
                addPoints(10);
                break;
            case GREAT:
                numGreat++;
                addPoints(8);
                break;
            case GOOD:
                numGood++;
                addPoints(5);
                break;
            case BAD:
                numBad++;
                addPoints(-1);
                break;
            case MISSED:
                numMissed++;
                break;
            default:
                break;
        }
    }

    private void addPoints(int dPoints) {
        this.points = Math.max(0, this.points + dPoints);
    }

    public int getNumPerfect() {
        return numPerfect;
    }

    public int getNumGreat() {
        return numGreat;
    }

    public int getNumGood() {
        return numGood;
    }

    public int getNumBad() {
        return numBad;
    }

    public int getNumMissed() {
        return numMissed;
    }

    public int getPoints() {
        return points;
    }
}
