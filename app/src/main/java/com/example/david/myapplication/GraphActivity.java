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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        //Link graph object to graph
        final GraphView graph = (GraphView) findViewById(R.id.graph);

        //Set graph domain and range
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(10);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

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
                    series = new LineGraphSeries<>(new DataPoint[]{
                            new DataPoint(0, 2),
                            new DataPoint(1, 2),
                            new DataPoint(2, 2),
                            new DataPoint(3, 2),
                            new DataPoint(4, 2)
                    });
                    graph.addSeries(series);
                    //Generate data from textfile and graph
                    //Currently not displaying graph
                } else if (position == 2) {
                    graph.removeAllSeries();
                    series = new LineGraphSeries<>(generateData());
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
     * Method calculates specified file's line count (aka coord pairs count.
     * @return An integer representing the coord amount
     * */
    public int coordAmount(){
        BufferedReader in;
        int coordCount = 0;
        String fileName = "C:\\Users\\David\\Documents\\AndroidStudioProjects\\group6Project\\Workout1.txt";
        try{
            in = new BufferedReader(new FileReader(fileName));
            while(in.readLine()!= null){
              coordCount++;
            }
        }
        catch (IOException e) {
            System.out.println("There was a problem: " + e);
        }
        return coordCount;
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
     * */
    public DataPoint[] generateData(){
        BufferedReader in;
        int[] xArray = new int[coordAmount()];
        int[] yArray = new int[coordAmount()];
        //Set datapoint array size to line count of textfile, thus the number of pairs/coords
        DataPoint[] values = new DataPoint[coordAmount()];
        String fileName = "C:\\Users\\David\\Documents\\AndroidStudioProjects\\group6Project\\Workout1.txt";
        //Read and split string, create and add data point from result
        try {
            in = new BufferedReader(new FileReader(fileName));
            String str;
            String[] ar;
            int i = 0;
            //For later: build 2 arrays, fill each with x and y respectively, for loop to read through and create data
            while((str = in.readLine()) != null){
                //Splits current string at the comma into two parts
                ar = str.split(",");
                //Parse each part of the string as an int and store into array
                //int x and int y now hold the appropriate x and y graph values
                int x = parseInt(ar[0]);
                int y = parseInt(ar[1]);
                //Create datapoint from x and y values
                //Add the datapoint to the current values[] position
                //values[i] = new DataPoint(x,y);
                xArray[i] = x;
                yArray[i] = y;
                i++;
            }
        }
        catch (IOException e) {
            System.out.println("There was a problem: " + e);
        }
        //xArrays and yArrays are now filled
        //Loops through each of their elements and build data point
        for(int c=0;c<coordAmount();c++){
            DataPoint v = new DataPoint(xArray[c],yArray[c]);
            values[c] = v;
        }
        return values;
    }
}

