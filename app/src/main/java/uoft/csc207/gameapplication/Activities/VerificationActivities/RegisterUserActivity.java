package uoft.csc207.gameapplication.Activities.VerificationActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Utility.GameRequestService.CallBack;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.UserVerificationServices.RegisterService;
import uoft.csc207.gameapplication.Utility.RegisterUtility;

/**
 * Make the registration activity set up.
 */
public class RegisterUserActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText emailInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordConfirmationInput;

    private RegisterService registerService;

    String registerEmail;
    String registerUsername;
    String registerPassword;
    String registerPasswordConfirmation;

    /**
     * The registration activity enters the Created state.
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        registerButton = findViewById(R.id.register_button);
        emailInput = findViewById(R.id.register_email);
        usernameInput = findViewById(R.id.register_username);
        passwordInput = findViewById(R.id.register_password);
        passwordConfirmationInput = findViewById(R.id.register_password_confirmation);

        registerService = new RegisterService();
        registerService.setContext(this);



        // The on register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEmail = emailInput.getText().toString();
                registerUsername = usernameInput.getText().toString();
                registerPassword = passwordInput.getText().toString();
                registerPasswordConfirmation = passwordConfirmationInput.getText().toString();
                if (!registerPassword.equals(registerPasswordConfirmation)) {
                    showToast("The passwords do not match");
                }
                else if (!RegisterUtility.validEmail(registerEmail)) {
                    showToast("The email you entered does not seem to be valid");
                }
                else if (!RegisterUtility.strongPassword(registerPassword)) {
                    showToast("Your password should be at least 8 character long " +
                            "contains at least 1 \"@#$%-_=+!^&*\\\", 1 lower and upper case and" +
                            " 1 number and no spaces are allowed. ");
                }
                else if (registerUsername.length() > 15) {
                    showToast("Your username is too long");
                }
                else {
                    JSONObject jsonObject = register();
                    registerService.register(jsonObject, new CallBack() {
                        @Override
                        public void onFailure() {
                            showToast("username is taken");
                        }

                        @Override
                        public void onSuccess() {
                            showToast("registered");
                            finish();
                        }

                        @Override
                        public void onWait() {
                            showToast("sending information");
                        }
                    });
                }
            }
        });
    }
    /**
     * Store the new user's information.
     * @return The Json object to store the new user's information.
     */
    private JSONObject register(){
        try {
            JSONObject newUser = new JSONObject();
            newUser.put("username", registerUsername);
            newUser.put("password", RegisterUtility.hash(registerPassword, "SHA-256"));
            newUser.put("email", registerEmail);
            return newUser;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Displays the text on screen
     * @param text a text to be displayed on screen
     */
    private void showToast(String text) {
        Toast.makeText(RegisterUserActivity.this, text, Toast.LENGTH_LONG).show();
    }

}
