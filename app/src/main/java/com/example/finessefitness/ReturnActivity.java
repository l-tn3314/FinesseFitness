package com.example.finessefitness;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import databaseSchema.DBHandler;

public class ReturnActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returning);
    }

    /** This activity is performed when the user presses the login button */
    public void Login(View view) {
        // Need to check whether the username is in the database and
        // if the password matches
        EditText eUserName = (EditText)findViewById(R.id.username);
        String user = eUserName.getText().toString();

        EditText ePassword = (EditText)findViewById(R.id.password);
        String password = ePassword.getText().toString();


        DBHandler db  = DBHandler.getInstance(this);
        if (db.checkInfo(user, password)) {
            // sessions: add to SharedPreferences
            SharedPreferences shared = getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString(MainActivity.USER, user);
            editor.putBoolean(MainActivity.IS_LOGGED_IN, true);
            editor.commit();

            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
        else {
            eUserName.requestFocus();
            eUserName.setError("Invalid Username!");

            ePassword.requestFocus();
            ePassword.setError("Invalid Password!");
        }
    }
}
