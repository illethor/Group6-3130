package com.example.david.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        //Link graph object to graph
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        //Declare ArrayLists that store workoutNames for the spinner and workoutObjects for workout data respectively.
        final ArrayList<String> workoutNames = new ArrayList<>();
        final List<Workout> workoutObjects = new ArrayList<>();
        //Declare buttons
        final Button stepsGraph = (Button)findViewById(R.id.stepGraphBtn);
        final Button heartratesGraph = (Button)findViewById(R.id.heartrateGraphBtn);
        //Averages not implemented yet
        final Button averageStepsGraph = (Button)findViewById(R.id.averageSteps);
        final Button averageHeartratesGraph = (Button)findViewById(R.id.averageHeartrates);
        final TextView graphText = (TextView)findViewById(R.id.graphTextView);
        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference();
        //Obtains Workout POJOs from database and stores them in an ArrayList<Workout>
        dbRef.child("workouts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child:children) {
                    Workout i = child.getValue(Workout.class);
                    String fsteps = i.getStepsList();
                    String fheartrate = i.getHeartrateList();
                    String fworkoutType = i.getWorkoutType();
                    int faverageHeartrate = i.getAverageHeartrate();
                    workoutObjects.add(new Workout(fheartrate,fsteps,fworkoutType,faverageHeartrate));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        dbRef.child("workouts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                /**
                 * Looks at the children of "workouts", and gets their name(keys)
                 * Adds them to ArrayList to populate spinner
                 */
                for (DataSnapshot child:children) {
                    String key = child.getKey();
                    workoutNames.add(key);
                }
                //Spinner stuff
                ArrayAdapter<String> adapter = new ArrayAdapter<>(GraphActivity.this, android.R.layout.simple_spinner_item, workoutNames);
                Spinner spinner = (Spinner) findViewById(R.id.graphs_spinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                spinner.setAdapter(adapter);
                //Spinner Listener, reacts to what you select
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Create string[] to hold avg heart rates
                        final int[] averageHeartrate = new int[workoutObjects.size()];
                        //array to hold X axis when plotting average heart rate per workout
                        final int[] workoutNumber = new int[workoutObjects.size()];
                        workoutNumber[0] = 1;
                        for(int i=1;i<workoutObjects.size()-1;i++){
                            workoutNumber[i] = workoutNumber[i-1]+1;
                        }
                        //Fills averageHeartrate array with average heart rate pulled from each workoutObject in list
                        for(int i=0;i<workoutObjects.size();i++){
                            averageHeartrate[i] = workoutObjects.get(i).getAverageHeartrate();
                        }
                        final String[] stepStringArray, heartrateStringArray;
                        final int[] stepIntArray, heartrateIntArray, timeArray;
                        final String workoutType = workoutObjects.get(position).getWorkoutType();
                        final int[] highestStep = new int[workoutObjects.size()];
                        final String[] stepListStringArray = new String[workoutObjects.size()];
                        String[] stepListStringSplitArray = new String[workoutObjects.size()];
                        for(int i=0;i<workoutObjects.size();i++){
                            stepListStringArray[i] = workoutObjects.get(i).getStepsList();
                        }
                        for(int i=0;i<workoutObjects.size();i++){
                            stepListStringSplitArray = stepListStringArray[i].trim().split("\\s+");
                            highestStep[i] =Integer.parseInt(stepListStringSplitArray[stepListStringSplitArray.length-1]);
                        }
                        double averageStepsUndivided=0;
                        for(int i=0;i<highestStep.length;i++){
                            averageStepsUndivided += highestStep[i];
                        }
                        final double averageSteps = averageStepsUndivided/highestStep.length;
                        //Grab string of steps from currently selected workoutObject
                        String steps = workoutObjects.get(position).getStepsList();
                        //Grab string of heart rates from currently selected workoutObject
                        String heartrates = workoutObjects.get(position).getHeartrateList();
                        //Extract each number as a string and store into string arrays
                        stepStringArray = steps.trim().split("\\s+");
                        stepIntArray = new int[stepStringArray.length];
                        //Convert string numbers from string array into ints and store into int array
                        for(int i=0;i<stepStringArray.length;i++){
                            stepIntArray[i]=Integer.parseInt(stepStringArray[i]);
                        }
                        //Extract each number as a string and store into string arrays
                        heartrateStringArray = heartrates.trim().split("\\s+");
                        heartrateIntArray = new int[heartrateStringArray.length];
                        //Convert string numbers from string array into ints and store into int array
                        for(int i=0;i<stepStringArray.length;i++){
                            heartrateIntArray[i]=Integer.parseInt(heartrateStringArray[i]);
                        }
                        //Fills stepIntArray and heartrateIntArray with type int
                        for (int i = 0; i < stepStringArray.length; i++) {
                            stepIntArray[i]=Integer.parseInt(stepStringArray[i]);
                            heartrateIntArray[i]=Integer.parseInt(heartrateStringArray[i]);
                        }
                        //Create array of times. Assume steps/heart rates are polled every 10 seconds
                        //Assumes step array and heart rate array are same length/steps and heart rate polled together.
                        timeArray = new int[stepStringArray.length];
                        //First time should be 0
                        timeArray[0] = 0;
                        //+= 10 every next element of the array
                        for (int i = 1; i < stepIntArray.length-1; i++){
                            timeArray[i] = timeArray[i-1]+10;
                        }
                        graph.removeAllSeries();
                        //Step button listener. When "STEPS" button is pressed, graphs steps over time of currently selected workout.
                        stepsGraph.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                graphText.setText("Activity: "+workoutType);
                                //set graph title
                                graph.setTitle("Steps taken over time");
                                // set manual X bounds
                                graph.getViewport().setXAxisBoundsManual(true);
                                graph.getViewport().setMinX(0);
                                graph.getViewport().setMaxX(stepIntArray.length*10);
                                //set manual Y bounds
                                graph.getViewport().setYAxisBoundsManual(true);
                                graph.getViewport().setMinY(0);
                                graph.getViewport().setMaxY(stepIntArray[stepIntArray.length-1]);
                                graph.getViewport().setScalable(true);
                                graph.removeAllSeries();
                                LineGraphSeries series = new LineGraphSeries<>(generateData(timeArray,stepIntArray));
                                //DataPoint tap listener. Displays via Toast the datapoint of the series that has been tapped
                                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                                    @Override
                                    public void onTap(Series series, DataPointInterface dataPoint) {
                                        Toast.makeText(GraphActivity.this, "Tapped: "+dataPoint, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                graph.addSeries(series);
                            }
                        });
                        //Heartrate button listener. When "HEARTRATE" is pressed, graphs heartrate over time of currently selected workout.
                        heartratesGraph.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                graphText.setText("Activity: "+workoutType);
                                //set graph title
                                graph.setTitle("Heart rate over time");
                                // set manual X bounds because I can't get the right maximum elapsed time from my timeArray, RIP.
                                graph.getViewport().setXAxisBoundsManual(true);
                                graph.getViewport().setMinX(0);
                                graph.getViewport().setMaxX(heartrateIntArray.length*10);
                                //set manual Y bounds
                                graph.getViewport().setYAxisBoundsManual(true);
                                graph.getViewport().setMinY(0);
                                graph.getViewport().setMaxY(200);
                                graph.removeAllSeries();
                                LineGraphSeries series = new LineGraphSeries<>(generateData(timeArray,heartrateIntArray));
                                //DataPoint tap listener. Displays via Toast the datapoint of the series that has been tapped
                                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                                    @Override
                                    public void onTap(Series series, DataPointInterface dataPoint) {
                                        Toast.makeText(GraphActivity.this, "Tapped: "+dataPoint, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                graph.addSeries(series);
                            }
                        });
                        averageHeartratesGraph.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                graphText.setText("");
                                //set graph title
                                graph.setTitle("Average Heart Rate Per Workout");
                                // set manual X bounds
                                graph.getViewport().setXAxisBoundsManual(true);
                                graph.getViewport().setMinX(0);
                                graph.getViewport().setMaxX(workoutNumber.length);
                                //set manual Y bounds
                                graph.getViewport().setYAxisBoundsManual(true);
                                graph.getViewport().setMinY(0);
                                graph.getViewport().setMaxY(averageHeartrate[averageHeartrate.length-1]);
                                graph.removeAllSeries();
                                PointsGraphSeries series = new PointsGraphSeries<>(generateData(workoutNumber,averageHeartrate));
                                //DataPoint tap listener. Displays via Toast the datapoint of the series that has been tapped
                                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                                    @Override
                                    public void onTap(Series series, DataPointInterface dataPoint) {
                                        Toast.makeText(GraphActivity.this, "Tapped: "+dataPoint, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                graph.addSeries(series);
                            }
                        });
                        averageStepsGraph.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                graphText.setText("Your average steps (over all workouts): "+averageSteps);
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            public void generateStepsAverage(){
                for(int i=0;i<workoutObjects.size();i++){
                    String steps = workoutObjects.get(i).getStepsList();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /**
     * Method for return button - brings you back to Main Activity.
     * @param v
     */
    public void graphToMain(View v) {
        startActivity(new Intent(GraphActivity.this, MainActivity.class));
    }

    public DataPoint[] generateData(int[] time, int[] steps){
        DataPoint[] db = new DataPoint[steps.length];
        for(int i=0; i<steps.length;i++){
            DataPoint v = new DataPoint(time[i],steps[i]);
            db[i] = v;
        }
        return db;
    }
}

