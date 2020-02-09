package uoft.csc207.gameapplication.Utility.GameRequestService.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A class responsible for the leader board.
 */
public class LeaderBoard {
    @JsonProperty("scores")
    private List<Score> scores;
    @JsonProperty("game")
    private String game;

    /**
     * @return the game type of the leader board.
     */
    public String getGame() {
        return game;
    }

    /**
     * Set up the game type for the leader board option.
     *
     * @param game The game type wanted.
     */
    public void setGame(String game) {
        this.game = game;
    }

    /**
     * @return a list of scores in the leader board.
     */
    public List<Score> getScores() {
        return scores;
    }

    /**
     * Set up the scores for the leader board.
     *
     * @param scores The scores to be written into the leader board.
     */
    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
