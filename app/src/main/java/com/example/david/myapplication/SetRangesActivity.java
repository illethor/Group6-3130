package com.example.david.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class SetRangesActivity extends AppCompatActivity {

    String workoutType;
    int lowerBound, upperBound;

    /**
     * Main method which listens for user inputting values.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ranges);

        Button btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                applyRanges(v);
            }
        });

        //Attach spinner to created spinner object
        Spinner spnInfo = (Spinner) findViewById(R.id.spnInfo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterInfo = ArrayAdapter.createFromResource(this, R.array.infoToTrack_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterInfo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnInfo.setAdapter(adapterInfo);

        //Spinner listener
        spnInfo.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Retrieves selected item
                workoutType = parent.getItemAtPosition(position).toString(); //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }
    /**
     * Method which actually sets the specified ranges input by the user.
     * @param v current view
     * */
    public void applyRanges(View v) {

        EditText etLowerBound = (EditText) findViewById(R.id.etLowerBound);
        EditText etUpperBound = (EditText) findViewById(R.id.etUpperBound);

        if (tryParsing(etLowerBound.getText().toString()) && tryParsing(etUpperBound.getText().toString())) {
            lowerBound = Integer.parseInt(etLowerBound.getText().toString());
            upperBound = Integer.parseInt(etUpperBound.getText().toString());
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Invalid range for heart rate! Please input valid heart rate bounds.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if (upperBound < lowerBound) {
            Context context = getApplicationContext();
            CharSequence text = "Your upper bound is less than your lower bound!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        } else {
            //send workoutType, lowerBound, and upperBound for use with alert dialog
        }

        Intent intent = new Intent(SetRangesActivity.this, WorkoutActivity.class);

        //pass along required data to the next activity
        intent.putExtra("upperBound", upperBound);
        intent.putExtra("lowerBound", lowerBound);
        intent.putExtra("workoutType", workoutType);

        startActivity(intent);
        finish();
    }

    /**
     * Checks if parsing on String is valid, catches error if not.
     * @param value String representing int
     * @return boolean value, true if parse is valid, false if not.
     **/
    public static boolean tryParsing(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}