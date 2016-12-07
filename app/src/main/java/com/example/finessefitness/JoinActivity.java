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
        EditText eLastName = (EditText) findViewById(R.id.lastname);
        EditText eUserName = (EditText) findViewById(R.id.username);
        EditText ePassword = (EditText) findViewById(R.id.password);
        EditText eEmail = (EditText) findViewById(R.id.email);
        EditText ePhone = (EditText) findViewById(R.id.phone_number);

        boolean valid = true;

        if (eFirstName.length() == 0 ) {
            valid = false;
            eFirstName.requestFocus();
            eFirstName.setError("Field cannot be empty");
        }
        if (!(eFirstName.getText().toString().matches("[a-zA-Z ]+"))) {
            valid = false;
            eFirstName.requestFocus();
            eFirstName.setError("Enter only alphabetical characters");
        }

        if (eLastName.length() == 0) {
            valid = false;
            eLastName.requestFocus();
            eLastName.setError("Field cannot be empty");
        }

        if (!(eLastName.getText().toString().matches("[a-zA-Z ]+"))) {
            valid = false;
            eLastName.requestFocus();
            eLastName.setError("Enter only alphabetical characters");
        }

        if (eUserName.length() == 0) {
            valid = false;
            eUserName.requestFocus();
            eUserName.setError("Field cannot be empty");
        }
        else {
            String s = "";
            try {
                // checks for unique username
                DBHandler handler = DBHandler.getInstance(this);
                s = handler.userGetValOf(eUserName.getText().toString(), DBHandler.UserKey.USERNAME);
            } catch (Exception e) {
                // ok - username does not exist
            }
            //System.out.println(s);
            if (!s.equals("")) {
                eUserName.requestFocus();
                eUserName.setError("Username already taken!");
                valid = false;
            }
        }

        if (ePassword.length() == 0) {
            ePassword.requestFocus();
            ePassword.setError("Field cannot be empty");
            valid = false;
        }
        if (eEmail.length() == 0) {
            eEmail.requestFocus();
            eEmail.setError("Field cannot be empty");
            valid = false;
        }
        if (ePhone.length() == 0) {
            ePhone.requestFocus();
            ePhone.setError("Field cannot be empty");
            valid = false;
        }

        if (valid) {
            user.firstName = eFirstName.getText().toString();

            user.lastName = eLastName.getText().toString();

            user.userName = eUserName.getText().toString();

            user.password = ePassword.getText().toString();

            user.email = eEmail.getText().toString();

            user.phoneNumber = Integer.parseInt(ePhone.getText().toString());

            EditText eHeight = (EditText) findViewById(R.id.height);
            try {
                user.height = Integer.parseInt(eHeight.getText().toString());
            } catch(Exception e) {
                user.height = null;
            }

            EditText eWeight = (EditText) findViewById(R.id.weight);
            try {
                user.weight = Integer.parseInt(eWeight.getText().toString());
            } catch(Exception e) {
                user.weight = null;
            }
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
}
