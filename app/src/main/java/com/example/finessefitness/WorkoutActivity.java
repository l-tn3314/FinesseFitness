package com.example.finessefitness;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
    private static CountDownTimer timer;
    private static boolean quit = false;
    private Bundle bundle;

    // customized countdown timer
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
             if (quit) {
                 return;
             }
             curInd++;
            startNextTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        this.bundle = savedInstanceState;

        seconds = (TextView) findViewById(R.id.seconds);
        activity = (TextView) findViewById(R.id.activity);

        // reset
        curInd = 0;
        quit = false;

        activity.setText(StartWorkoutActivity.workoutExercises.get(0));
        new ActivityCountDownTimer(StartWorkoutActivity.exerciseSeconds.get(0) * 1000, 1000).start();

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_workout);

        // disable back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


    }

    private void startNextTimer() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (curInd < StartWorkoutActivity.workoutExercises.size()) {
            System.out.println("next");
            activity.setText(StartWorkoutActivity.workoutExercises.get(curInd));
            ((TextView) findViewById(R.id.timeRemaining)).setText("seconds remaining");
            timer = new ActivityCountDownTimer(StartWorkoutActivity.exerciseSeconds.get(curInd) * 1000, 1000);
            timer.start();
            v.vibrate(2000);
        }
    }

    static void quit() {
        quit = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //onPause();
            quit();
            //QuitDialog qd = new QuitDialog();
            //qd.onCreateDialog(this.bundle);
        }
        return super.onKeyDown(keyCode, event);
    }
}

