package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.EndLevelStrategy;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGamePointsSystem;

/**
 * Determines that the level is over based on a maximum number of notes missed.
 */
public class EndByLives extends EndRhythmLevelStrategy {
    private int numLives;
    private RhythmGamePointsSystem pointsSystem;

    public EndByLives(int numLives, RhythmGamePointsSystem pointsSystem) {
        this.numLives = numLives;
        this.pointsSystem = pointsSystem;
    }

    /**
     * Returns if the level is over.
     * @return true if and only if the number of notes missed is
     * greater than or equal to the maximum number of lives.
     */
    @Override
    public boolean getIsLevelOver() {
        return pointsSystem.getNumMissed() >= numLives;
    }
}
