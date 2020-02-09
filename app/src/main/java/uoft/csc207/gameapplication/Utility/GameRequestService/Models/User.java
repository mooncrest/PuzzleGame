package uoft.csc207.gameapplication.Utility.GameRequestService.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A class responsible for the game users.
 */
public class User {
    @JsonProperty("username")
    private String username;

    @JsonProperty("userScores")
    private List<LeaderBoard> userScores;

    @JsonProperty("totalScore")
    private String totalPoints;

    @JsonProperty("timePlayed")
    private String timePlayed;

    @JsonProperty("currentStage")
    private String currentStage;

    /**
     * @return the current stage of the game.
     */
    public String getCurrentStage() {
        return currentStage;
    }

    /**
     * Set up the current stage of the game.
     *
     * @param currentStage The current stage to be set.
     */
    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    /**
     * @return the total score.
     */
    public String getTotalPoints() {
        return totalPoints;
    }

    /**
     * Set up the total points.
     *
     * @param totalPoints The total points to be written.
     */
    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    /**
     * @return the playing time.
     */
    public String getTimePlayed() {
        return timePlayed;
    }

    /**
     * Set up the playing time.
     *
     * @param timePlayed The playing time to be written.
     */
    public void setTimePlayed(String timePlayed) {
        this.timePlayed = timePlayed;
    }

    /**
     * Set up the user's name.
     *
     * @param username The user's name to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the user's name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return a list of scores into the leader board.
     */
    public List<LeaderBoard> getUserScores() {
        return userScores;
    }

    /**
     * Set up the user's score into the leader board.
     *
     * @param userScores The user's score to be set up.
     */
    public void setUserScores(List<LeaderBoard> userScores) {
        this.userScores = userScores;
    }
}
