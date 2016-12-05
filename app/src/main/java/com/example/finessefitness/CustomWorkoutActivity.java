package com.example.finessefitness;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import databaseSchema.DBHandler;

public class CustomWorkoutActivity extends SidebarActivity  implements AdapterView.OnItemSelectedListener {

    private SharedPreferences shared;
    private String username; // current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_custom_workout);
        super.onCreate(savedInstanceState);

        shared = getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        username = shared.getString(MainActivity.USER, "");

        updateSpinner();

        // set height for scrollable (wrapped in linear layout)
        int height = this.getResources().getDisplayMetrics().heightPixels;
        LinearLayout wrap = (LinearLayout) findViewById(R.id.scroll_wrap);
        ViewGroup.LayoutParams params = wrap.getLayoutParams();
        params.height = height / 3;
        wrap.setLayoutParams(params);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String selection = (String) parent.getItemAtPosition(pos);
        TextView spinnerText = (TextView) findViewById(R.id.text_custom_workout);
        spinnerText.setText(selection);

        DBHandler handler = DBHandler.getInstance(this);
        TextView exerciseList = (TextView) findViewById(R.id.custom_exercise_list);
        exerciseList.setText("");
        List<String> exercises = handler.getExercisesForWorkout(selection, username);
        for (String s : exercises) {
            exerciseList.append("\n" + s);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // updates the spinner
    private void updateSpinner() {
        DBHandler handler = DBHandler.getInstance(this);
        List<String> lis = handler.workoutGetValOf(username, DBHandler.WorkoutKey.WORKOUT_NAME);
        String[] customWorkoutsArr = new String[lis.size()];
        int counter = 0;
        for (String s : lis) {
            customWorkoutsArr[counter] = s;
            counter++;
        }

        Spinner spinner = (Spinner) findViewById(R.id.custom_workout_spinner);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                R.layout.drawer_list_item, customWorkoutsArr);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(0);
    }

    /*
    Deletes the currently selected workout
     */
    public void deleteWorkout(View view) {
        TextView spinnerText = (TextView) findViewById(R.id.text_custom_workout);
        String selected = spinnerText.getText().toString();
        DBHandler handler = DBHandler.getInstance(this);
        handler.deleteWorkout(selected, username);

        updateSpinner();
    }
}
