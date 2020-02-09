package uoft.csc207.gameapplication.Activities.MainMenuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Utility.GameRequestService.CallBack;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.LeaderBoard;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.Score;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.GameDataService.LeaderBoardService;

/**
 * Set up the leader board and shows the top players' scores.
 */
public class LeaderboardActivity extends AppCompatActivity {
    private LeaderBoardService scoreService = new LeaderBoardService();
    private String leaderboardType;

    /**
     * The leader board activity gets into the Created state.
     *
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // check leaderboard type
        leaderboardType = getIntent().getExtras().getString("leaderboardType");
        
        scoreService.setContext(this);

        scoreService.getGlobalLeaderboards(leaderboardType, new CallBack() {
            @Override
            public void onSuccess() {
                initialize(scoreService.getLeaderBoard());
            }

            @Override
            public void onFailure() {
                System.out.println("Failed to get users");
            }

            @Override
            public void onWait() {
                System.out.println("Waiting for user info");
            }
        });

    }

    /**
     * Initialize the leader board and show the top ten players.
     *
     * @param leaderBoard The leader board instance to be initialized.
     */
    public void initialize(LeaderBoard leaderBoard) {

        List<Score> scores = leaderBoard.getScores();
        String[] topTenPlayers = new String[10];
        String[] topTenScores = new String[10];

        TextView titleTextView = findViewById(R.id.leaderboardTitle);
        String title = leaderboardType;
        title += " Leaderboard";
        titleTextView.setText(title);

        for (int i = 0; i < 10; i++) {
            Score score = scores.get(i);
            topTenPlayers[i] = score.getUsername();
            topTenScores[i] = score.getScore();
        }

        int[] playerTextViewIds = {R.id.player1, R.id.player2, R.id.player3, R.id.player4,
                R.id.player5, R.id.player6, R.id.player7, R.id.player8, R.id.player9,
                R.id.player10};

        int[] scoreTextViewIds = {R.id.score1, R.id.score2, R.id.score3, R.id.score4, R.id.score5,
                R.id.score6, R.id.score7, R.id.score8, R.id.score9, R.id.score10};

        for (int i = 0; i < 10; i++) {
            TextView playerTextView = findViewById(playerTextViewIds[i]);
            playerTextView.setTextSize(15);
            playerTextView.setText(topTenPlayers[i]);

            TextView scoreTextView = findViewById(scoreTextViewIds[i]);
            scoreTextView.setTextSize(15);
            scoreTextView.setText(topTenScores[i]);

        }

    }

}

