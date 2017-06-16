package com.konect4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.facebook.FacebookSdk;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;
import com.konect4.R;
import com.konect4.adapter.FriendsListAdapter;
import com.konect4.model.Friends;
import com.konect4.utilities.CustomRequest;
import com.konect4.utilities.Functions;
import com.konect4.utilities.GPSTracker;
import com.konect4.utilities.Globals;
import com.konect4.utilities.MyVolley;
import com.konect4.utilities.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Muhammad Zeeshan on 6/6/2017.
 */

public class FriendsList extends Activity {

    RecyclerView list_friends;
    FriendsListAdapter adapter;
    ArrayList<Friends> friends_data;
    ImageView back;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);

        list_friends = (RecyclerView)findViewById(R.id.list_friends);
        search = (EditText)findViewById(R.id.search);
        friends_data = new ArrayList<Friends>();

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });

        init(list_friends);
    }


    public void filter(String text){
        ArrayList<Friends> temp = new ArrayList<Friends>();
        for(Friends d: friends_data){
            //or use .contains(text)
            if(d.getName().contains(text)){
                temp.add(d);
            }
        }
        adapter.updateList(temp);
    }




    private void init(RecyclerView recyclerView) {

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        boolean is_contacts=false, is_friends=true;
        for (int i=0; i<10; i++){
            Friends friends = new Friends(""+i, "User Name "+i, "image", "", is_contacts, is_friends);
            friends_data.add(friends);
        }
        adapter = new FriendsListAdapter(FriendsList.this, friends_data);
        recyclerView.setAdapter(adapter);


        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onPendingDismiss(RecyclerViewAdapter recyclerView, int position) {

                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
//                                adapter.remove(position);
                            }
                        });
        touchListener.setDismissDelay(900000);
        recyclerView.setOnTouchListener(touchListener);
        touchListener.undoPendingDismiss();
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            list_friends.addOnScrollListener((RecyclerView.OnScrollListener)touchListener.makeScrollListener());
        }
        else{
            list_friends.setOnScrollListener((RecyclerView.OnScrollListener)touchListener.makeScrollListener());
        }

        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.message_img) {
                            touchListener.undoPendingDismiss();
                            Intent intent = new Intent(getApplicationContext(), Messages.class);
                            startActivity(intent);
                        }else if (view.getId() == R.id.txt_msg) {
                            touchListener.undoPendingDismiss();
                            Intent intent = new Intent(getApplicationContext(), Messages.class);
                            startActivity(intent);
                        }else if (view.getId() == R.id.add) {

                        }
                        else if (view.getId() == R.id.block) {

                        } else { // R.id.txt_data
                        }
                    }
                }));
    }


}