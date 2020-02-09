package uoft.csc207.gameapplication.Activities.MainMenuActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import uoft.csc207.gameapplication.R;
import uoft.csc207.gameapplication.Utility.JSONFileRW;

/**
 * The customization board for choosing different styles of game entities.
 */
public class CustomizeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String FILE = "Customize.json";
    private JSONFileRW fileRW;
    private JSONObject allCustomizations;
    private JSONObject tetrisCustomizations;
    private JSONObject rhythmCustomizations;
    private JSONObject mazeCustomizations;

    /**
     * Let the customization menu get into the Created state.
     *
     * @param savedInstanceState Containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        loadCustomizations();
        initializeViews();
    }
    
    // Loads the customizations into the JSONObject instances.
    private void loadCustomizations() {
        fileRW = new JSONFileRW(FILE, this);
        allCustomizations = fileRW.load();
        if (allCustomizations != null) {
            try {
                tetrisCustomizations = allCustomizations.getJSONObject("tetris");
                rhythmCustomizations = allCustomizations.getJSONObject("rhythm");
                mazeCustomizations = allCustomizations.getJSONObject("maze");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Initializes the spinners and apply button
    private void initializeViews() {
        // Initializes the spinner for the colour of Tetris game
        Spinner tetrisColourSpinner =  findViewById(R.id.tetris_colour_spinner);
        ArrayAdapter<CharSequence> tetrisColourAdapter = ArrayAdapter.createFromResource(this,
                R.array.tetrisColours, android.R.layout.simple_spinner_item);
        tetrisColourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tetrisColourSpinner.setAdapter(tetrisColourAdapter);
        tetrisColourSpinner.setOnItemSelectedListener(this);

        // Initializes the spinners for the shapes of Rhythm game
        int[] shapeSpinnersId = {R.id.shape1_spinner, R.id.shape2_spinner, R.id.shape3_spinner,
                R.id.shape4_spinner};

        for (int i = 0; i < 4; i++) {
            Spinner shapeSpinner =  findViewById(shapeSpinnersId[i]);
            ArrayAdapter<CharSequence> shapeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.shapes, android.R.layout.simple_spinner_item);
            shapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            shapeSpinner.setAdapter(shapeAdapter);
            shapeSpinner.setOnItemSelectedListener(this);
        }

        // Initializes the spinner for the controls of Maze game
        Spinner mazeControlSpinner =  findViewById(R.id.maze_control_spinner);
        ArrayAdapter<CharSequence> mazeControlAdapter = ArrayAdapter.createFromResource(this,
                R.array.mazeControls, android.R.layout.simple_spinner_item);
        mazeControlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mazeControlSpinner.setAdapter(mazeControlAdapter);
        mazeControlSpinner.setOnItemSelectedListener(this);

        // Apply Button
        Button applyButton = (Button) findViewById(R.id.apply_button);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileRW.write(allCustomizations.toString());
                Toast.makeText(CustomizeActivity.this, "Applied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Saves the selection into the corresponding JSONObjects
     * https://developer.android.com/reference/android/widget/AdapterView.OnItemSelectedListener
     * @param adapterView The AdapterView where the selection happens.
     * @param i           The position of the view in the adapter.
     * @param l           The row id of the item that is selected.
     * @param view        The view within the AdapterView that was clicked.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            switch (adapterView.getId()) {
                case R.id.tetris_colour_spinner:
                    tetrisCustomizations.put("colours", adapterView.getSelectedItem());
                    break;
                case R.id.shape1_spinner:
                    rhythmCustomizations.put("shape1", adapterView.getSelectedItem());
                    break;
                case R.id.shape2_spinner:
                    rhythmCustomizations.put("shape2", adapterView.getSelectedItem());
                    break;
                case R.id.shape3_spinner:
                    rhythmCustomizations.put("shape3", adapterView.getSelectedItem());
                    break;
                case R.id.shape4_spinner:
                    rhythmCustomizations.put("shape4", adapterView.getSelectedItem());
                    break;
                case R.id.maze_control_spinner:
                    mazeCustomizations.put("controls", adapterView.getSelectedItem());
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when nothing is selected (the default case).
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}

