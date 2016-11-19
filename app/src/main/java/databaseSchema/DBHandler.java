package databaseSchema;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "finesse_fitness";

    // User Table Column Names
    private static final String KEY_USERNAME = "username"; // Primary Key
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_DASHBOARD = "dashboard"; // Foreign Key to Dashboard

    // Workout Table Column Names
    private static final String KEY_WK_ID = "workout_id"; // Primary Key
    private static final String KEY_INTENSITY = "intesity";
    private static final String KEY_CALORIES_BURNED = "calories_burned";

    // Excercise Table Column Names
    private static final String KEY_EXER_ID = "exercise_id"; // Primary Key
    private static final String KEY_EXER_NAME = "exercise_name";
    private static final String KEY_DESCRIPTION = "excercise_description";

    // Dashboard Table Column Names
    private static final String KEY_DASH_ID = "dashboard_id"; // Primary Key
    private static final String KEY_WK_GOAL = "work_goal";
    private static final String NUM_COMPLETED = "num_workouts_completed";

    // Completes Relation Table Column Names
    private static final String KEY_USER_COMP = "user_completed"; // Foreign Key to User
    private static final String KEY_WK_COMP = "workout_completed"; // Foreign Key to Workout

    // Contains Realtion Table Column Names
    private static final String KEY_WK_CONTAINS = "workout_contains"; // Foriegn Key to Workout
    private static final String KEY_EXER_CONTAINS = "exercise_contains"; // Foriegn Key to Exercise

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
