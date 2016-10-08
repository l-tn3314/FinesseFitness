package com.example.finessefitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

public class StartWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        Intent intent = getIntent();
        String buttonPressed = intent.getStringExtra("button pressed");
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(buttonPressed);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_start_workout);
        layout.addView(textView);

        getSupportActionBar().setHomeButtonEnabled(true);

    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
