package uoft.csc207.gameapplication.Games.RhythmGame.Presenter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGamePointsSystem;

/**
 * Displays the number of perfect, great, good, and missed notes.
 */
class MainStatsDrawer extends StatsDrawer {
    private Paint upperRectPaint;

    /**
     * Initializes necessary paints.
     */
    void setUpPaints() {
        super.setUpPaints();

        upperRectPaint = new Paint();
        upperRectPaint.setColor(Color.BLACK);
    }

    /**
     * Draws the statistics of number of perfect, great, good, and missed notes.
     * @param canvas the canvas to draw on.
     * @param pointsSystem the points system.
     * @param left the left most side of the drawing area.
     * @param top the top most side of the drawing area.
     * @param right the right most side of the drawing area.
     * @param bottom the bottom mose side of the drawing area.
     */
    @Override
    void drawStats(Canvas canvas, RhythmGamePointsSystem pointsSystem,
                          float left, float top, float right, float bottom) {
        canvas.drawRect(left, top, right, bottom, upperRectPaint);

        int leftSpacing = 80;
        int upperSpacing = 70;
        canvas.drawText("Perfect: " + pointsSystem.getNumPerfect(),
                left + leftSpacing, top + upperSpacing, getGoodStatsPaint());
        canvas.drawText("Great: " + pointsSystem.getNumGreat(),
                left + leftSpacing, (top + bottom)/2 + upperSpacing, getGoodStatsPaint());

        canvas.drawText("Good: " + pointsSystem.getNumGood(),
                (left + right)/2 + leftSpacing, top + upperSpacing, getGoodStatsPaint());
        canvas.drawText("Missed: " + pointsSystem.getNumMissed(),
                (left + right)/2 + leftSpacing, (top + bottom)/2 + upperSpacing, getBadStatsPaint());
    }
}
