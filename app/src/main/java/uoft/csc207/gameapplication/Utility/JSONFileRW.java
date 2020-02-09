package uoft.csc207.gameapplication.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reading and writing on the Json data base.
 */
public class JSONFileRW {
    private String file_name;
    private Context context;

    public JSONFileRW(String file_name, Context context) {
        this.file_name = file_name;
        this.context = context;
    }

    /**
     * Loading(read) the game information from the Json object.
     */
    public JSONObject load() {

        String jsonString = "";

        try {
            int i = 0;

            try {
                FileInputStream fileInputStream = context.openFileInput(file_name);
                while ((i = fileInputStream.read()) != -1) {
                    jsonString += (char) i;
                }
            } catch (FileNotFoundException e) {
                BufferedReader bufferReader = new BufferedReader(
                        new InputStreamReader(context.getAssets().open(file_name)));
                while ((i = bufferReader.read()) != -1) {
                    jsonString += (char) i;
                }
            }
            return new JSONObject(jsonString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Writing on the Json object.
     */
    public void write(String jsonString) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(file_name, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
