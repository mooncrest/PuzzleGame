package uoft.csc207.gameapplication.Activities.MainMenuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Utility.GameRequestService.CallBack;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.GameDataService.GetUserService;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.UserVerificationServices.LoginService;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.LeaderBoard;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.Score;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.User;

/**
 * Set up users' score and display it on the scoreboard.
 */
public class PersonalScoresActivity extends AppCompatActivity {
    String timePlayedText;
    String totalPointsText;
    String scoresType;

    GetUserService getUserService;

    /**
     * The score activity gets into the Created state.
     *
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_scores);

        // check the game for scores

        scoresType = getIntent().getExtras().getString("scoresType");

        getUserService = new GetUserService();
        getUserService.setContext(this);

        getUserService.getUser(LoginService.getLoginToken(), new CallBack() {
            @Override
            public void onSuccess() {
                initialize(getUserService.getUser());
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
     * Initialize and set up the user's score.
     *
     * @param user The user to be initialized.
     */
    private void initialize(User user) {
        // creates string representation of total point text
        totalPointsText = user.getTotalPoints();
        totalPointsText = "Total Points: \n" + totalPointsText;

        // creates string representation of total time played text
        timePlayedText = user.getTimePlayed();

        int minutesPlayed = Integer.valueOf(timePlayedText) / 60;
        int extraSeconds = Integer.valueOf(timePlayedText) % 60;
        timePlayedText = "Time Played: \n" + minutesPlayed + "m " + extraSeconds + "s";

        List<Score> scores = new ArrayList<>();
        // default if no leaderBoard presented
        for (int i = 0; i < 10; i++) {
            scores.add(new Score());
        }

        // creates the string representation of user score board
        for (LeaderBoard leaderBoard : user.getUserScores()) {
            if (leaderBoard.getGame().equals(scoresType)) {
                scores = leaderBoard.getScores();

                System.out.println("personal scores: " + scoresType);
            }
        }
        String scoreBoard = "";
        for (int i = 0; i < scores.size(); i++) {
            scoreBoard += scores.get(i).getScore() + " \n";
        }

        // begin settings those strings.
        TextView title = findViewById(R.id.score_title);
        title.setTextSize(30);

        String titleText = "Personal scores for " + scoresType;
        title.setText(titleText);

        TextView scoreList = findViewById(R.id.scores);
        scoreList.setTextSize(20);
        scoreList.setText(scoreBoard);

        TextView timePlayed = findViewById(R.id.time_played);
        timePlayed.setTextSize(20);
        timePlayed.setText(timePlayedText);

        TextView totalPoints = (TextView) findViewById(R.id.total_points);
        totalPoints.setTextSize(20);
        totalPoints.setText(totalPointsText);
    }
}
