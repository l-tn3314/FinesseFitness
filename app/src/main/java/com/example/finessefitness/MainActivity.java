package com.example.finessefitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import android.widget.EditText;

/*
main screen - choosing level of difficulty for workout
 */
public class MainActivity extends AppCompatActivity {

    public static final String PREF_NAME = "MyPrefs";
    public static final String USER = "user";
    public static final String IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences shared = getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        if (shared.getBoolean(IS_LOGGED_IN, false)) {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    /* called when the user selects a workout difficulty */
    public void startWorkoutScreen(View view) {
        Intent intent = new Intent(this, StartWorkoutActivity.class);
        String buttonPressed = ((Button)view).getText().toString();
        intent.putExtra("button pressed", buttonPressed);
        startActivity(intent);
    }


    public void JoinUs(View view) {
        Intent intent = new Intent(this, JoinActivity.class);
        //String buttonPressed = ((Button)view).getText().toString();
        //intent.putExtra("button pressed", buttonPressed);
        startActivity(intent);
    }
}
