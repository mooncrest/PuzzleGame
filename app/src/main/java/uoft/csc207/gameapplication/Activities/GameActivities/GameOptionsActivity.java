package uoft.csc207.gameapplication.Activities.GameActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Utility.GameRequestService.CallBack;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.GameDataService.GetUserService;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.UserVerificationServices.LoginService;

/**
 * Set up the board of choosing different games.
 */
public class GameOptionsActivity extends AppCompatActivity {
    GetUserService getUserService;

    private int currentStage;

    /**
     * Let the game choosing menu get into the Created state.
     *
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);

        getUserService = new GetUserService();
        getUserService.setContext(this);

        getUserService.getUser(LoginService.getLoginToken(), new CallBack() {
            @Override
            public void onSuccess() {
                System.out.println(getUserService.getUser().getCurrentStage());
                currentStage = Integer.valueOf(getUserService.getUser().getCurrentStage());
                initialize();
            }

            @Override
            public void onWait() {
                System.out.println("waiting");
            }

            @Override
            public void onFailure() {
                System.out.println("failed to get user");
            }
        });
    }

    /**
     * Initialize and set up the order for playing games.
     */
    private void initialize() {
        Button gameWrapperButton = findViewById(R.id.game_wrapper);
        Button tetrisGameButton = findViewById(R.id.tetris_game);
        Button rhythmGameButton = findViewById(R.id.rhythm_game);
        Button mazeGameButton = findViewById(R.id.maze_game);

        // Based on button clicked, saves information for the next activity as to what game
        // the user wants to play. Also determines if the user has unlocked the game before playing.

        gameWrapperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameWrapperActivity = new Intent(GameOptionsActivity.this, GameActivity.class);
                gameWrapperActivity.putExtra("gameType", "GameWrapper");
                startActivity(gameWrapperActivity);
                finish();
            }

        });

        tetrisGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStage > 0) {
                    Intent tetrisGameActivity = new Intent(GameOptionsActivity.this, GameActivity.class);
                    tetrisGameActivity.putExtra("gameType", "TetrisGame");
                    startActivity(tetrisGameActivity);
                    finish();
                } else {
                    showToast("This level is not yet unlocked. " +
                            "You must first play all previoius games.");
                }
            }

        });

        rhythmGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStage > 1) {
                    Intent rhythmGameActivity = new Intent(GameOptionsActivity.this, GameActivity.class);
                    rhythmGameActivity.putExtra("gameType", "RhythmGame");
                    startActivity(rhythmGameActivity);
                    finish();
                } else {
                    showToast("This level is not yet unlocked. " +
                            "You must first play all previoius games.");
                }
            }
        });

        mazeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStage > 2) {
                    Intent mazeGameActivity = new Intent(GameOptionsActivity.this, GameActivity.class);
                    mazeGameActivity.putExtra("gameType", "MazeGame");
                    startActivity(mazeGameActivity);
                    finish();
                } else {
                    showToast("This level is not yet unlocked. " +
                            "You must first play all previoius games.");
                }
            }
        });
    }

    /**
     * Displays a toast message.
     *
     * @param text The text to be displayed.
     */
    private void showToast(String text) {
        Toast.makeText(GameOptionsActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
