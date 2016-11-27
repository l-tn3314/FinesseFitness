package com.example.finessefitness;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import hard_code.Workouts;

/*
Overview of workout before starting it
 */
public class StartWorkoutActivity extends SidebarActivity {
    protected static String buttonPressed;
    protected static ArrayList<String> workoutExercises;
    protected static ArrayList<Integer> exerciseSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start_workout);
        super.onCreate(savedInstanceState);

        // layout
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_start_workout);
        GridLayout grid = (GridLayout) findViewById(R.id.workoutTable);
        TextView workoutLevel = (TextView) findViewById(R.id.workoutLevel);

        grid.setColumnCount(2);

        // text for level of workout
        Intent intent = getIntent();
        String buttonPressed = intent.getStringExtra("button pressed");
        if (buttonPressed != null) {
            this.buttonPressed = buttonPressed;
        }
        workoutLevel.setText(this.buttonPressed);


        // text for details (exercises) of workout
        TextView workoutDetails = new TextView(this);
        String[] exercises = Workouts.exercises; // array of all possible exercises
        String workoutCol1 = ""; // first column of exercises
        String workoutCol2 = ""; // second column of exercises
        Random r = new Random();
        boolean inWorkoutCol2 = false; // goes in col 2?
        int ind;
        int timeExercise;
        // re-initialize static variables
        workoutExercises = new ArrayList<String>();
        exerciseSeconds = new ArrayList<Integer>();

        // three cases: beginner, intermediate, advanced
        switch(this.buttonPressed) {
            // beginner is 1 column
            case "Beginner":
                for (int i = 0; i < 10; i++) {
                    if (i % 5 == 0) {
                        workoutCol1 += "5 min jog\n";
                        workoutExercises.add("jog");
                        exerciseSeconds.add(3);
                    }
                    ind = r.nextInt(exercises.length);
                    timeExercise = r.nextInt(10) + 21;
                    String str = exercises[ind];
                    int nlLocation = str.indexOf("\n\t");
                    if (nlLocation > 0) {
                        str = str.substring(0, nlLocation) + " " + str.substring(nlLocation + 2);
                    }
                    workoutCol1 += Integer.toString(timeExercise) + " sec " + str + "\n";
                    workoutExercises.add(str);
                    exerciseSeconds.add(timeExercise);
                }
                workoutCol1 += "10 min stretch";
                break;
            // intermediate is 2 columns
            case "Intermediate":
                for (int i = 0; i < 15; i++) {
                    if (i % 5 == 0) {
                        if (inWorkoutCol2) {
                            workoutCol2 += "\t\t5 min jog\n";
                        } else {
                            workoutCol1 += "5 min jog\n";
                        }
                        workoutExercises.add("jog");
                        exerciseSeconds.add(300);
                        inWorkoutCol2 = !inWorkoutCol2;
                    }
                    ind = r.nextInt(exercises.length);
                    timeExercise = r.nextInt(10) + 21;
                    String str = exercises[ind];
                    int nlLocation = str.indexOf("\n");

                    if (inWorkoutCol2) {
                        if (nlLocation > 0) {
                            str = str.substring(0, 1 + nlLocation) + "\t\t" + str.substring(1 + nlLocation);
                        }
                        workoutCol2 += "\t\t" + Integer.toString(timeExercise) + " sec " + str + "\n";
                    } else {
                        workoutCol1 += Integer.toString(timeExercise) + " sec " + str + "\n";
                    }
                    workoutExercises.add(str);
                    exerciseSeconds.add(timeExercise);
                    inWorkoutCol2 = !inWorkoutCol2;
                }
                workoutCol1 += "10 min stretch";
                break;
            // advanced is 2 columns
            case "Advanced":
                for (int i = 0; i < 20; i++) {
                    if (i % 5 == 0) {
                        if (inWorkoutCol2) {
                            workoutCol2 += "\t\t5 min jog\n";
                        } else {
                            workoutCol1 += "5 min jog\n";
                        }
                        workoutExercises.add("jog");
                        exerciseSeconds.add(300);
                        inWorkoutCol2 = !inWorkoutCol2;
                    }
                    ind = r.nextInt(exercises.length);
                    timeExercise = r.nextInt(10) + 21;
                    String str = exercises[ind];
                    int nlLocation = str.indexOf("\n");

                    if (inWorkoutCol2) {
                        if (nlLocation > 0) {
                            str = str.substring(0, 1 + nlLocation) + "\t\t" + str.substring(1 + nlLocation);
                        }
                        workoutCol2 += "\t\t" + Integer.toString(timeExercise) + " sec " + str + "\n";
                    } else {
                        workoutCol1 += Integer.toString(timeExercise) + " sec " + str + "\n";
                    }
                    workoutExercises.add(str);
                    exerciseSeconds.add(timeExercise);
                    inWorkoutCol2 = !inWorkoutCol2;
                }
                workoutCol1 += "10 min stretch";
                break;
            default:
                break;
        }
        workoutDetails.setTextSize(17);
        workoutDetails.setTextColor(Color.parseColor("#FFFFFF"));
        workoutDetails.setText(workoutCol1);
        //layout.addView(workoutDetails, params);
        grid.addView(workoutDetails);

        TextView workoutDetails2 = new TextView(this);
        workoutDetails2.setTextSize(17);
        workoutDetails2.setText(workoutCol2);
        workoutDetails2.setTextColor(Color.parseColor("#FFFFFF"));
        // place col 2 view below the workout level and to the right of col 1
        RelativeLayout.LayoutParams params2;
        params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.BELOW, workoutLevel.getId());
        params2.addRule(RelativeLayout.RIGHT_OF, workoutDetails.getId());
        //layout.addView(workoutDetails2, params2);
        grid.addView(workoutDetails2);

        // back arrow
       // getSupportActionBar().setHomeButtonEnabled(true);
    }

    /* called when the user starts a workout */
    public void workoutScreen(View view) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        String buttonPressed = ((Button)view).getText().toString();
        intent.putExtra("button pressed", buttonPressed);
        startActivity(intent);
    }
}