package uoft.csc207.gameapplication.Games.RhythmGame.Presenter;

import android.graphics.Canvas;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGamePointsSystem;

/**
 * Displays the number of missed notes on a canvas.
 */
public class MissedStatsDrawer extends StatsDrawer {
    /**
     * Draws the number of missed notes.
     * @param canvas the canvas to draw on.
     * @param pointsSystem the points system.
     * @param left the left most side of the drawing area.
     * @param top the top most side of the drawing area.
     * @param right the right most side of the drawing area.
     * @param bottom the bottom mose side of the drawing area.
     */
    @Override
    public void drawStats(Canvas canvas, RhythmGamePointsSystem pointsSystem,
                          float left, float top, float right, float bottom) {
        canvas.drawText("Missed: " + pointsSystem.getNumMissed(), (left + right) /2,
                (top + bottom) / 2, getBadStatsPaint());
    }
}
