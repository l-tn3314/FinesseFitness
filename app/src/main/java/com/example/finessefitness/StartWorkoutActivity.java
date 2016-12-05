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
import java.util.List;
import java.util.Random;

import databaseSchema.DBHandler;
import hard_code.Workouts;

/*
Overview of workout before starting it
 */
public class StartWorkoutActivity extends SidebarActivity {
    protected static String workoutName;
    protected static List<String> workoutExercises;
    protected static List<Integer> exerciseSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start_workout);
        super.onCreate(savedInstanceState);

        // layout
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_start_workout);
        TextView workoutLevel = (TextView) findViewById(R.id.workoutLevel);

        // text for level of workout
        Intent intent = getIntent();
        String workoutName = intent.getStringExtra("workout name");
        if (workoutName != null) {
            this.workoutName = workoutName;
        }

        workoutLevel.setText(this.workoutName);
        String user = intent.getStringExtra("user created");

        // text for details (exercises) of workout
        TextView workoutDetails = (TextView)findViewById(R.id.exercise_list);
        DBHandler db = DBHandler.getInstance(this);
        List<String> exercises = db.getExercisesForWorkout(this.workoutName, user);
        // re-initialize static variables
        workoutExercises = exercises;
        exerciseSeconds = new ArrayList<Integer>();

        workoutDetails.setText("");
        Random r = new Random();
        for (String s : exercises) {
            workoutDetails.append("\n" + s);
            exerciseSeconds.add(r.nextInt(10) + 2);
        }

    }

    /* called when the user starts a workout */
    public void workoutScreen(View view) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        String buttonPressed = ((Button)view).getText().toString();
        intent.putExtra("button pressed", buttonPressed);
        startActivity(intent);
    }
}