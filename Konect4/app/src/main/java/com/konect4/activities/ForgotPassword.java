package com.konect4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.facebook.FacebookSdk;
import com.konect4.R;
import com.konect4.model.MessageData;
import com.konect4.utilities.CustomRequest;
import com.konect4.utilities.Functions;
import com.konect4.utilities.Globals;
import com.konect4.utilities.MyVolley;
import com.konect4.utilities.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Muhammad Zeeshan on 6/1/2017.
 */

public class ForgotPassword extends Activity {

    ImageView back;
    TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.forgot_password);

        back = (ImageView)findViewById(R.id.back);
        sign_up = (TextView) findViewById(R.id.sign_up);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    /*
    SignIn API
    */
    public void signUpAPI(final String source) {
        Functions.showDialog("Signing Up...", ForgotPassword.this);
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("android", "1");
        param.put("method", "fb_login");

        Log.d("CHECKING FOR PARAMS ", " " + param.toString());

        final RequestQueue queue = MyVolley.getRequestQueue();
        CustomRequest crq = new CustomRequest(com.android.volley.Request.Method.POST, Globals.BASEURL, param, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String error_msg = "";
                String token = "";
                String user_id = "";
                String http_status;

                Functions.hideDialog();

                Log.d("checking for the SignUp response", " " + response);

                try {
                    error_msg = response.optString("error");
                    token = response.optJSONObject("data").optString("token");
                    http_status = response.optString("http_status");
                    user_id = response.optJSONObject("data").optString("user_id");


                    if (http_status.equals("200")) {
                        SessionManager s = new SessionManager(getApplicationContext());
                        s.setKey("token", token);
                        s.setKey("user_id", user_id);
                        s.setKey("LoginStatus", "false");
                        s.setKey("is_remember", "false");


                        Functions.hideDialog();
//                        Intent intent = new Intent(getApplicationContext(), Home.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        finish();
                    }
                    else{
                        if (error_msg.equals("")) {
                            Functions.showToast(ForgotPassword.this, getResources().getString(R.string.some_error));

                        } else {
                            Functions.showToast(ForgotPassword.this, error_msg);
                        }
                    }

                } catch (Exception e) {
                    Functions.showToast(ForgotPassword.this, getResources().getString(R.string.some_error));
                    Functions.hideDialog();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Functions.hideDialog();
                volleyError.printStackTrace();
                if (volleyError.networkResponse == null) {
                    Functions.showToast(ForgotPassword.this, getResources().getString(R.string.slow_internet));
                } else {
                    Functions.showToast(ForgotPassword.this, getResources().getString(R.string.some_error));
                }
            }
        }, "SignUp");
        crq.setShouldCache(false);
        queue.add(crq);
    }
}