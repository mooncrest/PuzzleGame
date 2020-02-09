package uoft.csc207.gameapplication.Utility.GameRequestService.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class for the request authentication tokens.
 */
public class Token {
    @JsonProperty("token")
    private String token;

    @JsonProperty("username")
    private String username;

    /**
     * @return the user's name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set up user's name.
     *
     * @param username The user's name to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the token from input.
     */
    public String getToken() {
        return token;
    }

    /**
     * Set up the token.
     *
     * @param token The token to be set.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
