package uoft.csc207.gameapplication.Games.MazeGame.Presenter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class DrawingBlock {
    /**
     * the dimension and position of this drawing block
     */
    private int xCord;
    private int yCord;
    private int sideLength;

    /**
     * a integer representation of the stage we are currently drawing on
     */
    private int blockDrawingStage;

    /**
     * the paints used on each stage
     */
    private Paint stageOnePaint;
    private Paint stageTwoPaint;
    private Paint finalPaint;

    /**
     * the status on the life cycle of this block
     */
    private boolean deleteBlock = false;
    private boolean deleteStatus = false;

    /**
     * initializes this drawing block and will begin animations as soon as draw is called
     * @param xCord the x coordinate of this block on the 2d array
     * @param yCord the y coordinate of this block on the 2d array
     * @param sideLength the side length of this square.
     */
    public DrawingBlock(int xCord, int yCord, int sideLength) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.sideLength = sideLength;
        blockDrawingStage = 0;
    }

    /**
     * sets the paint required for each stage of the block;
     * @param paints a array of 3 paints used to give colour to each stage
     */
    void setPaints(Paint[] paints) {
        this.stageOnePaint = paints[0];
        this.stageTwoPaint = paints[1];
        this.finalPaint = paints[2];
    }

    /**
     * How the fade away and growing effect is created and draws this block to the canvas.
     * @param canvas a graphics interface to be drawn on
     */
    public void draw(Canvas canvas) {
        // by default blockDrawingStage is set to 0 so it will increase until 3 and stop
        if (blockDrawingStage == -1) {
            deleteStatus = true;
        }

        else if (blockDrawingStage <= 1) {
            int stageBlockWidth = sideLength / 3;
            int topX = xCord * sideLength + (sideLength - stageBlockWidth) / 2;
            int topY = yCord * sideLength + (sideLength - stageBlockWidth) / 2;
            int bottomX = topX + stageBlockWidth;
            int bottomY = topY + stageBlockWidth;
            Rect rect = new Rect(topX, topY, bottomX, bottomY);
            canvas.drawRect(rect, stageOnePaint);
            if (!deleteBlock) {
                blockDrawingStage++;
            }
        }

        else if (blockDrawingStage == 2) {
            int stageBlockWidth = sideLength * 2 / 3;
            int topX = xCord * sideLength + (sideLength - stageBlockWidth) / 2;
            int topY = yCord * sideLength + (sideLength - stageBlockWidth) / 2;
            int bottomX = topX + stageBlockWidth;
            int bottomY = topY + stageBlockWidth;
            Rect rect = new Rect(topX, topY, bottomX, bottomY);
            canvas.drawRect(rect, stageTwoPaint);
            if (!deleteBlock) {
                blockDrawingStage++;
            }
        }

        else if (blockDrawingStage == 3) {
            Rect rect = new Rect(xCord * sideLength, yCord * sideLength,
                           (xCord + 1) * sideLength, (yCord + 1) * sideLength);
            canvas.drawRect(rect, finalPaint);
        }

        // when deleteBlock method is invoked it will draw stages in reverse until deleteStatus
        // is true ie when blockDrawingStage is -1;
        if (deleteBlock && !deleteStatus) {
            blockDrawingStage--;
        }

    }

    /**
     * starts deletion animation to create a fancy fade away animation
     */
    void deleteBlock() {
        this.deleteBlock = true;
    }

    /**
     * Gets the status on this block to see if it is at its end of its life
     * @return whether we should delete this block from hogging memory
     */
    boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * Checks to see if two blocks are equal in this case if they have the same coordinate.
     * @param block another block to compare to
     * @return if this block and the other block has the same (x,y) coordinates
     */
    boolean equals(DrawingBlock block) {
        return this.xCord == block.xCord && this.yCord == block.yCord;
    }
}
