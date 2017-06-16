package com.konect4.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.facebook.FacebookSdk;
import com.konect4.R;
import com.konect4.adapter.MessagingAdapter;
import com.konect4.adapter.NewFriendsAdapter;
import com.konect4.model.Friends;
import com.konect4.model.MessageData;
import com.konect4.utilities.MyRecyclerView;
import java.util.ArrayList;

/**
 * Created by Muhammad Zeeshan on 6/6/2017.
 */

public class Messages extends Activity {

    ListView list_messages;
    MessagingAdapter adapter;
    ArrayList<MessageData> messages_data;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.messaging);

        list_messages = (ListView)findViewById(R.id.messaging_list);
        back = (ImageView)findViewById(R.id.back);

        messages_data = new ArrayList<MessageData>();
        populateList();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }




    public void populateList(){
        for (int i=0; i<10; i++){
            MessageData friends = new MessageData(i, "User Name "+i, "06-06-2017 11:30 am ", "messages send by the user", "image");

            messages_data.add(friends);
        }
        list_messages.setFocusable(false);
        adapter = new MessagingAdapter(Messages.this, messages_data);
        list_messages.setAdapter(adapter);
    }
}
