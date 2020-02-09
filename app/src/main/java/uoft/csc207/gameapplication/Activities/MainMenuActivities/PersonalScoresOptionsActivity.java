package uoft.csc207.gameapplication.Activities.MainMenuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uoft.csc207.gameapplication.R;

/**
 * The Option menu of specific scores for the users.
 */
public class PersonalScoresOptionsActivity extends AppCompatActivity {
    /**
     * Let the score menu get into the Created state, with four different options.
     *
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_scores_options);

        Button gameWrapperScoresButton =  findViewById(R.id.wrapper_scores_button);
        Button tetrisGameScoresButton =  findViewById(R.id.tetris_scores_button);
        Button rhythmGameScoresButton =  findViewById(R.id.rhythm_scores_button);
        Button mazeGameScoresButton =  findViewById(R.id.maze_scores_button);

        // Based on button clicked, saves information for the next activity as to what game
        // the user wants to see their personal scores for.

        gameWrapperScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameWrapperScoresActivity = new Intent(PersonalScoresOptionsActivity.this, PersonalScoresActivity.class);
                gameWrapperScoresActivity.putExtra("scoresType", "WrapperGame");
                startActivity(gameWrapperScoresActivity);
            }

        });

        tetrisGameScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tetrisGameScoresActivity = new Intent(PersonalScoresOptionsActivity.this, PersonalScoresActivity.class);
                tetrisGameScoresActivity.putExtra("scoresType", "TetrisGame");
                startActivity(tetrisGameScoresActivity);

            }

        });

        rhythmGameScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rhythmGameScoresActivity = new Intent(PersonalScoresOptionsActivity.this, PersonalScoresActivity.class);
                rhythmGameScoresActivity.putExtra("scoresType", "RhythmGame");
                startActivity(rhythmGameScoresActivity);

            }
        });

        mazeGameScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mazeGameScoresActivity = new Intent(PersonalScoresOptionsActivity.this, PersonalScoresActivity.class);
                mazeGameScoresActivity.putExtra("scoresType", "MazeGame");
                startActivity(mazeGameScoresActivity);

            }
        });
    }
}
