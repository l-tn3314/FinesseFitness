package com.example.finessefitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

/*
workout screen
 */
public class WorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        String buttonPressed = intent.getStringExtra("button pressed");
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(buttonPressed);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_workout);
        layout.addView(textView);

        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
