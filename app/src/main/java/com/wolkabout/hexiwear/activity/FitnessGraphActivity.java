package com.wolkabout.hexiwear.activity;

import java.io.*;
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
import android.os.Environment;

import com.google.firebase.auth.FirebaseAuth;
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
import com.wolkabout.hexiwear.R;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class FitnessGraphActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_activity_graph);
        mAuth = FirebaseAuth.getInstance();
        //Link graph object to graph
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        //Declare ArrayLists that store workoutNames for the spinner and fitnessWorkoutObjects for workout data respectively.
        final ArrayList<String> workoutNames = new ArrayList<>();
        final List<FitnessWorkout> fitnessWorkoutObjects = new ArrayList<>();
        //Declare buttons
        final Button stepsGraph = (Button)findViewById(R.id.stepGraphBtn);
        final Button heartratesGraph = (Button)findViewById(R.id.heartrateGraphBtn);
        final Button export = (Button)findViewById(R.id.exportBtn);
        //Averages not implemented yet
        final Button averageStepsGraph = (Button)findViewById(R.id.averageSteps);
        final Button averageHeartratesGraph = (Button)findViewById(R.id.averageHeartrates);
        final TextView graphText = (TextView)findViewById(R.id.graphTextView);
        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Changed July 23, 9:40 am to access users/workouts/
        DatabaseReference dbRef = database.getReference();
        String userEmail = mAuth.getCurrentUser().getEmail();
        userEmail = cleanEmail(userEmail);
        //Obtains FitnessWorkout POJOs from database and stores them in an ArrayList<FitnessWorkout>
        dbRef.child("users").child(userEmail).child("workouts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child:children) {
                    FitnessWorkout i = child.getValue(FitnessWorkout.class);
                    String fsteps = i.getStepsList();
                    String fheartrate = i.getHeartrateList();
                    String fworkoutType = i.getWorkoutType();
                    int faverageHeartrate = i.getAverageHeartrate();
                    fitnessWorkoutObjects.add(new FitnessWorkout(fheartrate,fsteps,fworkoutType,faverageHeartrate));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        dbRef.child("users").child(userEmail).child("workouts").addValueEventListener(new ValueEventListener() {
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

                //July 23-10:30 am. The way workouts are now attributed and pushed to individual users causes naming convention issues. Using loop to iterate through
                //list of workout names to appropriately change them.
                int workoutNumber = 1;
                for(int i=0;i<workoutNames.size();i++){
                    workoutNames.set(i, "workout "+workoutNumber);
                    workoutNumber++;
                }
                //Spinner stuff
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FitnessGraphActivity.this, android.R.layout.simple_spinner_item, workoutNames);
                Spinner spinner = (Spinner) findViewById(R.id.graphs_spinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                spinner.setAdapter(adapter);
                //Spinner Listener, reacts to what you select
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Create string[] to hold avg heart rates
                        final int[] averageHeartrate = new int[fitnessWorkoutObjects.size()];
                        //array to hold X axis when plotting average heart rate per workout
                        final int[] workoutNumber = new int[fitnessWorkoutObjects.size()];
                        workoutNumber[0] = 1;
                        for(int i = 1; i< fitnessWorkoutObjects.size()-1; i++){
                            workoutNumber[i] = workoutNumber[i-1]+1;
                        }
                        //Fills averageHeartrate array with average heart rate pulled from each workoutObject in list
                        for(int i = 0; i< fitnessWorkoutObjects.size(); i++){
                            averageHeartrate[i] = fitnessWorkoutObjects.get(i).getAverageHeartrate();
                        }
                        final String[] stepStringArray, heartrateStringArray;
                        final int[] stepIntArray, heartrateIntArray, timeArray;
                        final String workoutType = fitnessWorkoutObjects.get(position).getWorkoutType();
                        final String workoutName = workoutNames.get(position);
                        final int[] highestStep = new int[fitnessWorkoutObjects.size()];
                        final String[] stepListStringArray = new String[fitnessWorkoutObjects.size()];
                        String[] stepListStringSplitArray = new String[fitnessWorkoutObjects.size()];
                        for(int i = 0; i< fitnessWorkoutObjects.size(); i++){
                            stepListStringArray[i] = fitnessWorkoutObjects.get(i).getStepsList();
                        }
                        for(int i = 0; i< fitnessWorkoutObjects.size(); i++){
                            stepListStringSplitArray = stepListStringArray[i].trim().split("\\s+");
                            highestStep[i] =Integer.parseInt(stepListStringSplitArray[stepListStringSplitArray.length-1]);
                        }
                        double averageStepsUndivided=0;
                        for(int i=0;i<highestStep.length;i++){
                            averageStepsUndivided += highestStep[i];
                        }
                        final double averageSteps = averageStepsUndivided/highestStep.length;
                        //Grab string of steps from currently selected workoutObject
                        String steps = fitnessWorkoutObjects.get(position).getStepsList();
                        //Grab string of heart rates from currently selected workoutObject
                        String heartrates = fitnessWorkoutObjects.get(position).getHeartrateList();
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
                            timeArray[i] = timeArray[i-1]+5;
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
                                        Toast.makeText(FitnessGraphActivity.this, "Tapped: "+dataPoint, Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(FitnessGraphActivity.this, "Tapped: "+dataPoint, Toast.LENGTH_SHORT).show();
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
                                graph.setTitle("Average Heart Rate Per FitnessWorkout");
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
                                        Toast.makeText(FitnessGraphActivity.this, "Tapped: "+dataPoint, Toast.LENGTH_SHORT).show();
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
                        //brogan and David
                        export.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                FileWriter filewriter = null;

                                try{
                                    //make the directory to put the csv file into
                                    File directory = new File(Environment.getExternalStorageDirectory(), "Group6 Workouts");
                                    if (!directory.exists()) {
                                        directory.mkdirs();
                                    }

                                    File file = new File (directory, workoutName+".csv");
                                    filewriter = new FileWriter(file);

                                    //each entrance in workout will be formatted as
                                    //Time: time, Heart Rate: heartRate, Steps: steps
                                    for(int i=0; i<stepIntArray.length-1; i++){
                                        filewriter.append("Time: ");
                                        filewriter.append(String.valueOf(timeArray[i]));
                                        filewriter.append(",");
                                        filewriter.append("Heart Rate: ");
                                        filewriter.append(String.valueOf(heartrateIntArray[i]));
                                        filewriter.append(",");
                                        filewriter.append("Steps: ");
                                        filewriter.append(String.valueOf(stepIntArray[i]));
                                        filewriter.append("\n");
                                    }
                                    //graphText.setText(workoutName+".csv succesfully created");
                                    graphText.setText("Brogan is cool");
                                    filewriter.flush();
                                    filewriter.close();

                                } catch (Exception e) {
                                    //graphText.setText("Error in CsvFileWriter !!!");
                                    graphText.setText("Brogan is cool");
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            public void generateStepsAverage(){
                for(int i = 0; i< fitnessWorkoutObjects.size(); i++){
                    String steps = fitnessWorkoutObjects.get(i).getStepsList();
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
        startActivity(new Intent(FitnessGraphActivity.this, FitnessMainActivity.class));
    }
    /**
     * Removes the dot from an email for database storage
     * */
    public String cleanEmail(String email){
        return email.replaceAll("\\.","");
    }

    public static DataPoint[] generateData(int[] time, int[] steps){
        DataPoint[] db = new DataPoint[steps.length];
        for(int i=0; i<steps.length;i++){
            DataPoint v = new DataPoint(time[i],steps[i]);
            db[i] = v;
        }
        return db;
    }
}

