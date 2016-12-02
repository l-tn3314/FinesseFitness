package databaseSchema;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fitnessModel.User;

import static android.content.ContentValues.TAG;

public class DBHandler extends SQLiteOpenHelper {

    //Instance of this Database
    private static DBHandler sInstance;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "finesse_fitness";

    // User Table Name
    private static final String TABLE_USER = "user";

    // User Table Column Names
    private static final String KEY_USERNAME = "username"; // Primary Key
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "first_name";
    private static final String KEY_LNAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_DASHBOARD = "dashboard"; // Foreign Key to Dashboard
    // enum to access the fields in the user table
    public enum UserKey {
        USERNAME(KEY_USERNAME), PASSWORD(KEY_PASSWORD), FIRST_NAME(KEY_FNAME), LAST_NAME(KEY_LNAME),
        EMAIL(KEY_EMAIL), PHONE_NUMBER(KEY_PHONE_NUMBER), WEIGHT(KEY_WEIGHT), HEIGHT(KEY_HEIGHT),
        DASHBOARD(KEY_DASHBOARD);

        private String val;

        UserKey(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return this.val;
        }
    }

    // Workout Table Name
    private static final String TABLE_WORKOUT = "workout";

    // Workout Table Column Names
    private static final String KEY_WK_ID = "workout_id"; // Primary Key
    private static final String KEY_INTENSITY = "intesity";
    private static final String KEY_CALORIES_BURNED = "calories_burned";

    // Exercise Table Name
    private static final String TABLE_EXERCISE = "exercise";

    // Exercise Table Column Names
    private static final String KEY_EXER_ID = "exercise_id"; // Primary Key
    private static final String KEY_EXER_NAME = "exercise_name";
    private static final String KEY_DESCRIPTION = "exercise_description";

    // Dashboard Table Name
    private static final String TABLE_DASHBOARD = "dashboard";

    // Dashboard Table Column Names
    private static final String KEY_DASH_ID = "dashboard_id"; // Primary Key
    private static final String KEY_WK_GOAL = "work_goal";
    private static final String NUM_COMPLETED = "num_workouts_completed";
    // enum to access the fields in the dashboard table
    public enum DashboardKey {
        USERNAME(KEY_DASH_ID), WORKOUT_GOAL(KEY_WK_GOAL), WORKOUTS_COMPLETED(KEY_WK_COMP);

        private String val;

        DashboardKey(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return this.val;
        }
    }


    // Completes Table Name
    private static final String TABLE_COMPLETES = "completes";

    // Completes Relation Table Column Names
    private static final String KEY_USER_COMP = "user_completed"; // Foreign Key to User
    private static final String KEY_WK_COMP = "workout_completed"; // Foreign Key to Workout

    // Contains Table Name
    private static final String TABLE_CONTAINS = "contains";

    // Contains Relation Table Column Names
    private static final String KEY_WK_CONTAINS = "workout_contains"; // Foriegn Key to Workout
    private static final String KEY_EXER_CONTAINS = "exercise_contains"; // Foriegn Key to Exercise

    // Constructors
    public static synchronized DBHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHandler(context.getApplicationContext());
        }
        return sInstance;
    }


    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table for the Dashboard entity
        String CREATE_DASHBOARD_TABLE = "CREATE TABLE " + TABLE_DASHBOARD + "(" +
                KEY_DASH_ID + " INT PRIMARY KEY, " + KEY_WK_GOAL + " VARCHAR, " +
                NUM_COMPLETED + " INT" + ")";

        // Creates the table for the User entity
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
                KEY_USERNAME + " VARCHAR PRIMARY KEY, " + KEY_PASSWORD +
                " VARCHAR NOT NULL, "+ KEY_FNAME + " VARCHAR NOT NULL, " +
                KEY_LNAME + " VARCHAR NOT NULL, "+ KEY_EMAIL + " VARCHAR NOT NULL, " +
                KEY_PHONE_NUMBER + " INT(10) NOT NULL, " + KEY_WEIGHT + " INT, " +
                KEY_HEIGHT + " INT, " + KEY_DASHBOARD + " INT, " +
                "FOREIGN KEY (" + KEY_DASHBOARD + ") REFERENCES " +
                TABLE_DASHBOARD + "(" + KEY_DASH_ID + ") );";

        // Creates the table for the Workout entity
        String CREATE_WORKOUT_TABLE = "CREATE TABLE " + TABLE_WORKOUT + "(" +
                KEY_WK_ID + " INT PRIMARY KEY, " + KEY_INTENSITY +
                " VARCHAR, " +
                KEY_CALORIES_BURNED + " INT" + ")";

        // Create the table for the Exercise entity
        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISE + "(" +
                KEY_EXER_ID + " INT PRIMARY KEY, " + KEY_EXER_NAME + " VARCHAR, "
                + KEY_DESCRIPTION + " VARCHAR " + ")";

        // Create the table for the Completes Relation
        String CREATE_COMPLETES_TABLE = "CREATE TABLE " + TABLE_COMPLETES + "(" +
                KEY_USER_COMP + " VARCHAR, " + KEY_WK_COMP + " INT, " +
                "FOREIGN KEY (" + KEY_USER_COMP + ") REFERENCES " +
                TABLE_USER + "(" + KEY_USERNAME + "), " +
                "FOREIGN KEY (" + KEY_WK_COMP + ") REFERENCES " +
                TABLE_WORKOUT + "(" + KEY_WK_ID + ") );";

        // Create the table for the Contains Relation
        String CREATE_CONTAINS_TABLE = "CREATE TABLE " + TABLE_CONTAINS + "(" +
                KEY_WK_CONTAINS + " INT, " + KEY_EXER_CONTAINS + " INT, " +
                "FOREIGN KEY (" + KEY_WK_CONTAINS + ") REFERENCES " +
                TABLE_WORKOUT + "(" + KEY_WK_ID + "), " +
                "FOREIGN KEY (" + KEY_EXER_CONTAINS + ") REFERENCES " +
                TABLE_EXERCISE + "(" + KEY_EXER_ID + ") );";

        // Execute the queries to create tables
        db.execSQL(CREATE_DASHBOARD_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_CONTAINS_TABLE);
        db.execSQL(CREATE_COMPLETES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DASHBOARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETES);

        // Creating tables again
        this.onCreate(db);
    }

    /*

     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();


        db.beginTransaction();
          try{
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.userName);
        values.put(KEY_FNAME, user.firstName);
        values.put(KEY_LNAME, user.lastName);
        values.put(KEY_PASSWORD, user.password);
        values.put(KEY_EMAIL, user.email);
        values.put(KEY_PHONE_NUMBER, user.phoneNumber);
        values.put(KEY_HEIGHT, user.height);
        values.put(KEY_WEIGHT, user.weight);

        db.insert(TABLE_USER, null, values);
        db.setTransactionSuccessful();

          } catch (Exception e) {
            Log.d(TAG, "Error while trying to add user to database");
          } finally {
        db.endTransaction();
         }
    }

    public boolean checkInfo(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultset = db.rawQuery("SELECT " + KEY_USERNAME + " FROM " + TABLE_USER,
                null);
        List<String> usernames = new ArrayList<String>();
        while(resultset.moveToNext()) {
            String addition = resultset.getString(resultset.getColumnIndex(KEY_USERNAME));
            usernames.add(addition);

        }
        if (usernames.contains(username)) {
            return userGetValOf(username, UserKey.PASSWORD).equals(password);
        }
        else {
            return false;
        }
    }

    /*
    Gets the given field from the User table for the given username
     */
    public String userGetValOf(String username, UserKey key) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {key.toString()};
        String selection = KEY_USERNAME + " = ?";
        String[] selectionArgs = { username };
        Cursor c = db.query(
                TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        c.moveToFirst();
        return c.getString(0);
    }

    /*
    Gets the given field from the Dashboard table for the given username
     */
    public String dashboardGetValOf(String username, DashboardKey key) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {key.toString()};
        String selection = KEY_DASH_ID + " = ?";
        String[] selectionArgs = { username };
        Cursor c = db.query(
                TABLE_DASHBOARD,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        c.moveToFirst();
        return c.getString(0);
    }
}
