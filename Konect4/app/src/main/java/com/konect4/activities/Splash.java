package com.konect4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.konect4.R;

/**
 * Created by Muhammad Zeeshan on 5/31/2017.
 */

public class Splash extends Activity {

    int SPLASH_TIME=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), Welcome.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME);
    }
}