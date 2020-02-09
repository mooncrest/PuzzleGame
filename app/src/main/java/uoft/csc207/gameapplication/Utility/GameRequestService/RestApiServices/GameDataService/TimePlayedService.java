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
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.Token;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.RestApiConnector;

/**
 * Connect to the rest API, and display the time played.
 */
public class TimePlayedService extends RestApiConnector {
    public static final String TIMEPLAYED = "api/tokens/timeplayed/";

    /**
     * Update and display the playing time.
     *
     * @param callback   used to return the state of the application.
     * @param token      The token used for authentication.
     * @param timePlayed The playing time to post.
     */
    public void updateTimePlayed(Token token, Long timePlayed, final CallBack callback) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject jsonTokenObject = new JSONObject(objectMapper.writeValueAsString(token));
            String timePlayedSeconds = String.valueOf((int) (timePlayed / 1000));
            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.PUT, URL + TIMEPLAYED + timePlayedSeconds, jsonTokenObject, new Response.Listener<JSONObject>() {
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
