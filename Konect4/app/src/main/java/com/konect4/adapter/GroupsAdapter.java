package com.konect4.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konect4.R;
import com.konect4.model.Friends;
import com.konect4.model.Group;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Muhammad Zeeshan on 6/2/2017.
 */

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.FriendsViewHolder> {

    private final ArrayList<Group> groups_arrayList;
    private Context context;

    public GroupsAdapter(Activity activity, ArrayList<Group> videos_arrayList) {
        this.context = activity;
        this.groups_arrayList = videos_arrayList;
    }


    @Override
    public void onBindViewHolder(final GroupsAdapter.FriendsViewHolder homeViewHolder, final int i) {

//        homeViewHolder.headline_date.setText(videos_arrayList.get(i).getNews_date());
//        Utility.showImage(videos_arrayList.get(i).getGet_img_big(),homeViewHolder.vimg,homeViewHolder.cp);

    }

    @Override
    public int getItemCount() {
        if (groups_arrayList.size() <= 0)
            return 0;
        return groups_arrayList.size();
    }

    @Override
    public GroupsAdapter.FriendsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.items_groups, viewGroup, false);

        return new GroupsAdapter.FriendsViewHolder(itemView);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        protected CircleImageView image;

        public FriendsViewHolder(View v) {
            super(v);
            image = (CircleImageView) v.findViewById(R.id.profile_image);
        }
    }
}