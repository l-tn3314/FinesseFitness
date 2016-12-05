package com.example.finessefitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChooseWorkoutActivity extends SidebarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_workout);
        super.onCreate(savedInstanceState);
    }

    public void WorkoutLevels(View view) {
        Intent intent = new Intent(this, ActivityIntensity.class);
        //String buttonPressed = ((ImageButton)view).getId().toString();
        //intent.putExtra("button pressed", buttonPressed);
        startActivity(intent);
    }
}
