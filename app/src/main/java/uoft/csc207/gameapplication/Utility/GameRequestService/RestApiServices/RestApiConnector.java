package uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices;

import android.content.Context;

public abstract class RestApiConnector {
    public static final String URL = "http://192.168.2.17:8080/";

    protected Context context;

    /**
     * Sets context so we can use volley
     * @param context the game activity or the context we are using volley on
     */
    public void setContext(Context context) {
        this.context = context;
    }
}
