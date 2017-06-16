package com.konect4.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Muhammad Zeeshan on 5/31/2017.
 */

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "urdupoint";

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getKey(String key) {
        return pref.getString(key, "");
    }

    public void setKey(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
}

