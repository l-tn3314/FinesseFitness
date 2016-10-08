package com.example.finessefitness;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import hard_code.Workouts;

/*
Overview of workout before starting it
 */
public class StartWorkoutActivity extends AppCompatActivity {
    static String buttonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        // text for level of workout
        Intent intent = getIntent();
        String buttonPressed = intent.getStringExtra("button pressed");
        if (buttonPressed != null) {
            this.buttonPressed = buttonPressed;
        }
        TextView workoutLevel = new TextView(this);
        workoutLevel.setTextSize(25);
        workoutLevel.setText(this.buttonPressed);
        workoutLevel.setId(R.id.workoutLevelId);
        System.out.println(workoutLevel.getId());

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_start_workout);
        layout.addView(workoutLevel);

        // text for details (exercises) of workout
        TextView workoutDetails = new TextView(this);
        workoutDetails.setTextSize(15);
        String[] exercises = Workouts.exercises;
        String workout = "";
        String workoutCol2 = "";
        Random r = new Random();
        switch(this.buttonPressed) {
            case "Beginner":
                for (int i = 0; i < 10; i++) {
                    if (i % 5 == 0) {
                        workout += "5 min jog\n";
                    }
                    workout += "5 " + exercises[r.nextInt(exercises.length)] + "\n";
                }
                workout += "10 min stretch";
                break;
            case "Intermediate":
                for (int i = 0; i < 15; i++) {
                    if (i % 5 == 0) {
                        workout += "5 min jog\n";
                    }
                    workout += "5 " + exercises[r.nextInt(exercises.length)] + "\n";
                }
                workout += "10 min stretch";
                break;
            case "Advanced":
                boolean inWorkoutCol2 = false;
                for (int i = 0; i < 20; i++) {
                    if (i % 5 == 0) {
                        if (inWorkoutCol2) {
                            workoutCol2 += "\t\t5 min jog\n";
                        } else {
                            workout += "5 min jog\n";
                        }
                        inWorkoutCol2 = !inWorkoutCol2;
                    }
                    if (inWorkoutCol2) {
                        workoutCol2 += "\t\t5 " + exercises[r.nextInt(exercises.length)] + "\n";
                    } else {
                        workout += "5 " + exercises[r.nextInt(exercises.length)] + "\n";
                    }
                    inWorkoutCol2 = !inWorkoutCol2;
                }
                workout += "10 min stretch";
                break;
            default:
                break;
        }
        workoutDetails.setText(workout);
        workoutDetails.setId(R.id.workoutLevelId + 1);
        System.out.println(workoutDetails.getId());
        RelativeLayout.LayoutParams params;
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, workoutLevel.getId());
        layout.addView(workoutDetails, params);

        TextView workoutDetails2 = new TextView(this);
        workoutDetails2.setTextSize(15);
        workoutDetails2.setText(workoutCol2);
        System.out.println(workoutCol2);
        RelativeLayout.LayoutParams params2;
        params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.BELOW, workoutLevel.getId());
        params2.addRule(RelativeLayout.RIGHT_OF, workoutDetails.getId());
        layout.addView(workoutDetails2, params2);

        // back arrow
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /* called when the user starts a workout */
    public void workoutScreen(View view) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        String buttonPressed = ((Button)view).getText().toString();
        intent.putExtra("button pressed", buttonPressed);
        startActivity(intent);
    }
}
