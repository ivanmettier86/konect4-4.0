package com.konect4.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.konect4.R;
import com.konect4.adapter.FriendsListAdapter;
import com.konect4.adapter.NewFriendsAdapter;
import com.konect4.model.Friends;
import com.konect4.model.MessageData;
import com.konect4.utilities.MyRecyclerView;

import java.util.ArrayList;

/**
 * Created by Muhammad Zeeshan on 6/6/2017.
 */

public class NewFriends extends Activity {

    MyRecyclerView list_friends;
    NewFriendsAdapter adapter;
    ArrayList<Friends> friends_data;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.new_friends_list);

        list_friends = (MyRecyclerView)findViewById(R.id.list_friends);
        LinearLayoutManager llm = new LinearLayoutManager(NewFriends.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list_friends.setLayoutManager(llm);

        friends_data = new ArrayList<Friends>();
        populateList();

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void populateList(){
        for (int i=0; i<10; i++){
            Friends friends = new Friends(""+i, "User Name "+i, "image", "", false, false);
            friends_data.add(friends);
        }
        list_friends.setFocusable(false);
        adapter = new NewFriendsAdapter(NewFriends.this, friends_data);
        list_friends.setAdapter(adapter);
    }
}
