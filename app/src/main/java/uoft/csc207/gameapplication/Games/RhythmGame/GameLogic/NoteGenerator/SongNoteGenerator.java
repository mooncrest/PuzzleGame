package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.NoteGenerator;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.Column.Column;


/**
 * Generates notes based on the rhythm of the song
 */
public class SongNoteGenerator extends NoteGenerator {
    private static Map<String, String> SONG_TO_FILE_MAP = createSongFileMap();

    private Queue<Pair<Double, Integer>> notesQueue;
    private Double timeDelay;
    private Integer colNumber;
    private int lastNoteTime;

    private boolean isRunning;
    private boolean isOver;

    public SongNoteGenerator(Column[] columns, String song, Context context) {
        super(columns);
        isOver = false;

        // Determines the file to read, by
        String fileName = SONG_TO_FILE_MAP.get(song);
        if (fileName == null) {
            fileName = "old_town_road_intervals.csv";
        }

        NoteIntervalsReader reader = new NoteIntervalsReader(fileName, context);
        this.notesQueue = reader.getNotesQueue();
    }

    /**
     * Starts and allows note generation.
     */
    @Override
    public void start() {
        lastNoteTime = 0;
        isRunning = getNextNote();
    }

    /**
     * Updates it by one unit time, if allowed.
     */
    @Override
    public void timeUpdate() {
        if (isRunning) {
            lastNoteTime += 30;

            if (!notesQueue.isEmpty()) {
                if (lastNoteTime >= timeDelay * 1000) {
                    getColumns()[colNumber].generateNote();
                    lastNoteTime = 0;
                    getNextNote();
                }
            } else {
                isOver = true;
            }
        }
    }

    /**
     * Gets the time delay and column for the next note, returns if operation is successful.
     * @return false if and only if the queue is empty.
     */
    private boolean getNextNote() {
        Pair<Double, Integer> noteToGenerate;
        if (!notesQueue.isEmpty()) {
            noteToGenerate = notesQueue.poll();
            timeDelay = noteToGenerate.first;
            colNumber = noteToGenerate.second;
            if (colNumber >= getColumns().length) {
                getNextNote();
            }
            return true;
        }
        return false;
    }

    /**
     * Pauses note generation.
     */
    @Override
    public void stop() {
        isRunning = false;
    }

    /**
     * Returns whether the generator is done generating notes.
     * @return true if and only if the generator has no more notes to generate.
     */
    @Override
    public boolean getIsOver() {
        return isOver;
    }

    private static Map<String, String> createSongFileMap() {
        Map<String, String> songToFile = new HashMap<>();
        songToFile.put("Old Town Road", "old_town_road_intervals.csv");
        songToFile.put("Mii Channel", "mii_channel_intervals.csv");

        return Collections.unmodifiableMap(songToFile);
    }
}
