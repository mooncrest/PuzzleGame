package uoft.csc207.gameapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Activities.VerificationActivities.LoginActivity;

/**
 * Set up the game and start the game life cycle.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The game activity enters the Created state.
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // temporary sleep method in case we need to do some computations before game start
        Thread thread = new Thread() {
            @Override
            public void run() {
            try {
                sleep(500);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
            }
        };
        thread.start();
    }

    /**
     * Called when the user leaves the activity.
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}