package uoft.csc207.gameapplication.Games.RhythmGame.GameLogic.NoteGenerator;

import android.content.Context;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Queue;


class NoteIntervalsReader {
    private Queue<Pair<Double, Integer>> notesQueue;

    NoteIntervalsReader(String fileName, Context context) {
        notesQueue = new ArrayDeque<>();
        read(fileName, context);
    }

    /**
     * Reads the file by parsing it into the notesQueue
     * @param fileName the file name .csv
     * @param context the context of the activity
     */
    private void read(String fileName, Context context) {
        try (InputStream raw = context.getAssets().open(fileName)) {
            BufferedReader is = new BufferedReader(new InputStreamReader(raw, StandardCharsets.UTF_8));

            String currentLine = is.readLine();
            while (currentLine != null) {
                String[] pair = currentLine.split(",");
                double interval = Double.valueOf(pair[0]);
                Integer column = Integer.valueOf(pair[1].trim());
                notesQueue.add(new Pair<>(interval, column));

                currentLine = is.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Queue<Pair<Double, Integer>> getNotesQueue() {
        return notesQueue;
    }
}