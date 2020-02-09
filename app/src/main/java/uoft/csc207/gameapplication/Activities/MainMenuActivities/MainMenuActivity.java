package uoft.csc207.gameapplication.Activities.MainMenuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uoft.csc207.gameapplication.Activities.GameActivities.GameOptionsActivity;
import uoft.csc207.gameapplication.R;

/**
 * Set up the main menu, creating four options for the users to choose by buttons.
 */
public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button playButton =  findViewById(R.id.play_button);
        Button leaderboardButton =  findViewById(R.id.leaderboard_button);
        Button scoreButton =  findViewById(R.id.score_button);
        Button customizeButton =  findViewById(R.id.customize_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameOptionsActivity = new Intent(MainMenuActivity.this, GameOptionsActivity.class);
                startActivity(gameOptionsActivity);
            }

        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leaderboardOptionsActivity = new Intent(MainMenuActivity.this, LeaderboardOptionsActivity.class);
                startActivity(leaderboardOptionsActivity);
            }

        });

        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personalScoresOptionsActivity = new Intent(MainMenuActivity.this, PersonalScoresOptionsActivity.class);
                startActivity(personalScoresOptionsActivity);
            }
        });

        customizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customizeActivity = new Intent(MainMenuActivity.this, CustomizeActivity.class);
                startActivity(customizeActivity);
            }
        });
    }
}
