package com.konect4.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.konect4.R;
import com.konect4.utilities.CustomRequest;
import com.konect4.utilities.Functions;
import com.konect4.utilities.Globals;
import com.konect4.utilities.MyVolley;
import com.konect4.utilities.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Muhammad Zeeshan on 5/31/2017.
 */

public class Welcome extends Activity {

    Button facebook, login;
    TextView register;
    private LoginButton btnFbLogin;
    private static final String PREF_FB_ID = "facebook_user_id";

    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private static final int RC_SIGN_IN = 2;

    String fb_user_id, fb_token, fb_email, first_name, last_name;
    CallbackManager callbackManager;
    String TAG = "WELCOME SCREEN";
    final private int REQUEST_CODE_LOCATION_REQUEST = 0x5;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 0x4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        facebook=(Button)findViewById(R.id.facebook);
        login=(Button) findViewById(R.id.sign_in);
        register=(TextView)findViewById(R.id.register);

        btnFbLogin = (LoginButton) findViewById(R.id.fb_login_button);
        btnFbLogin.setReadPermissions(Arrays.asList("public_profile, email, user_friends"));  // user_birthday
        callbackManager = CallbackManager.Factory.create();

        /**
         * Volley init call
         */
        if (MyVolley.getRequestQueue() == null)
            MyVolley.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Functions.showToast(Welcome.this, "In-Process");

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LoginManager.getInstance().logOut();
                btnFbLogin.performClick();
            }
        });


        // Callback registration
        btnFbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d("FbLogin", "Success");

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {

                            if (object.has("first_name") && object.has("last_name")){
                                first_name=object.optString("first_name");
                                last_name=object.optString("last_name");
                            }
                            else{
                                String splitted[]=object.optString("name").split(" ");
                                first_name=splitted[0];
                                last_name=splitted[1];
                            }

                            fb_user_id=object.optString("id");
                            fb_email=object.optString("email");
                            fb_token=loginResult.getAccessToken().toString();

//                            socialMediaLogin("facebook");

//                            {"id":"1672503456394999","name":"Muhammad Zeeshan","email":"muhammad.zeeshan@purelogics.net","gender":"male"}

                            Log.d("!@#!@#!@#@#@", response.toString()+" " + loginResult.getAccessToken().getToken());
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

//                            LoginSocialMedia("facebook");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                //TODO: onCancel
                Log.d("FbLogin", "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                //TODO: onError
                Log.d("FbLogin", "Error");
                exception.printStackTrace();
            }
        });
        checkAndRequestPermissions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
                return;
            }
            mIntentInProgress = false;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    /*
     Check permissions for 6.0 and above verions
     */
    private  boolean checkAndRequestPermissions() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int permissionLocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        // int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int memorycardPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ReadPermissionMemory = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ReadPermissionMemory != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (memorycardPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_REQUEST: {
            }
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                //  perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //   Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                ) {
                            showDialogOK("Camera and  Gallery  access  Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }


    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

}
