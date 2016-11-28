package com.example.finessefitness;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
