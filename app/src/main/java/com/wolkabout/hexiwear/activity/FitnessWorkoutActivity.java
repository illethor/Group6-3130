package com.wolkabout.hexiwear.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wolkabout.hexiwear.activity.DatabaseObjects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wolkabout.hexiwear.R;

public class FitnessWorkoutActivity extends AppCompatActivity implements SensorEventListener {

    //firebase objects
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseUserReference = database.getReference("users");
    private DatabaseReference firebaseCoachReference = database.getReference("users");
    private FirebaseAuth mAuth;
    private DatabaseReference liveHrReference = database.getReference("HR");

    //workout object variables
    int hrUpperBound;
    int hrLowerBound;
    String workoutType;

    //firebase user variables
    String userEmail;
    String coachEmail;

    //step counter variables
    TextView tv_stepNum;
    TextView tv_hrNum;
    SensorManager sensorManager;
    boolean running = false;
    int stepsTaken = -1;
    String stepsString = "";

    //timer variables
    public TextView timeS;
    public TextView timeM;
    public String stringS="";
    public String stringM="00";
    private Button Start;
    private Button Stop;
    public int counterS = -1;
    public int counterM = 0;
    private boolean stopClicked = true;
    private int uploadCounter = 0;
    private int currentHr = 0;
    private String hrString = "";

    /**
     * Main method which sets up timer and step counter and their respective listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_activity_timer);
        final Context context = this;

        setTitle("Fitness Workout Application");

        /**UI Elements**/
        Start = (Button) findViewById(R.id.Start);
        Stop = (Button) findViewById(R.id.Stop);
        Stop.setVisibility(View.INVISIBLE);
        timeS = (TextView) findViewById(R.id.textViewS);
        timeM = (TextView) findViewById(R.id.textViewM);
        tv_hrNum = (TextView) findViewById(R.id.tv_hrNum);

        //Set ranges values
        hrUpperBound = getIntent().getIntExtra("upperBound",  0);
        hrLowerBound = getIntent().getIntExtra("lowerBound", 0);
        workoutType = getIntent().getStringExtra("workoutType");

        // Initialize all firebase auth information
        mAuth = FirebaseAuth.getInstance();
        userEmail = mAuth.getCurrentUser().getEmail();

        // Constantly monitor the user to see if a message has appeared if it has display it and update coach alert field to true
        firebaseUserReference.child(cleanEmail(userEmail)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User objUser = dataSnapshot.getValue(User.class);
                coachEmail = objUser.getCoachEmail();
                // check if the user has a message by checking if message length greater than 0
                if(objUser.getMessage().length() > 0){
                    // Display an alert message
                    AlertDialog.Builder builder = new AlertDialog.Builder(FitnessWorkoutActivity.this);
                    builder.setMessage(objUser.getMessage()).setTitle("Coach Message");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            // Message was read send notification to coach
                            firebaseCoachReference.child(cleanEmail(coachEmail)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User coachUser = dataSnapshot.getValue(User.class);
                                    // when coach is located update message notification field to true and update it
                                    coachUser.setMessageNotification(true);
                                    // save message notification to true
                                    firebaseCoachReference.child(cleanEmail(coachEmail)).setValue(coachUser);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    });
                    builder.show();
                    // set message field blank again
                    objUser.setMessage("");
                    firebaseUserReference.child(cleanEmail(userEmail)).setValue(objUser);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Heartrate and Alert Setup
        liveHrReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Heartrate Alert!");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Heartrate has fallen outside of specified range!").setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                            }
                        });

                // create alert dialog and heartrate variables for testing
                AlertDialog alertDialog = alertDialogBuilder.create();

                String currentHrString = (String) snapshot.getValue();
                String heartrateValue = currentHrString.split(" ")[0];
                currentHr = Integer.parseInt(heartrateValue);
                tv_hrNum.setText(String.valueOf(currentHr));

                if((currentHr > hrUpperBound|| currentHr < hrLowerBound) && stopClicked==false)
                    alertDialog.show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Step counter setup
        tv_stepNum = (TextView) findViewById(R.id.tv_stepNum);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //set stop button status
        Stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //change boolean value
                stopClicked=true;

                //if no steps taken and no step update , set to 0 (adjust from -1 initialization value)
                if (stepsTaken < 0 && uploadCounter < 15) {
                    stepsString = "0 ";
                }

                // heartrate push logic
                int avgHr = 0;

                if (!hrString.equals("")) {

                    String[] allHr = hrString.split(" ");

                    for (int i = 0; i < allHr.length; i++) {
                        avgHr += Integer.parseInt(allHr[i]);
                    }

                    avgHr = avgHr / allHr.length;
                }

                //create workout object
                FitnessWorkout workout = new FitnessWorkout(hrString, stepsString, workoutType, avgHr);

                //push to db
                firebaseUserReference.child(cleanEmail(userEmail) + "/workouts").push().setValue(workout);


                DatabaseReference firebaseHrReferencee = database.getReference("HR");
                firebaseHrReferencee.setValue("0 bpm");
                // CLOSE OUT THIS ACTIVITY
                finish();
            }
        });

        //Start button logic
        Start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                stopClicked=false;
                Start.setVisibility(View.INVISIBLE);
                Stop.setVisibility(View.VISIBLE);

                CountDownTimer startSS = new CountDownTimer(1000000000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        uploadCounter++;

                        //Step counter upload
                        if (uploadCounter % 5 == 0) {
                            //adjust for initialized steps taken value of -1
                            if (stepsTaken < 0) {
                                stepsTaken = 0;
                            }
                            stepsString += stepsTaken + " ";

                            hrString += currentHr + " ";
                        }

                        if(stopClicked){
                            timeS.setText(stringS);
                            timeM.setText(stringM);
                            this.cancel();
                        }
                        else{
                            RangeOfTime(counterS,counterM);
                        }
                        timeS.setText(stringS);
                        timeM.setText(stringM);
                    }
                    @Override
                    public void onFinish(){
                    }
                }.start();
            }
        });
    }

    /**
     * Method which handles the seconds and minutes displayed base on parameter values.
     * @param S The number of seconds.
     * @param M The number of minutes.
     * */
    public void RangeOfTime(int S, int M){
        if (S < 9) {
            S++;
            stringS = "0" + S;
        }
        else if(S < 59&&S >= 9){
            S++;
            stringS = S + "";
        }
        else{
            M++;
            stringM = "0"+M+"";
            S = 0;
            stringS = "0"+S;
        }
        counterM = M;
        counterS = S;
    }

    /**
     * Step counter method
     */
    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Error: Sensor not found.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Paused step counter listener.
     */
    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    /**
     * Updates the step counter upon it being triggered.
     * @param event step counter change event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            if (!stopClicked) {
                stepsTaken++;
                tv_stepNum.setText(String.valueOf(stepsTaken));
            }
        }
    }

    /**
     * Change of accuracy listener, do nothing in this case.
     * @param sensor step counter sensor
     * @param accuracy accuracy value
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing.
    }

    /**
     * Removes the dot from an email for database storage
     * */
    public static String cleanEmail(String email){
        return email.replaceAll("\\.","");
    }
}
