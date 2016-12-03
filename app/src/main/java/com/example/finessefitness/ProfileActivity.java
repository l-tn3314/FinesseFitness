package com.example.finessefitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import databaseSchema.DBHandler;

public class ProfileActivity extends SidebarActivity {

    private SharedPreferences shared;
    private String username; // current user

    private EditText eFirstName;
    private EditText eLastName;
    private EditText ePassword;
    private EditText eEmail;
    private EditText ePhone;

    private EditText eGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);

        eFirstName = (EditText) findViewById(R.id.firstname);
        eLastName = (EditText) findViewById(R.id.lastname);
        ePassword = (EditText) findViewById(R.id.password);
        eEmail = (EditText) findViewById(R.id.email);
        ePhone = (EditText) findViewById(R.id.phone_number);

        eGoal = (EditText) findViewById(R.id.goal);


        shared = getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        username = shared.getString(MainActivity.USER, "");
        DBHandler db = DBHandler.getInstance(this);

        // set current values
        eFirstName.setText(db.userGetValOf(username, DBHandler.UserKey.FIRST_NAME));
        eLastName.setText(db.userGetValOf(username, DBHandler.UserKey.LAST_NAME));
        ePassword.setText(db.userGetValOf(username, DBHandler.UserKey.PASSWORD));
        eEmail.setText(db.userGetValOf(username, DBHandler.UserKey.EMAIL));
        ePhone.setText(db.userGetValOf(username, DBHandler.UserKey.PHONE_NUMBER));

        String height = db.userGetValOf(username, DBHandler.UserKey.HEIGHT);
        String weight = db.userGetValOf(username, DBHandler.UserKey.WEIGHT);
        if (height != null) {
            EditText eHeight = (EditText) findViewById(R.id.height);
            eHeight.setText(height);
        }
        if (weight != null) {
            EditText eWeight = (EditText) findViewById(R.id.weight);
            eWeight.setText(weight);
        }

        eGoal.setText(db.dashboardGetValOf(username, DBHandler.DashboardKey.WORKOUT_GOAL));

        // set username for profile
        TextView eUserName = (TextView) findViewById(R.id.username);
        eUserName.setText(username + "'s ");

    }

    // called when the user hits the update button
    public void updateProfile(View view){

        if (eFirstName.length() == 0 ) {
            eFirstName.requestFocus();
            eFirstName.setError("Field cannot be empty");
        }
        if (!(eFirstName.getText().toString().matches("[a-zA-Z ]+"))) {
            eFirstName.requestFocus();
            eFirstName.setError("Enter only alphabetical characters");
        }

        if (eLastName.length() == 0) {
            eLastName.requestFocus();
            eLastName.setError("Field cannot be empty");
        }

        if (!(eLastName.getText().toString().matches("[a-zA-Z ]+"))) {
            eLastName.requestFocus();
            eLastName.setError("Enter only alphabetical characters");
        }

        if (ePassword.length() == 0) {
            ePassword.requestFocus();
            ePassword.setError("Field cannot be empty");
        }
        if (eEmail.length() == 0) {
            eEmail.requestFocus();
            eEmail.setError("Field cannot be empty");
        }
        if (ePhone.length() == 0) {
            ePhone.requestFocus();
            ePhone.setError("Field cannot be empty");
        }

        if (eGoal.length() == 0) {
            eGoal.requestFocus();
            eGoal.setError("Field cannot be empty");
        }

        String firstName = eFirstName.getText().toString();

        String lastName = eLastName.getText().toString();

        String password = ePassword.getText().toString();

        String email = eEmail.getText().toString();

        int phoneNumber = Integer.parseInt(ePhone.getText().toString());

        EditText eHeight = (EditText) findViewById(R.id.height);
        Integer height;
        try {
            height = Integer.parseInt(eHeight.getText().toString());
            updateUserIfApplicable(Integer.toString(height), DBHandler.UserKey.HEIGHT);
        } catch (Exception e) {
            height = null;
            // case for null
            updateUserIfApplicable(null, DBHandler.UserKey.HEIGHT);
        }

        EditText eWeight = (EditText) findViewById(R.id.weight);
        Integer weight;
        try {
            weight = Integer.parseInt(eWeight.getText().toString());
            updateUserIfApplicable(Integer.toString(weight), DBHandler.UserKey.WEIGHT);
        } catch(Exception e) {
            weight = null;
            // case for null
            updateUserIfApplicable(null, DBHandler.UserKey.WEIGHT);
        }

        String goal = eGoal.getText().toString();

        // update user table if applicable
        updateUserIfApplicable(firstName, DBHandler.UserKey.FIRST_NAME);
        updateUserIfApplicable(lastName, DBHandler.UserKey.LAST_NAME);
        updateUserIfApplicable(password, DBHandler.UserKey.PASSWORD);
        updateUserIfApplicable(email, DBHandler.UserKey.EMAIL);
        updateUserIfApplicable(Integer.toString(phoneNumber), DBHandler.UserKey.PHONE_NUMBER);
        // height and weight updated above in try/catch

        // update dashboard goal
        updateDashGoalIfApplicable(goal);

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

    }

    private void updateUserIfApplicable(String val, DBHandler.UserKey key) {
        DBHandler db = DBHandler.getInstance(this);
        if (val == null || !val.equals(db.userGetValOf(username, key))) {
            db.userUpdateValOf(username, key, val);
        }
    }

    private void updateDashGoalIfApplicable(String val) {
        DBHandler db = DBHandler.getInstance(this);
        if (!val.equals(db.dashboardGetValOf(username, DBHandler.DashboardKey.WORKOUT_GOAL))) {
            db.dashboardUpdateValOf(username, DBHandler.DashboardKey.WORKOUT_GOAL, val);
        }
    }
}
