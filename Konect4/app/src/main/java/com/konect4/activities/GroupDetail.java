package com.konect4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.facebook.FacebookSdk;
import com.konect4.R;
import com.konect4.adapter.FriendsSwipeAdapter;

import java.util.ArrayList;

/**
 * Created by Muhammad Zeeshan on 6/5/2017.
 */

public class GroupDetail extends Activity {

    Button match, accepted, not_match, return_btn;
    ImageView dislike, like, undo;
    SwipeDeck friends_card_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);

        match = (Button) findViewById(R.id.match);
        not_match = (Button) findViewById(R.id.not_match);
        accepted = (Button) findViewById(R.id.accepted);
        return_btn = (Button) findViewById(R.id.return_btn);

        dislike = (ImageView) findViewById(R.id.dislike);
        like = (ImageView) findViewById(R.id.like);
        undo = (ImageView) findViewById(R.id.undo);

        friends_card_list = (SwipeDeck) findViewById(R.id.swipe_deck);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SuggestGroup.class);
                startActivity(intent);
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        final ArrayList<String> testData = new ArrayList<>();
        testData.add("0");
        testData.add("1");
        testData.add("2");
        testData.add("3");
        testData.add("4");

        final FriendsSwipeAdapter adapter = new FriendsSwipeAdapter(testData, this);
        friends_card_list.setAdapter(adapter);

        friends_card_list.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Toast.makeText(getApplicationContext(), "You DisLike this user", Toast.LENGTH_SHORT).show();
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                Toast.makeText(getApplicationContext(), "You Liked this user", Toast.LENGTH_SHORT).show();
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {
                Log.i("MainActivity", "cardActionDown");

            }

            @Override
            public void cardActionUp() {
                Log.i("MainActivity", "cardActionUp");

            }
        });
    }
}