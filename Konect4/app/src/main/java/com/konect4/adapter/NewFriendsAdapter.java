package com.konect4.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.konect4.R;
import com.konect4.model.Friends;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Muhammad Zeeshan on 6/6/2017.
 */

public class NewFriendsAdapter extends RecyclerView.Adapter<NewFriendsAdapter.FriendsViewHolder> {

    private final ArrayList<Friends> friends_arrayList;
    private Context context;

    public NewFriendsAdapter(Activity activity, ArrayList<Friends> friends_arrayList) {
        this.context = activity;
        this.friends_arrayList = friends_arrayList;
    }


    @Override
    public void onBindViewHolder(final NewFriendsAdapter.FriendsViewHolder homeViewHolder, final int i) {


        homeViewHolder.username.setText(friends_arrayList.get(i).getName());


        homeViewHolder.add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (friends_arrayList.size() <= 0)
            return 0;
        return friends_arrayList.size();
    }

    @Override
    public NewFriendsAdapter.FriendsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_add_friend, viewGroup, false);

        return new NewFriendsAdapter.FriendsViewHolder(itemView);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        protected TextView username;
        protected CircleImageView image;
        protected Button add_friend;

        public FriendsViewHolder(View v) {
            super(v);
            username = (TextView) v.findViewById(R.id.username);
            image = (CircleImageView) v.findViewById(R.id.profile_image);
            add_friend = (Button) v.findViewById(R.id.add_friend);
        }
    }
}