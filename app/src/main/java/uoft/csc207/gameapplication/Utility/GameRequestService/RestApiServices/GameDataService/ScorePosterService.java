package uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.GameDataService;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import uoft.csc207.gameapplication.Utility.GameRequestService.CallBack;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.Score;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.Token;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.RestApiConnector;

/**
 * Connect to rest API, and post the score out.
 */
public class ScorePosterService extends RestApiConnector {
    public static final String SCOREPOSTER = "api/tokens/score/";

    /**
     * Called when posting the score.
     *
     * @param callback used to return the state of the application.
     * @param token    The token for authentication.
     * @param game     The game type of the score to be posted.
     * @param score    The score earned by the user.
     */
    public void postScore(Token token, Score score, String game, final CallBack callback) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JSONObject jsonObject = new JSONObject();
            ObjectMapper objectMapper = new ObjectMapper();
            jsonObject.put("token", new JSONObject(objectMapper.writeValueAsString(token)));
            jsonObject.put("score", new JSONObject(objectMapper.writeValueAsString(score)));

            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.PUT, URL + SCOREPOSTER + game, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    callback.onSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onFailure();
                }
            });
            callback.onWait();
            requestQueue.add(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
