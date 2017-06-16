package com.konect4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.facebook.FacebookSdk;
import com.konect4.R;
import com.konect4.adapter.ActiveFriendsAdapter;
import com.konect4.model.Friends;
import com.konect4.utilities.CustomRequest;
import com.konect4.utilities.Functions;
import com.konect4.utilities.GPSTracker;
import com.konect4.utilities.Globals;
import com.konect4.utilities.MyRecyclerView;
import com.konect4.utilities.MyVolley;
import com.konect4.utilities.SessionManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Muhammad Zeeshan on 6/2/2017.
 */

public class Home extends Activity {

    MyRecyclerView list_friends;
    ActiveFriendsAdapter adapter;
    ArrayList<Friends> friends_data;
    TextView checkin, friends_list;
    CircleImageView profile_image;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        checkin = (TextView) findViewById(R.id.checkin);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        username = (TextView) findViewById(R.id.username);
        friends_list = (TextView) findViewById(R.id.friends_list);

        list_friends = (MyRecyclerView)findViewById(R.id.list_friends);
        LinearLayoutManager llm = new LinearLayoutManager(Home.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list_friends.setLayoutManager(llm);

        friends_data = new ArrayList<Friends>();
        populateList();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Checkin.class);
                startActivity(intent);
            }
        });

        friends_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FriendsList.class);
                startActivity(intent);
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }


    public void populateList(){
        for (int i=0; i<20; i++){
            Friends friends = new Friends(""+i, "User Name "+i, "image", "", false, false);
            friends_data.add(friends);
        }

        list_friends.setFocusable(false);
        adapter = new ActiveFriendsAdapter(Home.this, friends_data, true);
        list_friends.setAdapter(adapter);
    }


}
