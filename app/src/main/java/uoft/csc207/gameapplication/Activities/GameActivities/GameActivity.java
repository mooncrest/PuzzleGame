package uoft.csc207.gameapplication.Activities.GameActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import uoft.csc207.gameapplication.Games.GameDriver;
import uoft.csc207.gameapplication.Games.GameWrapper.GameWrapperDriver;
import uoft.csc207.gameapplication.Games.MazeGame.MazeGameDriver;
import uoft.csc207.gameapplication.Games.RhythmGame.RhythmGameDriver;
import uoft.csc207.gameapplication.Games.TetrisGame.TetrisGameDriver;
import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Utility.JSONFileRW;
import uoft.csc207.gameapplication.Utility.TryGetJSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {
    GameView gameView;
    private GameDriver gameDriver;

    private Timer timer = new Timer();
    private TimerTask checkIsGameOver = new TimerTask() {
        @Override
        public void run() {
            if (gameDriver.getGameIsOver()) {
                finish();
            }
        }
    };

    // Configurations and customizations for the games.
    private static final String CUSTOMIZATIONS_FILE = "Customize.json";
    private static final String COLOUR_PALETTES_FILE = "ColourPalettes.json";
    private JSONObject tetrisConfigurations;
    private JSONObject rhythmConfigurations;
    private JSONObject mazeConfigurations;
    private JSONObject allConfigurations;
    private Map<String, Integer> colourScheme;



    /**
     * Initializes the game on create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.GameView);

        loadCustomizeJSON();
        loadColourPaletteJSON();

        // Determines which game to play
        boolean defaultCaseRan = false;
        String gameType = getIntent().getStringExtra("gameType");
        switch (gameType) {
            case "GameWrapper":
                gameDriver = new GameWrapperDriver();
                gameView.setStage("1");
                break;
            case "TetrisGame":
                gameDriver = new TetrisGameDriver();
                gameView.setStage("2");
                break;
            case "RhythmGame":
                gameDriver = new RhythmGameDriver();
                gameView.setStage("3");
                break;
            case "MazeGame":
                gameDriver = new MazeGameDriver();
                gameView.setStage("4");
                break;
            default:
                gameDriver = new GameWrapperDriver();
                defaultCaseRan = true;
                break;
        }

        if (!defaultCaseRan) {
            gameView.setGame(gameType);
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        gameDriver.setMetrics(metrics);
        gameDriver.setContext(this);
        gameDriver.setColourScheme(colourScheme);
        setDriverConfigurations(gameType);
        gameDriver.init();

        gameView.setDriver(gameDriver);
    }

    /**
     * Loads the configurations
     */
    private void loadCustomizeJSON() {
        JSONFileRW customizeFileRW = new JSONFileRW(CUSTOMIZATIONS_FILE, this);
        allConfigurations = customizeFileRW.load();

        if (allConfigurations != null) {
            try {
                tetrisConfigurations = allConfigurations.getJSONObject("tetris");
                rhythmConfigurations = allConfigurations.getJSONObject("rhythm");
                mazeConfigurations = allConfigurations.getJSONObject("maze");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the colour scheme based on the selected theme.
     */
    private void loadColourPaletteJSON() {
        JSONFileRW colourFileRW = new JSONFileRW(COLOUR_PALETTES_FILE, this);
        JSONObject palettes = colourFileRW.load();
        if (palettes != null) {
            try {
                // Gets the themme and parses it into a the colour scheme map.
                String theme = tetrisConfigurations.getString("colours");
                JSONObject colourPalette = palettes.getJSONObject(theme);
                Map<String, Integer> colourScheme = new HashMap<>();
                for (int i = 1; i <= 7; i++) {
                    String key = String.format(Locale.CANADA, "BlockColour%d", i);
                    JSONObject colourRGB = colourPalette.getJSONObject(key);
                    Integer colour = Color.rgb(colourRGB.getInt("r"),
                            colourRGB.getInt("g"), colourRGB.getInt("b"));
                    colourScheme.put(key, colour);
                }

                this.colourScheme = Collections.unmodifiableMap(colourScheme);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set up the driver's configuration for a game type.
     *
     * @param gameType The game type to have configuration set up.
     */
    private void setDriverConfigurations(String gameType) {
        switch (gameType) {
            case "GameWrapper":
                setRhythmConfigurations(gameType);
                gameDriver.setConfigurations(allConfigurations);
                break;
            case "TetrisGame":
                gameDriver.setConfigurations(tetrisConfigurations);
                break;
            case "RhythmGame":
                setRhythmConfigurations(gameType);
                gameDriver.setConfigurations(rhythmConfigurations);
                break;
            case "MazeGame":
                gameDriver.setConfigurations(mazeConfigurations);
                break;
            default:
                gameDriver.setConfigurations(new JSONObject());
                break;
        }
    }

    /**
     * Set up Rhythm Game's configurations.
     */
    private void setRhythmConfigurations(String gameType) {
        StringBuilder jsonString = new StringBuilder("{\n");
        // Copies the shape configurations
        for (int i = 1; i <= 4; i++) {
            jsonString.append(String.format(Locale.CANADA, "      \"shape%d\" : \"", i));
            jsonString.append(TryGetJSON.tryGetString(rhythmConfigurations, String.format(Locale.CANADA,
                    "shape%d", i), "O"));
            jsonString.append("\",\n");
        }

        // Determines presenter and level configurations based on game type selected
        switch (gameType) {
            case "GameWrapper":
                String wrapperConfig =
                        "      \"presenter\": \"MISSED\",\n" +
                                "      \"levels\": [\n" +
                                "        {\n" +
                                "          \"numColumns\": 4,\n" +
                                "          \"height\": 100,\n" +
                                "          \"song\": \"Mii Channel\",\n" +
                                "          \"mode\": \"RANDOM\" \n" +
                                "        }\n" +
                                "      ]\n" +
                                "  }";
                jsonString.append(wrapperConfig);
                break;
            case "RhythmGame":
                String individualConfig =
                        "      \"presenter\": \"STATS\",\n" +
                                "      \"levels\": [\n" +
                                "        {\n" +
                                "          \"numColumns\": 3,\n" +
                                "          \"height\": 100,\n" +
                                "          \"song\": \"Mii Channel\",\n" +
                                "          \"mode\": \"SONG\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"numColumns\": 4,\n" +
                                "          \"height\": 100,\n" +
                                "          \"song\": \"Old Town Road\",\n" +
                                "          \"mode\": \"SONG\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"numColumns\": 4,\n" +
                                "          \"height\": 80,\n" +
                                "          \"song\": \"Mii Channel\",\n" +
                                "          \"mode\": \"SONG\"\n" +
                                "        }\n" +
                                "      ]\n" +
                                "  }";
                jsonString.append(individualConfig);
                break;
            default:
                break;
        }
        try {
            rhythmConfigurations = new JSONObject(jsonString.toString());
            allConfigurations.put("rhythm", rhythmConfigurations);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Resumes the game and starts threads.
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameView.start();
        timer.scheduleAtFixedRate(checkIsGameOver, 0, 100);
    }

    /**
     * Ends thread-related processes.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameView.stop();

        timer.cancel();
        timer.purge();
    }
}
