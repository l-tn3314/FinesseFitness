package com.example.finessefitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
main screen - choosing level of difficulty for workout
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // setting button font
        Button beginnerButton =(Button) findViewById(R.id.beginnerButton);
        Typeface type = Typeface.createFromAsset(getAssets(), "arial.ttf");
        beginnerButton.setTypeface(type);

        Button intermediateButton =(Button) findViewById(R.id.intermediateButton);
        intermediateButton.setTypeface(type);

        Button advancedButton =(Button) findViewById(R.id.advancedButton);
        advancedButton.setTypeface(type);*/
    }

    /* called when the user selects a workout difficulty */
    public void startWorkoutScreen(View view) {
        Intent intent = new Intent(this, StartWorkoutActivity.class);
        String buttonPressed = ((Button)view).getText().toString();
        intent.putExtra("button pressed", buttonPressed);
        startActivity(intent);
        //Intent intent = new Intent(this, FitnessModel.class);
        //startActivity(intent);
    }
}
