package com.example.manasaa.resumebuilder.Other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.manasaa.resumebuilder.Activity.MainActivity;
import com.example.manasaa.resumebuilder.Activity.ResumeMainPage;

import java.util.HashMap;

public class SessionManager {
    private static final String TAG=SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREF_NAME = "LOGINPREF";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //USER   ID
    public static final String KEY_USERID = "userID";


    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    //PROFILE URL
    public static final String KEY_PROFILEURL="profile_url";

    // Constructor
    public SessionManager(Context context){
        Log.d(TAG, "called SessionManager(Context context)");
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email,String profileurl){
        Log.d(TAG, "called createLoginSession(");
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);


        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        //
        editor.putString(KEY_PROFILEURL, profileurl);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){

        Log.d(TAG, "called checkLogin()");
        // Check login status
        if(this.isLoggedIn()){
            Log.d(TAG, this.isLoggedIn()+" called loged in");
            Intent intentToProfilePage=new Intent(_context,ResumeMainPage.class);
            intentToProfilePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentToProfilePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intentToProfilePage);
        }
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        //
        user.put(KEY_PROFILEURL, pref.getString(KEY_PROFILEURL,null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}