package uoft.csc207.gameapplication.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TryGetJSON {
    /**
     * Returns the value of the String with the associated key name.
     * @param jsonObject to get the string from
     * @param name the key name
     * @param defaultValue is returned if the key name is invalid
     * @return the value in the object, if the key exists. defaultValue otherwise
     */
    public static String tryGetString(JSONObject jsonObject, String name, String defaultValue) {
        try {
            return jsonObject.getString(name);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     * Returns the value of the Int with the associated key name.
     * @param jsonObject to get the integer from
     * @param name the key name
     * @param defaultValue is returned if the key name is invalid
     * @return the value in the object, if the key exists. defaultValue otherwise
     */
    public static int tryGetInt(JSONObject jsonObject, String name, int defaultValue) {
        try {
            return jsonObject.getInt(name);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     * Returns the JSONObject at the array index.
     * @param array to get the JSONObject from
     * @param index the index
     * @param defaultValue is returned if the index is invalid
     * @return the object in the array, if the key exists. defaultValue otherwise
     */
    public static JSONObject tryGetJSONObject(JSONArray array, int index, JSONObject defaultValue) {
        try {
            return array.getJSONObject(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }
}
