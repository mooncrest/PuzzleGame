package uoft.csc207.gameapplication.Activities.MainMenuActivities;

import androidx.appcompat.app.AppCompatActivity;

import uoft.csc207.gameapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The Option menu of different games' and the total leader board.
 */
public class LeaderboardOptionsActivity extends AppCompatActivity {
    /**
     * Let the leader board menu get into the Created state, with four different options.
     *
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_options);

        Button gameWrapperLeaderboardButton = findViewById(R.id.wrapper_leaderboard_button);
        Button tetrisGameLeaderboardButton = findViewById(R.id.tetris_scores_button);
        Button rhythmGameLeaderboardButton = findViewById(R.id.rhythm_scores_button);
        Button mazeGameLeaderboardButton = findViewById(R.id.maze_leaderboard_button);

        // Based on button clicked, saves information for the next activity as to what game
        // the user wants to see  the leaderboard for.
        gameWrapperLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameWrapperLeaderboardActivity = new Intent(LeaderboardOptionsActivity.this, LeaderboardActivity.class);
                gameWrapperLeaderboardActivity.putExtra("leaderboardType", "WrapperGame");
                startActivity(gameWrapperLeaderboardActivity);
            }

        });

        tetrisGameLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tetrisGameLeaderboardActivity = new Intent(LeaderboardOptionsActivity.this, LeaderboardActivity.class);
                tetrisGameLeaderboardActivity.putExtra("leaderboardType", "TetrisGame");
                startActivity(tetrisGameLeaderboardActivity);

            }

        });

        rhythmGameLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rhythmGameLeaderboardActivity = new Intent(LeaderboardOptionsActivity.this, LeaderboardActivity.class);
                rhythmGameLeaderboardActivity.putExtra("leaderboardType", "RhythmGame");
                startActivity(rhythmGameLeaderboardActivity);

            }
        });

        mazeGameLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mazeGameLeaderboardActivity = new Intent(LeaderboardOptionsActivity.this, LeaderboardActivity.class);
                mazeGameLeaderboardActivity.putExtra("leaderboardType", "MazeGame");
                startActivity(mazeGameLeaderboardActivity);

            }
        });
    }

}
