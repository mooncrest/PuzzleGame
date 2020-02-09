package uoft.csc207.gameapplication.Games.RhythmGame.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.SparseArray;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGamePointsSystem;
import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column.Note;
import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.RhythmGameLevel;
import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column.Target;

/**
 * Presents the game to screen.
 */
public class RhythmGamePresenter {
    private RhythmGameLevel level;
    private RhythmGamePointsSystem pointsSystem;
    private Context context;
    private int numColumns;

    private Bitmap bitmap;
    private Canvas bitCanvas;

    /* Scaling and Sizing variables */
    private int screenWidth, screenHeight;
    private float colSize;
    private float targetScale, noteScale;
    private float heightRatio;
    private int bitmapTop;

    /* Graphic variables for drawing */
    private Paint[] columnPaints;
    private Paint targetPaint;
    private NoteShape[] colUnitNoteShapes;
    private char[] shapes;
    private Map<String, Integer> colourScheme;
    private Paint goodMessagePaint;
    private Paint badMessagePaint;

    private StatsDrawer statsDrawer;
    private MediaPlayer mediaPlayer;

    private static final Map<Character, String> BLOCK_TO_SCHEME_KEYS = createKeysMap();
    private static final Map<Character, Integer[][]> TETRO_COORDINATES = createTetroCoordinatesMap();
    private static final Map<String, Integer> SONG_IDS = createSongIdsMap();
    private static final Map<String, StatsDrawer> STATS_DRAWERS = createStatsDrawersMap();

    /**
     * Contructs a presenter for the Rhythm Game.
     * @param level the current level of the game.
     * @param metrics the screen metrics.
     * @param context the activity's context.
     * @param shapes the note shapes.
     * @param colourScheme the colour palette.
     * @param statDrawerMode the statistics presentation mode.
     */
    public RhythmGamePresenter(RhythmGameLevel level, DisplayMetrics metrics, Context context,
                               char[] shapes, Map<String, Integer> colourScheme, String statDrawerMode) {
        this.context = context;
        this.shapes = shapes;
        this.colourScheme = colourScheme;
        this.screenWidth = metrics.widthPixels;
        this.screenHeight = metrics.heightPixels - 40;
        this.statsDrawer = STATS_DRAWERS.get(statDrawerMode);
        setLevel(level);
    }

    /**
     * Initializes all instances associated with the specific level.
     * @param level the level of the Rhythm game.
     */
    public void setLevel(RhythmGameLevel level) {
        this.level = level;
        this.numColumns = level.getNumColumns();
        this.pointsSystem = level.getPointsSystem();

        setTheme();
        // Set the song
        Integer rawSongId = SONG_IDS.get(level.getSong());
        if (rawSongId == null) rawSongId = R.raw.mii_channel;
        this.mediaPlayer = MediaPlayer.create(context, rawSongId);

        initializeSizing();
    }

    /**
     * Starts the song.
     */
    public void start() {
        mediaPlayer.start();
    }

    /**
     * Stops the song.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Draws the game.
     * @param canvas the canvas to draw on.
     */
    public void draw(Canvas canvas){
        bitCanvas.save();
        bitCanvas.drawColor(Color.WHITE);

        Target[] targets = level.getAllTargets();
        SparseArray<List<Note>> notesMap = level.getAllNotes();
        String[] messages = level.getMessages();

        // draws the target, the notes, and the messages in each column
        for (int i = 0; i < numColumns; i++) {
            NoteShape scalableCopy = colUnitNoteShapes[i].copy();

            drawTarget(targets[i], scalableCopy, i);

            List<Note> notes = notesMap.get(i);
            drawNotes(notes, scalableCopy, i);

            drawMessage(messages[i], targets[i], i);
        }

        statsDrawer.drawStats(bitCanvas, pointsSystem, 0,-bitmapTop,
                screenWidth, targets[0].getY() * heightRatio-10);

        canvas.drawBitmap(bitmap, 0, bitmapTop, null);
        bitCanvas.restore();
    }

    // Sets up shapes and colours for each column and paints.
    private void setTheme() {
        // Sets up the shape theme for each column
        colUnitNoteShapes = new NoteShape[numColumns];
        for (int i = 0; i < numColumns; i++) {
            Integer[][] coordinates = TETRO_COORDINATES.get(shapes[i]);
            if (coordinates == null) coordinates = TETRO_COORDINATES.get('O');

            TetrominoShape tetrominoShape = new TetrominoShape(new Tetromino(coordinates));
            colUnitNoteShapes[i] = new NoteShape(tetrominoShape);
        }

        // Sets up the colour theme for each column
        columnPaints = new Paint[numColumns];
        for (int i = 0; i < numColumns; i++) {
            columnPaints[i] = new Paint();
            String schemeKey = BLOCK_TO_SCHEME_KEYS.get(shapes[i]);
            Integer colour = colourScheme.get(schemeKey);
            if (colour != null) columnPaints[i].setColor(colour);
        }

        targetPaint = new Paint();
        targetPaint.setColor(Color.GRAY);

        goodMessagePaint = new Paint();
        goodMessagePaint.setTextSize(50);
        goodMessagePaint.setColor(Color.GREEN);

        badMessagePaint = new Paint();
        badMessagePaint.setTextSize(50);
        badMessagePaint.setColor(Color.RED);

        statsDrawer.setUpPaints();
    }

    // Initialize sizing, scaling, and dimensions variables.
    private void initializeSizing() {
        colSize = screenWidth / (float) numColumns;
        float colWidthRatio = colSize / 2; // Since each piece is 2 units wide

        // target width is 70% the width of the column
        targetScale = (float) 0.7 * colWidthRatio;      // the new size of one unit length for target

        // note width is 60% the width of the column
        noteScale = (float) 0.6 * colWidthRatio;    // the new size of one unit length for note

        bitmapTop = -3 * (int) Math.ceil(noteScale);
        heightRatio = (screenHeight - bitmapTop) / level.getGameHeight();
        bitmap = Bitmap.createBitmap(screenWidth,
                screenHeight - bitmapTop, Bitmap.Config.ARGB_8888);
        bitCanvas = new Canvas(bitmap);
    }

    private void drawTarget(Target target, NoteShape scalableShape, int colId) {
        scalableShape.setScale(targetScale);
        // centre the target in the column
        scalableShape.draw(bitCanvas, centreInColumn(colId, 2 * targetScale),
                target.getY() * heightRatio, targetPaint);
    }

    private void drawNotes(List<Note> notes, NoteShape scalableShape, int colId) {
        scalableShape.setScale(noteScale);
        // centre the note in the column
        for (Note note : notes) {
            scalableShape.draw(bitCanvas, centreInColumn(colId, 2 * noteScale),
                    note.getY() * heightRatio, columnPaints[colId]);
        }
    }

    private void drawMessage(String message, Target target, int colId) {
        if (message.length() != 0) {
            Paint messagePaint = goodMessagePaint;
            if (message.charAt(0) == 'B')
                messagePaint = badMessagePaint;

            Rect bounds = new Rect();
            goodMessagePaint.getTextBounds(message, 0, message.length(), bounds);
            int textWidth = bounds.width();

            // draws the message centre of the column, on below of the target
            bitCanvas.drawText(message, centreInColumn(colId, textWidth),
                    target.getY() * heightRatio + 3 * targetScale + 10, messagePaint);
        }
    }

    /**
     * Returns the x-coordinate to be drawn, such that the drawing will be centred within the coloumn
     * @param colId the number of the column
     * @param shapeWidth the width of the shape being drawn
     * @return x-coordinate where the drawing will be centred in the column
     */
    private float centreInColumn(int colId, float shapeWidth) {
        return (float) ((colId + 0.5) * colSize - shapeWidth / 2);
    }

    private static Map<Character,Integer[][]> createTetroCoordinatesMap() {
        Map<Character, Integer[][]> tetroCoordinates = new HashMap<>();
        Integer[][] jCoordinates = {{1, 0}, {1, 1}, {1, 2}, {0, 2}};
        tetroCoordinates.put('J', jCoordinates);
        Integer[][] lCoordinates = {{0, 0}, {0, 1}, {0, 2}, {1, 2}};
        tetroCoordinates.put('L', lCoordinates);
        Integer[][] oCoordinates = {{0, 0}, {1, 0}, {1, 1}, {0, 1}};
        tetroCoordinates.put('O', oCoordinates);
        Integer[][] sCoordinates = {{0, 0}, {0, 1}, {1, 1}, {1, 2}};
        tetroCoordinates.put('S', sCoordinates);
        Integer[][] zCoordinates = {{1, 0}, {1, 1}, {0, 1}, {0, 2}};
        tetroCoordinates.put('Z', zCoordinates);
        Integer[][] tCoordinates = {{0, 0}, {0, 1}, {0, 2}, {1, 1}};
        tetroCoordinates.put('T', tCoordinates);

        return Collections.unmodifiableMap(tetroCoordinates);
    }

    private static Map<String, Integer> createSongIdsMap() {
        Map<String, Integer> songIds = new HashMap<>();
        songIds.put("Old Town Road", R.raw.old_town_road);
        songIds.put("Mii Channel", R.raw.mii_channel);
        songIds.put("Handclap", R.raw.handclap);

        return Collections.unmodifiableMap(songIds);
    }

    private static Map<String, StatsDrawer> createStatsDrawersMap() {
        Map<String, StatsDrawer> statsDrawerMap = new HashMap<>();
        statsDrawerMap.put("", new StatsDrawer());
        statsDrawerMap.put("STATS", new MainStatsDrawer());
        statsDrawerMap.put("MISSED", new MissedStatsDrawer());

        return Collections.unmodifiableMap(statsDrawerMap);
    }

    private static Map<Character, String> createKeysMap() {
        Map<Character, String> keysMap = new HashMap<>();
        keysMap.put('I', "BlockColour1");
        keysMap.put('J', "BlockColour2");
        keysMap.put('L',"BlockColour3");
        keysMap.put('O',"BlockColour4");
        keysMap.put('S', "BlockColour5");
        keysMap.put('Z', "BlockColour6");
        keysMap.put('T', "BlockColour7");

        return Collections.unmodifiableMap(keysMap);
    }
}
