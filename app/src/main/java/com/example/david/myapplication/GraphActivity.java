package com.example.david.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class GraphActivity extends AppCompatActivity {

    private ArrayList<Double> steps = new ArrayList<>();
    private ArrayList<Double> heartrate = new ArrayList<>();
    private ArrayList<Double> time = new ArrayList<>();
    private Workout myWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        //Link graph object to graph
        final GraphView graph = (GraphView) findViewById(R.id.graph);

        //Since we have no "workout" objects with filled arraylists, manually creating
        steps.add(0.0);steps.add(5.0);steps.add(20.0);steps.add(45.0);steps.add(70.0);
        heartrate.add(90.0);heartrate.add(95.0);heartrate.add(110.0);heartrate.add(115.0);heartrate.add(117.5);
        time.add(0.0);time.add(2.0);time.add(4.0);time.add(6.0);time.add(8.0);
        //Create new Workout object from these arraylists
        //In  the future, must figure out how to fill arraylists with data from database
        myWorkout = new Workout(heartrate,steps,time);

        //Attach spinner to created spinner object
        Spinner spinner = (Spinner) findViewById(R.id.graphs_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.graphs_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Spinner listener
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LineGraphSeries<DataPoint> series;
                //Retrieves selected item
                String selectedItem = parent.getItemAtPosition(position).toString(); //this is your selected item
                //Generate hardcoded graph
                if (position == 0) {
                    graph.removeAllSeries();
                    series = new LineGraphSeries<>(new DataPoint[]{
                            new DataPoint(0, 0),
                            new DataPoint(1, 2),
                            new DataPoint(2, 4),
                            new DataPoint(3, 6),
                            new DataPoint(4, 8)
                    });
                    graph.addSeries(series);
                    //Generate hardcoded graph
                } else if (position == 1) {
                    graph.removeAllSeries();
                    series = new LineGraphSeries<>(generateDataSteps(myWorkout));
                    graph.addSeries(series);
                    //Generate data from textfile and graph
                    //Currently not displaying graph
                } else if (position == 2) {
                    graph.removeAllSeries();
                    series = new LineGraphSeries<>(generateDataHeartRate(myWorkout));
                    graph.addSeries(series);
                }
                else return;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }
    /**
     * Initiates a graph object given a view.
     * @param v The view for the graph.
     * */
    public void graphToMain(View v) {
        startActivity(new Intent(GraphActivity.this, MainActivity.class));
    }

    /**
     * Generates a set of Data Points given a text file with coordinates.
     * Refactored 2017-06-27
     * */
    public DataPoint[] generateDataSteps(Workout wrk){
        //Array of datapoints to be used by generateData()
        //Is the size of the "time" ArrayList from myWorkout
        DataPoint[] db = new DataPoint[wrk.getTimeList().size()];
        //Scan through the elements of each ArrayList from myWorkout object to create datapoints
        for(int i=0; i<time.size();i++){
            DataPoint v = new DataPoint(wrk.getTimeList().get(i), wrk.getStepsList().get(i));
            //Add current datapoint to array;
            db[i] = v;
        }
        //Returns a filled datapoint array.
        return db;
    }

    /**
     * Generates a set of Data Points given heart rates and times.
     * */
    public DataPoint[] generateDataHeartRate(Workout wrk){
        //Array of datapoints to be used by generateData()
        //Is the size of the "time" ArrayList from myWorkout
        DataPoint[] db = new DataPoint[wrk.getTimeList().size()];
        //Scan through the elements of each ArrayList from myWorkout object to create datapoints
        for(int i=0; i<time.size();i++){
            DataPoint v = new DataPoint(wrk.getTimeList().get(i), wrk.getHeartrateList().get(i));
            //Add current datapoint to array;
            db[i] = v;
        }
        //Returns a filled datapoint array.
        return db;
    }


}

