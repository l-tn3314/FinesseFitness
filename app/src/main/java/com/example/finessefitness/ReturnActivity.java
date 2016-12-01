package com.example.finessefitness;


import android.content.Intent;
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
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, ReturnActivity.class);
            startActivity(intent);
        }
    }
}
