package uoft.csc207.gameapplication.Utility.GameRequestService.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class for the scores.
 */
public class Score {
    @JsonProperty("score")
    private String score;
    @JsonProperty("username")
    private String username;

    /**
     * @return the user's name.
     */
    public String getUsername() {
        return username;
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
     * Set up the score.
     *
     * @param score The score to be set.
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * @return the score.
     */
    public String getScore() {
        return score;
    }
}
