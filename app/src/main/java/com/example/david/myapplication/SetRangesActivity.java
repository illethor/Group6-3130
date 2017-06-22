package com.example.david.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SetRangesActivity extends AppCompatActivity {

    String infoToTrack, activity;
    int lowerBound, upperBound;

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

        //Attach spinners to created spinner objects
        Spinner spnInfo = (Spinner) findViewById(R.id.spnInfo);
        Spinner spnActivity = (Spinner) findViewById(R.id.spnActivity);

        // Create ArrayAdapters using the string arrays and a default spinner layout
        ArrayAdapter<CharSequence> adapterInfo = ArrayAdapter.createFromResource(this, R.array.infoToTrack_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(this, R.array.activities_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterInfo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        spnInfo.setAdapter(adapterInfo);
        spnActivity.setAdapter(adapterActivity);

        //spnActivity listener
        spnActivity.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Retrieves selected item
                activity = parent.getItemAtPosition(position).toString(); //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        //spnInfo listener
        spnInfo.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Retrieves selected item
                infoToTrack = parent.getItemAtPosition(position).toString(); //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }
    /**
     * Method which actually sets the specified ranges input by the user.
     * */
    public void applyRanges(View v) {

        EditText etLowerBound = (EditText) findViewById(R.id.etLowerBound);
        EditText etUpperBound = (EditText) findViewById(R.id.etUpperBound);

        lowerBound = Integer.parseInt(etLowerBound.getText().toString());
        upperBound = Integer.parseInt(etUpperBound.getText().toString());

        if(upperBound<lowerBound){
            //error message
        }
        else{
            //send activity, infoToTrack, lowerBound, and upperBound for use with alert dialog
        }
    }

}