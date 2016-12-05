package com.example.finessefitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import databaseSchema.DBHandler;

public class AddWorkoutActivity extends SidebarActivity implements AdapterView.OnItemSelectedListener {
    private String[] default_exercises;
    private List<String> exercises;

    private SharedPreferences shared;
    private String username; // current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_workout);
        super.onCreate(savedInstanceState);

        shared = getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        username = shared.getString(MainActivity.USER, "");

        exercises = new LinkedList<String>();
        default_exercises = getResources().getStringArray(R.array.default_exercise_array);
        Arrays.sort(default_exercises);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                R.layout.drawer_list_item, default_exercises);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(0);

        // set height for scrollable (wrapped in linear layout)
        int height = this.getResources().getDisplayMetrics().heightPixels;
        LinearLayout wrap = (LinearLayout) findViewById(R.id.scroll_wrap);
        ViewGroup.LayoutParams params = wrap.getLayoutParams();
        params.height = height / 2;
        wrap.setLayoutParams(params);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String selection = (String) parent.getItemAtPosition(pos);
        TextView spinnerText = (TextView) findViewById(R.id.text_spinner);
        spinnerText.setText(selection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // scroll to bottom
    private final void focusOnView(){
        final ScrollView scroll = (ScrollView) findViewById(R.id.scroll);
        final TextView text = (TextView) findViewById(R.id.exercise_list);
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.scrollTo(0, text.getBottom());
            }
        });
    }

    /*
    adds the selected exercise to the list
     */
    public void addExercise(View view) {
        TextView spinnerText = (TextView) findViewById(R.id.text_spinner);
        TextView exerciseList = (TextView) findViewById(R.id.exercise_list);
        exerciseList.append("\n" + spinnerText.getText().toString());
        focusOnView();

        // add to list of exercises
        exercises.add(spinnerText.getText().toString());
    }

    /*
    creates a new workout with the listed exercises
     */
    public void createWorkout(View view) {
        // if no exercises are present, do nothing
        if (exercises.size() == 0) {
            return;
        }
        Random r = new Random();
        EditText workout_name = (EditText) findViewById(R.id.custom_workout_name);
        DBHandler handler = DBHandler.getInstance(this);
        int workout_id = handler.insertNewWorkout(workout_name.getText().toString(), r.nextInt(250), username);
        int exercise_id;
        String exercise;
        for (String s : exercises) {
            exercise = s;
            try {
                exercise_id = Integer.parseInt(handler.exerciseGetValOf(exercise, DBHandler.ExerciseKey.EXER_ID));
                handler.insertToContainsTable(workout_id, exercise_id);
            } catch (Exception e) {
                continue;
            }

        }
        Intent intent = new Intent(this, CustomWorkoutActivity.class);
        startActivity(intent);
    }
}
