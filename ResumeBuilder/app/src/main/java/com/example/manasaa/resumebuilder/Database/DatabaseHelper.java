package com.example.manasaa.resumebuilder.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.manasaa.resumebuilder.Model.UserDetails;

/**
 * Created by manasa.a on 02-03-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG= "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;// Database Version
    private static final String DATABASE_NAME = "resumeManager";// Database Name
    private static final String TABLE_USER_DETAILS = "user_details";  // Table Names
//    private static final String TABLE_TAG = "tags";
//    private static final String TABLE_TODO_TAG = "todo_tags";
    // Common column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_NAME = "user_name_column";
    private static final String COLUMN_USER_EMAILID = "user_email_column";
    private static final String COLUMN_USER_EXPERIENCE = "user_experience_column";
    private static final String COLUMN_USER_PROFILE_URL ="user_profile_url";
    //Table create statement
    private static final String CREATE_TABLE_USER_DETAILS = "CREATE TABLE "
            + TABLE_USER_DETAILS +
            "(" + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_USER_NAME + " VARCHAR(25) ," +
            COLUMN_USER_EMAILID + " VARCHAR(45) UNIQUE," +
            COLUMN_USER_EXPERIENCE + " VARCHAR(50), " +
            COLUMN_USER_PROFILE_URL + " VARCHAR(100) " +
            ");";

    Context context;
    public DatabaseHelper(Context context) { //Constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {// creating required tables
        db.execSQL(CREATE_TABLE_USER_DETAILS );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insertIntoUserDetailsTable(UserDetails userDetails){ // Insert into UserDetails Table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, userDetails.getUserName()); // Contact Name
        values.put(COLUMN_USER_EMAILID, userDetails.getUserEmail()); // Contact Phone
        values.put(COLUMN_USER_PROFILE_URL, userDetails.getUserProfileLink());
        db.insert(TABLE_USER_DETAILS, null, values);// Inserting Row
        db.close(); // Closing database connection
    }
    public int getUserIdByEmail(String emailId) { // get row by emailID
        SQLiteDatabase sqliteQueryBuilder = this.getReadableDatabase();
        Cursor cursor =  sqliteQueryBuilder.rawQuery("select * from " + TABLE_USER_DETAILS + " where " + COLUMN_USER_EMAILID + "='" + emailId+ "'" , null);
        int userID=0;
        if (cursor != null) {
            Log.d(TAG,"called cursor!null");
            if (cursor.moveToFirst()) {
                Log.d(TAG, "called (cursor.moveToFirst())");
                userID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                Log.d(TAG,"called id " + userID);
            }
            cursor.close();
        }
        return userID;
    }

    public UserDetails getUserDetailsByID(int ID){
        SQLiteDatabase sqliteQueryBuilder = this.getReadableDatabase();
        Cursor cursor =  sqliteQueryBuilder.rawQuery("select * from " + TABLE_USER_DETAILS + " where " + COLUMN_ID + "='" + ID+ "'" , null);
        UserDetails usrDetails = new UserDetails();
        if (cursor != null) {
            Log.d(TAG,"called cursor!null");
            if (cursor.moveToFirst()) {
                Log.d(TAG, "called (cursor.moveToFirst())");
                usrDetails.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                usrDetails.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAILID)));
                usrDetails.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
               // Log.d(TAG,"called id " + userID);
            }
            cursor.close();
        }
        return usrDetails;
    };

    public int numberOfProfiles(){
        String countQuery = "SELECT  * FROM " + TABLE_USER_DETAILS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;

    };


}
