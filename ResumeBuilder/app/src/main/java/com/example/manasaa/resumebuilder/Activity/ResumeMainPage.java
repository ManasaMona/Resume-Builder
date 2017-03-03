package com.example.manasaa.resumebuilder.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.manasaa.resumebuilder.Database.DatabaseHelper;
import com.example.manasaa.resumebuilder.Model.UserDetails;
import com.example.manasaa.resumebuilder.Other.CircleTransform;
import com.example.manasaa.resumebuilder.Other.ComplexRecyclerViewAdapter;
import com.example.manasaa.resumebuilder.R;

import java.util.ArrayList;

import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_EMAIL;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_NAME;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_PROFILEURL;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_USERID;
import static com.example.manasaa.resumebuilder.Other.SessionManager.PREF_NAME;

public class ResumeMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = ResumeMainPage.class.getSimpleName();

    private static String user_name,user_profileImageUrl,user_emailId;
    public static int USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG," called OnCreate ");
        setContentView(R.layout.activity_resume_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doesUserExist();//Checks userExsits or not

        // DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Navigation view DrawerLayout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //For header in navigationView
        View navigationHeader = navigationView.getHeaderView(0);
        TextView userName = (TextView) navigationHeader.findViewById(R.id.user_name);
        userName.setText(user_name);
        TextView userEmail = (TextView) navigationHeader.findViewById(R.id.user_emailID);
        userEmail.setText(user_emailId);
        ImageView user_profile_image = (ImageView) navigationHeader.findViewById(R.id.user_imageView);


        Glide.with(this).load(user_profileImageUrl)
                .crossFade()
                .thumbnail(0.2f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(user_profile_image);


        Log.d(TAG,"called useridddd " + USER_ID);

        //ForRecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewProfile);
        recyclerView.setAdapter(new ComplexRecyclerViewAdapter(getSampleArrayList(),getBaseContext()));
        recyclerView .setLayoutManager(new LinearLayoutManager(this));
    }

    private void doesUserExist() {
        Log.d(TAG,"called DoesUSerExcits{ ");
        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        user_name = shared.getString(KEY_NAME,"");
        user_emailId = shared.getString(KEY_EMAIL,"");
        user_profileImageUrl = shared.getString(KEY_PROFILEURL,"");
        Log.d(TAG,user_name + user_emailId+ user_profileImageUrl + "username email onStrt called ");

        int userID;
        DatabaseHelper database = new DatabaseHelper(this);
        int count= database.numberOfProfiles();
        Log.d(TAG,"called  coutn"+ count);
        if(count==0){
            Log.d(TAG,"called  if(cursor==null)");
            UserDetails userDetails = new UserDetails(user_name,user_emailId,user_profileImageUrl);
            database.insertIntoUserDetailsTable(userDetails);
             userID= database.getUserIdByEmail(user_emailId);
            Log.d(TAG,userID+" called UserID");
            shared.edit().putInt(KEY_USERID,userID);
            USER_ID =userID;
        }
        else{
            Log.d(TAG,"called  if(cursor!!!!=null)");
            userID = database.getUserIdByEmail(user_emailId);
            Log.d(TAG,userID+" called UserID");
            shared.edit().putInt(KEY_USERID,userID);
            USER_ID =userID;

        }


    }

    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        DatabaseHelper database = new DatabaseHelper(this);
        UserDetails obj = database.getUserDetailsByID(USER_ID);
        items.add(obj);
        return items;
    }


    @Override
    public void onBackPressed() {
        Log.d(TAG," called OnBackPressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resume_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  // Handle action bar item clicks here. The action bar will// automatically handle clicks on the Home/Up button, so long// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d(TAG," called  onNavigationItemSelected(MenuItem item) ");
        int id = item.getItemId();
        if (id == R.id.nav_profile_main_page) {

        } else if (id == R.id.nav_about) {// Handle navigation view item clicks here.
            Log.d(TAG," called  R.id.nav_about) ");
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate( Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
//        drawer.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"called onStart()");

    }
}
