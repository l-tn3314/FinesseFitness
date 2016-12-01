package com.example.finessefitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spotify.sdk.android.authentication.LoginActivity;

import fitnessModel.User;

import databaseSchema.DBHandler;

public class JoinActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinus);


    }

    /** This activity is performed when the user selects the Join Button*/
    public void AddInformation(View view) {
        // Need to create a User object and pass it in to the database
        User user = new User();
        EditText eFirstName = (EditText)findViewById(R.id.firstname);
        user.firstName = eFirstName.getText().toString();

        EditText eLastName = (EditText)findViewById(R.id.lastname);
        user.lastName = eLastName.getText().toString();

        EditText eUserName = (EditText)findViewById(R.id.username);
        user.userName = eUserName.getText().toString();

        EditText ePassword = (EditText)findViewById(R.id.password);
        user.password = ePassword.getText().toString();

        EditText eEmail = (EditText)findViewById(R.id.email);
        user.email= eEmail.getText().toString();

        EditText ePhone = (EditText)findViewById(R.id.phone_number);
        user.phoneNumber = Integer.parseInt(ePhone.getText().toString());

        EditText eHeight = (EditText)findViewById(R.id.height);
        user.height = Integer.parseInt(eHeight.getText().toString());

        EditText eWeight = (EditText)findViewById(R.id.weight);
        user.weight = Integer.parseInt(eWeight.getText().toString());

        // Gets the instance from the database
        DBHandler db = DBHandler.getInstance(this);
        db.addUser(user);

        // sessions: add to SharedPreferences
        SharedPreferences shared = getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(MainActivity.USER, user.userName);
        editor.putBoolean(MainActivity.IS_LOGGED_IN, true);
        editor.commit();

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
