package uoft.csc207.gameapplication.Games.TetrisGame.Presenter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Map;

/**
 * A class responsible for presenting the Tetris game to the screen.
 */
public class TetrisGamePresenter {

    /**
     * The color scheme for the Tetris blocks.
     */
    private Map<String, Integer> colorScheme;

    /**
     * A map between a color held in colorScheme to its respective Tetris piece.
     */
    private Map<Character, String> pieceToColor;

    /**
     * Construct a new TetrisGamePresenter object.
     *
     * @param colorScheme The color scheme for the Tetris blocks.
     */
    public TetrisGamePresenter(Map<String, Integer> colorScheme) {
        this.colorScheme = colorScheme;
        pieceToColor = new HashMap<>();
        pieceToColor.put('I', "BlockColour1");
        pieceToColor.put('J', "BlockColour2");
        pieceToColor.put('L', "BlockColour3");
        pieceToColor.put('O', "BlockColour4");
        pieceToColor.put('S', "BlockColour5");
        pieceToColor.put('Z', "BlockColour6");
        pieceToColor.put('T', "BlockColour7");
    }

    /**
     * Draw the current frame of Tetris to the canvas.
     *
     * @param canvas The canvas to be displayed.
     * @param bitmap The bitmap for drawing.
     * @param grid The 2D array representation of a board to be displayed.
     */
    public void draw(Canvas canvas, Bitmap bitmap, char[][] grid) {
        Canvas newCanvas = new Canvas(bitmap);
        newCanvas.save();
        newCanvas.drawColor(Color.WHITE);
        drawGrid(newCanvas, grid);
        drawBlocks(newCanvas, grid);
        canvas.drawBitmap(bitmap, 88, 88, null);
        newCanvas.restore();
    }

    /**
     * Draw a grid onscreen.
     *
     * @param canvas The canvas to be displayed.
     * @param grid The 2D array representation of a board to be displayed.
     */
    private void drawGrid(Canvas canvas, char[][] grid) {
        int unit = canvas.getWidth() / (grid[0].length + 2);  // length of one unit on the grid
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(3);
        int x = 0;
        for (int i = 0; i < grid[0].length + 1; i++) {
            canvas.drawLine(x, 0, x, unit * grid.length, paint);
            x += unit;
        }
        int y = 0;
        for (int j = 0; j < grid.length + 1; j++) {
            canvas.drawLine(0, y, unit * grid[0].length, y, paint);
            y += unit;
        }
    }

    /**
     * Draw Tetris blocks onscreen.
     *
     * @param canvas The canvas to be displayed.
     * @param grid The 2D array representation of a board to be displayed.
     */
    private void drawBlocks(Canvas canvas, char[][] grid) {
        int unit = canvas.getWidth() / (grid[0].length + 2);  // length of one unit on the grid
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                char block = grid[y][x];
                int xRect = x * unit;
                int yRect = y * unit;
                if (block != '.') {
                    Integer color = colorScheme.get(pieceToColor.get(block));
                    if (color != null) {
                        paint.setColor(color);
                    }
                    Rect rect = new Rect(xRect, yRect, xRect + unit, yRect + unit);
                    canvas.drawRect(rect, paint);
                }
            }
        }
    }
}
