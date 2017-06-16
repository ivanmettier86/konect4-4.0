package com.konect4.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.facebook.FacebookSdk;
import com.konect4.R;
import com.konect4.adapter.ActiveFriendsAdapter;
import com.konect4.adapter.FriendsAddAdapter;
import com.konect4.adapter.GroupsAdapter;
import com.konect4.model.Friends;
import com.konect4.model.Group;
import com.konect4.utilities.CustomRequest;
import com.konect4.utilities.Functions;
import com.konect4.utilities.GPSTracker;
import com.konect4.utilities.Globals;
import com.konect4.utilities.MyRecyclerView;
import com.konect4.utilities.MyVolley;
import com.konect4.utilities.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Muhammad Zeeshan on 6/2/2017.
 */

public class Checkin extends Activity {

    MyRecyclerView list_friends;
    FriendsAddAdapter adapter;
    ArrayList<Friends> friends_data;

    MyRecyclerView list_users;
    FriendsAddAdapter adapter_users;
    ArrayList<Friends> users_data;

    MyRecyclerView list_groups;
    GroupsAdapter adapter_groups;
    ArrayList<Group> group_data;
    GPSTracker gpsTracker;

    Button next;
    ImageView back;
    String city="", address="", country="";
    double lat, lng;
    TextView address_txt;
    Button check_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);

        next = (Button)findViewById(R.id.next);
        back = (ImageView)findViewById(R.id.back);
        check_in = (Button) findViewById(R.id.checkin);

        list_friends = (MyRecyclerView)findViewById(R.id.list_friends);
        list_users = (MyRecyclerView)findViewById(R.id.list_users);
        address_txt = (TextView) findViewById(R.id.address);

        list_groups = (MyRecyclerView)findViewById(R.id.list_groups);
        LinearLayoutManager llm = new LinearLayoutManager(Checkin.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list_friends.setLayoutManager(llm);

        LinearLayoutManager llm_users = new LinearLayoutManager(Checkin.this);
        llm_users.setOrientation(LinearLayoutManager.VERTICAL);
        list_users.setLayoutManager(llm_users);

        LinearLayoutManager llm2 = new LinearLayoutManager(Checkin.this);
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        list_groups.setLayoutManager(llm2);

        friends_data = new ArrayList<Friends>();
        users_data = new ArrayList<Friends>();
        group_data = new ArrayList<Group>();
        populateList();
        populateUsersList();
        populateGroupsList();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FinalCreateGroup.class);
                startActivity(intent);
            }
        });

        check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateData();
            }
        });

        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else{
                //populateData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void populateData() {
        gpsTracker=new GPSTracker(Checkin.this);
        gpsTracker.getLocation();

        GetCurrentLocation();
    }


    /*
   Current Location of User
   */
    private void GetCurrentLocation() {
        if (gpsTracker.canGetLocation()) {
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
            city= gpsTracker.getLocality(this);
            address= gpsTracker.getAddressLine(this);
            country=gpsTracker.getCountryName(this);

            address_txt.setText(address+", "+city);

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    public void populateList(){
        for (int i=0; i<4; i++){
            Friends friends;
            if (i==0){
                friends = new Friends(""+i, "My Name goes here ", "image", "", false, true);
            }
            else{
                friends = new Friends(""+i, "User Name "+i, "image", "", false, true);
            }

            friends_data.add(friends);
        }
        adapter = new FriendsAddAdapter(Checkin.this, friends_data);
        list_friends.setAdapter(adapter);
    }


    public void populateUsersList(){
        for (int i=0; i<10; i++){
            Friends friends = new Friends(""+i, "User Name "+i, "image", "", false, false);
            users_data.add(friends);
        }
        adapter_users = new FriendsAddAdapter(Checkin.this, users_data);
        list_users.setAdapter(adapter_users);
    }


    public void populateGroupsList(){
        for (int i=0; i<4; i++){
            Group groups = new Group("User "+i, "image");
            group_data.add(groups);
        }
        adapter_groups = new GroupsAdapter(Checkin.this, group_data);
        list_groups.setAdapter(adapter_groups);
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //populateData();


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


}