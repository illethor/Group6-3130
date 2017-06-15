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

    String infoToTrack;
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
                infoToTrack = parent.getItemAtPosition(position).toString(); //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }
    public void applyRanges(View v) {

        EditText etLowerBound = (EditText) findViewById(R.id.etLowerBound);
        EditText etUpperBound = (EditText) findViewById(R.id.etUpperBound);

        lowerBound = Integer.parseInt(etLowerBound.getText().toString());
        upperBound = Integer.parseInt(etUpperBound.getText().toString());

        if(upperBound<lowerBound){
            //error message
        }
        else{
            //send infoToTrack, lowerBound, and upperBound for use with alert dialog
        }
    }

}