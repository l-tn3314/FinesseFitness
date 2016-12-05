package databaseSchema;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.finessefitness.MainActivity;
import com.example.finessefitness.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fitnessModel.User;
import hard_code.Workouts;

import static android.content.ContentValues.TAG;

public class DBHandler extends SQLiteOpenHelper {

    //Instance of this Database
    private static DBHandler sInstance;

    // Database Version
    private static final int DATABASE_VERSION = 2;

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
    private static final String KEY_WK_NAME = "workout_name";
    private static final String KEY_CALORIES_BURNED = "calories_burned";
    private static final String KEY_USER_CREATED = "user_created";

    // Exercise Table Name
    private static final String TABLE_EXERCISE = "exercise";

    private static int exercise_id = 1;
    // Exercise Table Column Names
    private static final String KEY_EXER_ID = "exercise_id"; // Primary Key
    private static final String KEY_EXER_NAME = "exercise_name";
    private static final String KEY_DESCRIPTION = "exercise_description";

    // Dashboard Table Name
    private static final String TABLE_DASHBOARD = "dashboard";

    private static int dash_id = 1;
    // Dashboard Table Column Names
    private static final String KEY_DASH_ID = "dashboard_id"; // Primary Key
    private static final String KEY_WK_GOAL = "work_goal";
    private static final String KEY_NUM_COMPLETED = "num_workouts_completed";

    // enum to access the fields in the dashboard table
    public enum DashboardKey {
        USERNAME(KEY_DASH_ID), WORKOUT_GOAL(KEY_WK_GOAL), WORKOUTS_COMPLETED(KEY_NUM_COMPLETED);

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
    private static final String KEY_WK_CONTAINS = "workout_contains"; // Foreign Key to Workout
    private static final String KEY_EXER_CONTAINS = "exercise_contains"; // Foreign Key to Exercise

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
                KEY_NUM_COMPLETED + " INT" + ")";

        // Creates the table for the User entity
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
                KEY_USERNAME + " VARCHAR PRIMARY KEY, " + KEY_PASSWORD +
                " VARCHAR NOT NULL, " + KEY_FNAME + " VARCHAR NOT NULL, " +
                KEY_LNAME + " VARCHAR NOT NULL, " + KEY_EMAIL + " VARCHAR NOT NULL, " +
                KEY_PHONE_NUMBER + " INT(10) NOT NULL, " + KEY_WEIGHT + " INT, " +
                KEY_HEIGHT + " INT, " + KEY_DASHBOARD + " INT, " +
                "FOREIGN KEY (" + KEY_DASHBOARD + ") REFERENCES " +
                TABLE_DASHBOARD + "(" + KEY_DASH_ID + ") );";

        // Creates the table for the Workout entity
        String CREATE_WORKOUT_TABLE = "CREATE TABLE " + TABLE_WORKOUT + "(" +
                KEY_WK_ID + " INT PRIMARY KEY, " + KEY_WK_NAME + " VARCHAR, " +
                KEY_CALORIES_BURNED + " INT, " +  KEY_USER_CREATED + " VARCHAR NOT NULL, " +
                "FOREIGN KEY (" + KEY_USER_CREATED + ") REFERENCES " +
        TABLE_USER + "(" + KEY_USERNAME + ") );";

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

        addExercises();

        /* TODO:
        // Add trigger to add a dashboard when a new user is added
        db.execSQL("CREATE TRIGGER create_user_dashboard" +
                    " AFTER INSERT" +
                    " ON " + TABLE_USER +
                    " FOR EACH ROW" +
                    " BEGIN" +
                    " INSERT INTO " + TABLE_DASHBOARD +
                    " VALUES (NEW." + KEY_USERNAME + ", 'Start working out!', 0)" +
                    " END;");
        */
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

    public void addExercises() {
        SQLiteDatabase db = this.getWritableDatabase();
        AppCompatActivity act = new MainActivity();
        String[] exercises = act.getResources().getStringArray(R.array.default_exercise_array);
        String description = "description";
        for (int i = 0; i < exercises.length; i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_EXER_ID, i);
            values.put(KEY_EXER_NAME, exercises[i]);
            values.put(KEY_DESCRIPTION, description);
            db.insert(TABLE_EXERCISE, null, values);
        }
        exercise_id = exercises.length;
    }

    /*
    Adds the given user to the User table, and a dashboard for the user in the Dashboard table
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            // insert into user table
            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, user.userName);
            values.put(KEY_FNAME, user.firstName);
            values.put(KEY_LNAME, user.lastName);
            values.put(KEY_PASSWORD, user.password);
            values.put(KEY_EMAIL, user.email);
            values.put(KEY_PHONE_NUMBER, user.phoneNumber);
            values.put(KEY_HEIGHT, user.height);
            values.put(KEY_WEIGHT, user.weight);
            values.put(KEY_DASHBOARD, dash_id);

            db.insert(TABLE_USER, null, values);

            // insert into dashboard table
            ContentValues dashVals = new ContentValues();
            dashVals.put(KEY_DASH_ID, dash_id);
            dashVals.put(KEY_WK_GOAL, "Start working out!");
            dashVals.put(KEY_NUM_COMPLETED, 0);

            db.insert(TABLE_DASHBOARD, null, dashVals);

            db.setTransactionSuccessful();
            // update dash_id (increment)
            dash_id++;
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add user to database");
        } finally {
            db.endTransaction();
        }
    }


    /** Add workouts to the database */
    public void addWorkouts() {
        SQLiteDatabase db = this.getReadableDatabase();
        AppCompatActivity act = new MainActivity();
        String[] workouts = act.getResources().getStringArray(R.array.workout_array);
        ContentValues cv = new ContentValues();
        cv.put(KEY_WK_ID, 0);
        cv.put(KEY_WK_NAME, workouts[0]);
        cv.put(KEY_CALORIES_BURNED, 500);
        cv.put(KEY_USER_CREATED, "FinesseFitness");
        db.insert(TABLE_WORKOUT, null, cv);

        ContentValues cv1 = new ContentValues();
        cv1.put(KEY_WK_ID, 1);
        cv1.put(KEY_WK_NAME, workouts[1]);
        cv1.put(KEY_CALORIES_BURNED, 400);
        cv1.put(KEY_USER_CREATED, "FinesseFitness");
        db.insert(TABLE_WORKOUT, null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put(KEY_WK_ID, 2);
        cv2.put(KEY_WK_NAME, workouts[2]);
        cv2.put(KEY_CALORIES_BURNED, 550);
        cv2.put(KEY_USER_CREATED, "FinesseFitness");
        db.insert(TABLE_WORKOUT, null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(KEY_WK_ID, 3);
        cv3.put(KEY_WK_NAME, workouts[3]);
        cv3.put(KEY_CALORIES_BURNED, 320);
        cv3.put(KEY_USER_CREATED, "FinesseFitness");
        db.insert(TABLE_WORKOUT, null, cv3);

        ContentValues cv4 = new ContentValues();
        cv4.put(KEY_WK_ID, 4);
        cv4.put(KEY_WK_NAME, workouts[4]);
        cv4.put(KEY_CALORIES_BURNED, 320);
        cv4.put(KEY_USER_CREATED, "FinesseFitness");
        db.insert(TABLE_WORKOUT, null, cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put(KEY_WK_ID, 5);
        cv5.put(KEY_WK_NAME, workouts[5]);
        cv5.put(KEY_CALORIES_BURNED, 400);
        cv5.put(KEY_USER_CREATED, "FinesseFitness");
        db.insert(TABLE_WORKOUT, null, cv5);
    }

    /** Add values to the Exercise/ Workout Relation Table */
    public void addContains() {
        SQLiteDatabase db = this.getReadableDatabase();
        AppCompatActivity act = new MainActivity();
        String[] workouts = act.getResources().getStringArray(R.array.workout_array);
        String[] exercises = act.getResources().getStringArray(R.array.default_exercise_array);
        List<String> exerciseList = Arrays.asList(exercises);
        ContentValues c0 = new ContentValues();
        c0.put(KEY_WK_CONTAINS, 0);
        c0.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Squats"));
        db.insert(TABLE_CONTAINS, null, c0);

        ContentValues c1 = new ContentValues();
        c1.put(KEY_WK_CONTAINS, 0);
        c1.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Forward Lunges"));
        db.insert(TABLE_CONTAINS, null, c1);

        ContentValues c2 = new ContentValues();
        c2.put(KEY_WK_CONTAINS, 0);
        c2.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Burpee Tuck Jump"));
        db.insert(TABLE_CONTAINS, null, c2);

        ContentValues c3 = new ContentValues();
        c3.put(KEY_WK_CONTAINS, 0);
        c3.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Power Skip"));
        db.insert(TABLE_CONTAINS, null, c3);

        ContentValues c4 = new ContentValues();
        c4.put(KEY_WK_CONTAINS, 0);
        c4.put(KEY_EXER_CONTAINS, exerciseList.indexOf("High Knees"));
        db.insert(TABLE_CONTAINS, null, c4);

        ContentValues c5 = new ContentValues();
        c5.put(KEY_WK_CONTAINS, 0);
        c5.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Butt Kicks"));
        db.insert(TABLE_CONTAINS, null, c5);

        ContentValues c6 = new ContentValues();
        c6.put(KEY_WK_CONTAINS, 0);
        c6.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Squat Leg Raises"));
        db.insert(TABLE_CONTAINS, null, c6);

        ContentValues c7 = new ContentValues();
        c7.put(KEY_WK_CONTAINS, 0);
        c7.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Standing Calf Raises"));
        db.insert(TABLE_CONTAINS, null, c7);

        ContentValues c8 = new ContentValues();
        c8.put(KEY_WK_CONTAINS, 0);
        c8.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Alternating Drop Lunges"));
        db.insert(TABLE_CONTAINS, null, c8);

        ContentValues c9 = new ContentValues();
        c9.put(KEY_WK_CONTAINS, 1);
        c9.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Butt Kicks"));
        db.insert(TABLE_CONTAINS, null, c9);

        ContentValues c10 = new ContentValues();
        c10.put(KEY_WK_CONTAINS, 1);
        c10.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Wall Sit"));
        db.insert(TABLE_CONTAINS, null, c10);

        ContentValues c11 = new ContentValues();
        c11.put(KEY_WK_CONTAINS, 1);
        c11.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Burpee Squat"));
        db.insert(TABLE_CONTAINS, null, c11);

        ContentValues c12 = new ContentValues();
        c12.put(KEY_WK_CONTAINS, 1);
        c12.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Squats"));
        db.insert(TABLE_CONTAINS, null, c12);

        ContentValues c13 = new ContentValues();
        c13.put(KEY_WK_CONTAINS, 1);
        c13.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Hip-Lift Progression"));
        db.insert(TABLE_CONTAINS, null, c13);

        ContentValues c14 = new ContentValues();
        c14.put(KEY_WK_CONTAINS, 1);
        c14.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Squat Leg Raises"));
        db.insert(TABLE_CONTAINS, null, c14);

        ContentValues c15 = new ContentValues();
        c15.put(KEY_WK_CONTAINS, 1);
        c15.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Sumo Squat"));
        db.insert(TABLE_CONTAINS, null, c15);

        ContentValues c16 = new ContentValues();
        c16.put(KEY_WK_CONTAINS, 2);
        c16.put(KEY_EXER_CONTAINS, exerciseList.indexOf("High Knees"));
        db.insert(TABLE_CONTAINS, null, c16);

        ContentValues c17 = new ContentValues();
        c17.put(KEY_WK_CONTAINS, 2);
        c17.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Jumping Jacks"));
        db.insert(TABLE_CONTAINS, null, c17);

        ContentValues c18 = new ContentValues();
        c18.put(KEY_WK_CONTAINS, 2);
        c18.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Fast Feet Shuffle"));
        db.insert(TABLE_CONTAINS, null, c18);

        ContentValues c19 = new ContentValues();
        c19.put(KEY_WK_CONTAINS, 2);
        c19.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Long Jump with Jog"));
        db.insert(TABLE_CONTAINS, null, c19);

        ContentValues c20 = new ContentValues();
        c20.put(KEY_WK_CONTAINS, 2);
        c20.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Jumping Lunges"));
        db.insert(TABLE_CONTAINS, null, c20);

        ContentValues c21 = new ContentValues();
        c21.put(KEY_WK_CONTAINS, 2);
        c21.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Butt Kicks"));
        db.insert(TABLE_CONTAINS, null, c21);

        ContentValues c22 = new ContentValues();
        c22.put(KEY_WK_CONTAINS, 2);
        c22.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Bicycle"));
        db.insert(TABLE_CONTAINS, null, c22);

        ContentValues c23 = new ContentValues();
        c23.put(KEY_WK_CONTAINS, 2);
        c23.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Ab Crunches"));
        db.insert(TABLE_CONTAINS, null, c23);

        ContentValues c24 = new ContentValues();
        c24.put(KEY_WK_CONTAINS, 3);
        c24.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Bicycle"));
        db.insert(TABLE_CONTAINS, null, c24);

        ContentValues c25 = new ContentValues();
        c25.put(KEY_WK_CONTAINS, 3);
        c25.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Plank"));
        db.insert(TABLE_CONTAINS, null, c25);

        ContentValues c26 = new ContentValues();
        c26.put(KEY_WK_CONTAINS, 3);
        c26.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Leg Raises"));
        db.insert(TABLE_CONTAINS, null, c26);

        ContentValues c27 = new ContentValues();
        c27.put(KEY_WK_CONTAINS, 3);
        c27.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Wall Sit"));
        db.insert(TABLE_CONTAINS, null, c27);

        ContentValues c28 = new ContentValues();
        c28.put(KEY_WK_CONTAINS, 3);
        c28.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Cobra"));
        db.insert(TABLE_CONTAINS, null, c28);

        ContentValues c29 = new ContentValues();
        c29.put(KEY_WK_CONTAINS, 4);
        c29.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Touch the Sky"));
        db.insert(TABLE_CONTAINS, null, c29);

        ContentValues c30 = new ContentValues();
        c30.put(KEY_WK_CONTAINS, 4);
        c30.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Calf Stretch"));
        db.insert(TABLE_CONTAINS, null, c30);

        ContentValues c31 = new ContentValues();
        c31.put(KEY_WK_CONTAINS, 4);
        c31.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Runner Stretch"));
        db.insert(TABLE_CONTAINS, null, c31);

        ContentValues c32 = new ContentValues();
        c32.put(KEY_WK_CONTAINS, 4);
        c32.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Forward Hang"));
        db.insert(TABLE_CONTAINS, null, c32);

        ContentValues c33 = new ContentValues();
        c33.put(KEY_WK_CONTAINS, 4);
        c33.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Cobra"));
        db.insert(TABLE_CONTAINS, null, c33);

        ContentValues c34 = new ContentValues();
        c34.put(KEY_WK_CONTAINS, 4);
        c34.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Seated Back Twist"));
        db.insert(TABLE_CONTAINS, null, c34);

        ContentValues c35 = new ContentValues();
        c35.put(KEY_WK_CONTAINS, 4);
        c35.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Toe Touch"));
        db.insert(TABLE_CONTAINS, null, c35);

        ContentValues c36 = new ContentValues();
        c36.put(KEY_WK_CONTAINS, 5);
        c36.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Bicep Curls"));
        db.insert(TABLE_CONTAINS, null, c36);

        ContentValues c37 = new ContentValues();
        c37.put(KEY_WK_CONTAINS, 5);
        c37.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Dumbbell Squats"));
        db.insert(TABLE_CONTAINS, null, c37);

        ContentValues c38 = new ContentValues();
        c38.put(KEY_WK_CONTAINS, 5);
        c38.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Side Raises"));
        db.insert(TABLE_CONTAINS, null, c38);

        ContentValues c39 = new ContentValues();
        c39.put(KEY_WK_CONTAINS, 5);
        c39.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Shoulder Press"));
        db.insert(TABLE_CONTAINS, null, c39);

        ContentValues c40 = new ContentValues();
        c40.put(KEY_WK_CONTAINS, 5);
        c40.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Pushups"));
        db.insert(TABLE_CONTAINS, null, c40);

        ContentValues c41 = new ContentValues();
        c41.put(KEY_WK_CONTAINS, 5);
        c41.put(KEY_EXER_CONTAINS, exerciseList.indexOf("Lateral Raises"));
        db.insert(TABLE_CONTAINS, null, c41);


    }

    public boolean checkInfo(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultset = db.rawQuery("SELECT " + KEY_USERNAME + " FROM " + TABLE_USER,
                null);
        List<String> usernames = new ArrayList<String>();
        while (resultset.moveToNext()) {
            String addition = resultset.getString(resultset.getColumnIndex(KEY_USERNAME));
            usernames.add(addition);
        }
        return usernames.contains(username) &&
                userGetValOf(username, UserKey.PASSWORD).equals(password);
    }

    /*
    Gets the given field from the User table for the given username
     */
    public String userGetValOf(String username, UserKey key) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {key.toString()};
        String selection = KEY_USERNAME + " = ?";
        String[] selectionArgs = {username};
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
    Updates the User table with the given newVal for the UserKey of username
    Returns true if more than one row is updated
    */
    public boolean userUpdateValOf(String username, UserKey key, String newVal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        // if field to be modified is an int
        if (key == UserKey.HEIGHT || key == UserKey.WEIGHT || key == UserKey.PHONE_NUMBER) {
            if (newVal == null) {
                args.put(key.toString(), (byte[]) null);
            } else {
                args.put(key.toString(), Integer.parseInt(newVal));
            }
        } else { // else String
            args.put(key.toString(), newVal);
        }
        String[] userArg = {username};
        return db.update(TABLE_USER, args, KEY_USERNAME + "=?", userArg) > 0;
    }

    /*
    Gets the given field from the Dashboard table for the given username
     */
    public String dashboardGetValOf(String username, DashboardKey key) {
        String selectArg = userGetValOf(username, UserKey.DASHBOARD);
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {key.toString()};
        String selection = KEY_DASH_ID + " = ?";
        String[] selectionArgs = {selectArg};
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

    /*
    Updates the Dashboard table with the given newVal for the UserKey of username
    Returns true if more than one row is updated
    */
    public boolean dashboardUpdateValOf(String username, DashboardKey key, String newVal) {
        String selectArg = userGetValOf(username, UserKey.DASHBOARD);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(key.toString(), newVal);
        String[] userArg = {selectArg};
        return db.update(TABLE_DASHBOARD, args, KEY_DASH_ID + "=?", userArg) > 0;
    }
}
