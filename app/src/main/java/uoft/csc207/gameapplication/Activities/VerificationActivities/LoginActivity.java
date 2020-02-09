package uoft.csc207.gameapplication.Activities.VerificationActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import uoft.csc207.gameapplication.Activities.MainMenuActivities.MainMenuActivity;
import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Utility.GameRequestService.CallBack;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.UserVerificationServices.LoginService;
import uoft.csc207.gameapplication.Utility.RegisterUtility;

/**
 * Make the login activity set up.
 */
public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView registerText;

    private LoginService userService;

    private static final String FILE = "UserData.json";
    private JSONObject jsonObject;

    private EditText usernameInput;
    private EditText passwordInput;

    private boolean loggingIn = false;

    String username;
    String loginPassword;

    /**
     * The login activity enters the Created state, and compare the information.
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // testing now to send post request
        userService = new LoginService();
        userService.setContext(this);

        // original code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.login_button);
        registerText = (TextView) findViewById(R.id.register_text);
        usernameInput = (EditText) findViewById(R.id.login_username_field);
        passwordInput = (EditText) findViewById(R.id.login_password_field);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggingIn) {
                    return;
                }

                username = usernameInput.getText().toString();
                loginPassword = RegisterUtility.hash(passwordInput.getText().toString(), "SHA-256");

                userService.login(username, loginPassword, new CallBack() {



                    @Override
                    public void onFailure() {
                        showToast("Incorrect login credentials");
                        loggingIn = false;
                    }

                    @Override
                    public void onSuccess() {
                        Intent mainMenuActivity = new Intent(LoginActivity.this, MainMenuActivity.class);
                        startActivity(mainMenuActivity);
                        loggingIn = false;
                    }

                    @Override
                    public void onWait() {
                        loggingIn = true;
                        showToast("Logging in please wait");
                    }
                });
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerUser = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(registerUser);
            }
        });
    }


//    /**
//     * load the json from assets or from the android memory
//     */
//    private void load() {
//        try {
//            int i = 0;
//            String jsonString = "";
//            try {
//                FileInputStream fileInputStream = openFileInput(FILE);
//                while ((i = fileInputStream.read()) != -1) {
//                    jsonString += (char) i;
//                }
//                fileInputStream.close();
//            }
//            catch (FileNotFoundException e) {
//                BufferedReader bufferReader = new BufferedReader(
//                        new InputStreamReader(getAssets().open(FILE)));
//                while ((i = bufferReader.read()) != -1) {
//                    jsonString += (char) i;
//                }
//                bufferReader.close();
//            }
//            System.out.println(jsonString);
//            jsonObject = new JSONObject(jsonString);
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Hashes the password and compares to the hash
//     * @param email user email used to login
//     * @param password user password used to login
//     * @return the username if the email and password is valid or else return null
//     */
//    public String verifyLogin(String email, String password) {
//        String passwordHash = RegisterUtility.hash(password, "SHA-256");
//        JSONArray userdata;
//        try {
//            userdata = jsonObject.getJSONArray("users");
//            for (int i = 0; i < userdata.length(); i++) {
//                JSONObject user = userdata.getJSONObject(i);
//                boolean sameUser = user.getString("email").equals(email);
//                boolean samePass = user.getString("password").equals(passwordHash);
//                if (sameUser && samePass) {
//                    showToast("Welcome " + user.getString("username"));
//                    return user.getString("username");
//                }
//            }
//            return null;
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * Displays the text on screen
     * @param text a text to be displayed on screen
     */
    private void showToast(String text) {
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
