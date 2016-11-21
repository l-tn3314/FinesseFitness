package databaseSchema;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "finesse_fitness";

    // User Table Name
    private static final String TABLE_USER = "user";

    // User Table Column Names
    private static final String KEY_USERNAME = "username"; // Primary Key
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_DASHBOARD = "dashboard"; // Foreign Key to Dashboard

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
    private static final String KEY_DESCRIPTION = "excercise_description";

    // Dashboard Table Name
    private static final String TABLE_DASHBOARD = "dashboard";

    // Dashboard Table Column Names
    private static final String KEY_DASH_ID = "dashboard_id"; // Primary Key
    private static final String KEY_WK_GOAL = "work_goal";
    private static final String NUM_COMPLETED = "num_workouts_completed";

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

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table for the Dashboard entity
        String CREATE_DASHBOARD_TABLE = "CREATE TABLE" + TABLE_DASHBOARD + "(" +
                KEY_DASH_ID + " INT PRIMARY KEY, " + KEY_WK_GOAL + " VARCHAR, " +
                NUM_COMPLETED + " INT" + ")";

        // Creates the table for the User entity
        String CREATE_USER_TABLE = "CREATE TABLE" + TABLE_USER + "(" +
                KEY_USERNAME + " VARCHAR PRIMARY KEY, " + KEY_PASSWORD +
                        " VARCHAR NOT NULL, " + KEY_EMAIL + " VARCHAR NOT NULL, " +
                KEY_PHONE_NUMBER + " INT(9) NOT NULL, " + KEY_WEIGHT + " INT, " +
                KEY_HEIGHT + " INT, " + KEY_DASHBOARD + " INT, " +
                "FOREIGN KEY (" + KEY_DASHBOARD + ") REFERENCES " +
                TABLE_DASHBOARD + "(" + KEY_DASH_ID + ") );";

        // Creates the table for the Workout entity
        String CREATE_WORKOUT_TABLE = "CREATE TABLE" + TABLE_WORKOUT + "(" +
                KEY_WK_ID + " INT PRIMARY KEY, " + KEY_INTENSITY +
                " ENUM('beginner', 'intermediate', 'advanced')," +
                KEY_CALORIES_BURNED + " INT" + ")";

        // Create the table for the Exercise entity
        String CREATE_EXERCISE_TABLE = "CREATE TABLE" + TABLE_EXERCISE + "(" +
                KEY_EXER_ID + " INT PRIMARY KEY, " + KEY_EXER_NAME + " VARCHAR, "
                + KEY_DESCRIPTION + " VARCHAR " + ")";

        // Create the table for the Completes Relation
        String CREATE_COMPLETES_TABLE = "CREATE TABLE" + TABLE_COMPLETES + "(" +
                KEY_USER_COMP + " VARCHAR, " + KEY_WK_COMP + " INT, " +
                "FOREIGN KEY (" + KEY_USER_COMP + ") REFERENCES " +
                TABLE_USER + "(" + KEY_USERNAME + "), " +
                "FOREIGN KEY (" + KEY_WK_COMP + ") REFERENCES " +
                TABLE_WORKOUT + "(" + KEY_WK_ID + ") );";

        // Create the table for the Contains Relation
        String CREATE_CONTAINS_TABLE = "CREATE TABLE" + TABLE_CONTAINS + "(" +
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

}
