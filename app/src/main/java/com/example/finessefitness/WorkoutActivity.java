package com.example.finessefitness;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.TimerTask;

/*
workout screen
 */
public class WorkoutActivity extends AppCompatActivity {
    private TextView seconds;
    private TextView activity;
    static int curInd;

     private class ActivityCountDownTimer extends CountDownTimer {
         ActivityCountDownTimer(long millisInFuture, long countDownInterval){
             super(millisInFuture, countDownInterval);
         }

        @Override
        public void onTick(long millisUntilFinished) {
            seconds.setText(Long.toString(millisUntilFinished / 1000));
            if (seconds.getText().equals("1")) {
                ((TextView) findViewById(R.id.timeRemaining)).setText("second remaining");
            }
        }
         @Override
        public void onFinish() {
             curInd++;
            startNextTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        System.out.println(StartWorkoutActivity.workoutExercises);
        System.out.println(StartWorkoutActivity.exerciseSeconds);

        seconds = (TextView) findViewById(R.id.seconds);
        activity = (TextView) findViewById(R.id.activity);

        curInd = 0;

        activity.setText(StartWorkoutActivity.workoutExercises.get(0));
        new ActivityCountDownTimer(StartWorkoutActivity.exerciseSeconds.get(0) * 1000, 1000).start();

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_workout);

        // back button
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void startNextTimer() {
        if (curInd < StartWorkoutActivity.workoutExercises.size()) {
            activity.setText(StartWorkoutActivity.workoutExercises.get(curInd));
            ((TextView) findViewById(R.id.timeRemaining)).setText("seconds remaining");
            new ActivityCountDownTimer(StartWorkoutActivity.exerciseSeconds.get(curInd) * 1000, 1000).start();
        }
    }

}

