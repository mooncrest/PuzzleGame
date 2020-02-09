package uoft.csc207.gameapplication.Games.RhythmGame.Presenter;

import android.graphics.RectF;

/**
 * A tetromino defined by four blocks of unit length, positioned adjacently.
 */
class TetrominoShape {
    private float height, width;
    private RectF[] unitFigure;

    /**
     * Generates the figure based on the coordinates
     * @param coords the coorinates of the tetromino
     * @return an array of squares that corresponds to the shape of the tetromino
     */
    private static RectF[] generateFigure(Integer[][] coords) {
        RectF[] fourBlocks = new RectF[4];
        for (int i = 0; i < 4; i++) {
            int left = coords[i][0];
            int top = coords[i][1];
            fourBlocks[i] = new RectF(left, top, left + 1, top + 1);
        }
        return fourBlocks;
    }

    TetrominoShape(Tetromino tetromino) {
        this.unitFigure = generateFigure(tetromino.getCoords());
        this.height = tetromino.getHeight();
        this.width = tetromino.getWidth();
    }

    /**
     * Performs a deep copy of the figure
     * @return an array of squares that corresponds to the shape of the tetromino
     */
    RectF[] getCopyOfUnitFigure() {
        RectF[] copy = new RectF[unitFigure.length];
        for (int i = 0; i < unitFigure.length; i++)
            copy[i] = new RectF(unitFigure[i]);
        return copy;
    }

    float getHeight() {
        return height;
    }

    float getWidth() {
        return width;
    }
}
