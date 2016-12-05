package com.example.finessefitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finessefitness.R;
import com.example.finessefitness.SidebarActivity;
import com.example.finessefitness.StartWorkoutActivity;

public class ActivityIntensity extends SidebarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_intensity);
        super.onCreate(savedInstanceState);
    }

    /* called when the user selects a workout difficulty */
    public void startWorkoutScreen(View view) {
        Intent intent = new Intent(this, StartWorkoutActivity.class);
        String buttonPressed = ((Button)view).getText().toString();
        intent.putExtra("button pressed", buttonPressed);
        startActivity(intent);
    }

    public void WorkoutLevels(View view) {

    }
}
