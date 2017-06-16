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
import android.widget.ImageView;
import android.widget.TextView;

import com.konect4.R;
import com.konect4.model.Friends;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Muhammad Zeeshan on 6/6/2017.
 */

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder> {

    private ArrayList<Friends> friends_arrayList;
    private Context context;

    public FriendsListAdapter(Activity activity, ArrayList<Friends> friends_arrayList) {
        this.context = activity;
        this.friends_arrayList = friends_arrayList;
    }


    @Override
    public void onBindViewHolder(final FriendsListAdapter.FriendsViewHolder homeViewHolder, final int position) {


        homeViewHolder.username.setText(friends_arrayList.get(position).getName());
//        homeViewHolder.headline_date.setText(videos_arrayList.get(i).getNews_date());
//        Utility.showImage(videos_arrayList.get(i).getGet_img_big(),homeViewHolder.vimg,homeViewHolder.cp);


        if (position<=3){
            homeViewHolder.block.setVisibility(View.VISIBLE);
        }
        else if (position>3 && position<=6){
            homeViewHolder.add.setVisibility(View.VISIBLE);
        }
        else if (position>6){
            homeViewHolder.invite.setVisibility(View.VISIBLE);
        }

//        homeViewHolder.un_friend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                alert.setTitle("Un-Friend");
//                alert.setMessage("Are you sure you want to un-friend?");
//                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //do your work here
//                        dialog.dismiss();
//
//                    }
//                });
//                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        dialog.dismiss();
//                    }
//                });
////                alert.create();
//                alert.show();
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        if (friends_arrayList.size() <= 0)
            return 0;
        return friends_arrayList.size();
    }

    @Override
    public FriendsListAdapter.FriendsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_friends_list, viewGroup, false);

        return new FriendsListAdapter.FriendsViewHolder(itemView);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        protected TextView username;
        protected CircleImageView image;
        protected Button block;
        protected Button add;
        protected Button invite;

        public FriendsViewHolder(View v) {
            super(v);
            username = (TextView) v.findViewById(R.id.username);
            image = (CircleImageView) v.findViewById(R.id.profile_image);
            block = (Button) v.findViewById(R.id.block);
            invite = (Button) v.findViewById(R.id.invite);
            add = (Button) v.findViewById(R.id.add);
        }
    }


    public void updateList(ArrayList<Friends> list){
        friends_arrayList = list;
        notifyDataSetChanged();
    }
}