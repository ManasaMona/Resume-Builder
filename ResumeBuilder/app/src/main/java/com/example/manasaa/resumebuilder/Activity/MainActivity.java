package com.example.manasaa.resumebuilder.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.manasaa.resumebuilder.Other.SessionManager;
import com.example.manasaa.resumebuilder.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTitle;
    private Button mSignInButton_google, mSignInButton_facebook;
    // Session Manager Class
    SessionManager session;

    //for google sign in
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;

    //for fACEBOOK
    private static String APP_ID = "@string/facebook_app_id";// Your Facebook APP ID
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    ProgressDialog progressDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "called  onCreate()");
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
//        try{
//            PackageInfo info = getPackageManager().getPackageInfo("com.example.manasaa.resumebuilder",PackageManager.GET_SIGNATURES);
//            for(Signature signature : info.signatures){
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        }catch(PackageManager.NameNotFoundException e){
//
//        }catch(NoSuchAlgorithmException e){
//
//        }


        setContentView(R.layout.activity_main);
        // Session class instance
        session = new SessionManager(getApplicationContext());
        //session.checkLogin();
        Log.d(TAG, session.isLoggedIn() + "called session is logged in");
        if (session.isLoggedIn()) {
            session.checkLogin();
        }
        //Custom Font for title
        mTitle = (TextView) findViewById(R.id.mainPagetitle);
        Typeface face = Typeface.createFromAsset(getAssets(), "fontStyles/font1.ttf");
        mTitle.setTypeface(face);

        //Google SignIn
        mSignInButton_google = (Button) findViewById(R.id.google_signin_button);
        mSignInButton_google.setOnClickListener(this);

        //facebookSign in
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("public_profile", "email", "user_friends");
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.google_signin_button:
                                            if (mGoogleApiClient == null) {
                                                signInwithGoogle();
                                            }
                                            break;
            case R.id.login_button:
                                        signInwithFacebook();
        }
    }

    private void signInwithFacebook() {
                callbackManager = CallbackManager.Factory.create();
                progressDialog = new ProgressDialog(MainActivity.this);
        //        progressDialog.setMessage("Loading...");
                // progressDialog.show();
                loginButton.setPressed(true);
                loginButton.invalidate();
                loginButton.registerCallback(callbackManager, mCallBack);
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        String email, name, profilePicUrl;
        @Override
        public void onSuccess(LoginResult loginResult) {
            progressDialog.dismiss();
            Log.d(TAG, "called mcallback success");
            // App code
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            Log.d("response: ", response + "");
                            try {
                                String facebookID = object.getString("id").toString();
                                email = object.getString("email").toString();
                                name = object.getString("name").toString();
                                String gender = object.getString("gender").toString();
                                profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                Log.d(TAG, profilePicUrl + "--------- email ---");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, email + "--------- email ---");
                            // Session Manager
                            session = new SessionManager(getApplicationContext());
                            session.createLoginSession(name, email, profilePicUrl);
                            session.checkLogin();
                            finish();
                        }

                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender, birthday, picture.type(large)");
                    request.setParameters(parameters);
                    request.executeAsync();
        }
        @Override
        public void onCancel() {
            progressDialog.dismiss();
        }
        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
        }
    };
    //For GooglePlus
    private void signInwithGoogle() {
        Log.d(TAG,"called signInwithGoogle()");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // Configure sign-in to request the user's ID, email address, and basic// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)// Build a GoogleApiClient with access to the Google Sign-In API and the// options specified by gso.
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "called onActivityResult ");
        if (requestCode == RC_SIGN_IN) {//return from SignIn API by google// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            Log.d(TAG, "called requestCode == RC_SIGN_IN ");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else { //If not request code is RC_SIGN_IN it must be facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {//After getting data from googleAPI
        Log.d(TAG, " called handleSignInResult: " + result.isSuccess());
        if (result.isSuccess()) {
            Log.d(TAG, " called result Success");
            GoogleSignInAccount acct = result.getSignInAccount();
            String userName = acct.getDisplayName();
            String emailId = acct.getEmail();
            String profileURL = acct.getPhotoUrl() + "";
            Log.d(TAG, userName + emailId + profileURL);
            // Session Manager
            session = new SessionManager(getApplicationContext());
            session.createLoginSession(userName, emailId, profileURL);
            mGoogleApiClient.disconnect();
        } else {
            Intent intentToProfilePage = new Intent(this, ResumeMainPage.class);
            startActivity(intentToProfilePage);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "called On REsume()");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Log.d(TAG, "called connection true mGoogleApiClient!=null && mGoogleApiClient.isConnected()");
        } else {
            Log.d(TAG, " called connection faalse mGoogleApiClient!=null && mGoogleApiClient.isConnected()\"");
        }
        Log.d(TAG, session.isLoggedIn() + " called session.isLogged in value");
        if (session.isLoggedIn()) {
            session.checkLogin();
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "called onStart()");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "called onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "called onDestroy()");
    }

}
