package uoft.csc207.gameapplication.Games.RhythmGame.Presenter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGamePointsSystem;

/**
 * Displays statistics on a canvas.
 */
class StatsDrawer {
    private Paint badStatsPaint;
    private Paint goodStatsPaint;

    /**
     * Initializes necessary paints.
     */
    void setUpPaints() {
        goodStatsPaint = new Paint();
        goodStatsPaint.setTextSize(60);
        goodStatsPaint.setColor(Color.GREEN);

        badStatsPaint = new Paint();
        badStatsPaint.setTextSize(60);
        badStatsPaint.setColor(Color.RED);
    }

    /**
     * Draws the statistics of the level.
     * @param canvas the canvas to draw on.
     * @param pointsSystem the points system.
     * @param left the left most side of the drawing area.
     * @param top the top most side of the drawing area.
     * @param right the right most side of the drawing area.
     * @param bottom the bottom mose side of the drawing area.
     */
    void drawStats(Canvas canvas, RhythmGamePointsSystem pointsSystem,
                          float left, float top, float right, float bottom) {
    }

    Paint getBadStatsPaint() {
        return badStatsPaint;
    }

    Paint getGoodStatsPaint() {
        return goodStatsPaint;
    }
}
