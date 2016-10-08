package com.example.finessefitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        System.out.println(this.buttonPressed);
        TextView workoutLevel = new TextView(this);
        workoutLevel.setTextSize(20);
        workoutLevel.setText(this.buttonPressed);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_start_workout);
        layout.addView(workoutLevel);

        // text for details (exercises) of workout
        TextView workoutDetails = new TextView(this);
        workoutDetails.setTextSize(10);
        String[] exercises = Workouts.exercises;
        String workout = "";
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
                for (int i = 0; i < 20; i++) {
                    if (i % 5 == 0) {
                        workout += "5 min jog\n";
                    }
                    workout += "5 " + exercises[r.nextInt(exercises.length)] + "\n";
                }
                workout += "10 min stretch";
                break;
            default:
                break;
        }
        workoutDetails.setText(workout);
        layout.addView(workoutDetails);

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
