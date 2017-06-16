package com.konect4.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.konect4.R;
import com.konect4.activities.Messages;
import com.konect4.model.Friends;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Muhammad Zeeshan on 6/2/2017.
 */

public class ActiveFriendsAdapter extends RecyclerView.Adapter<ActiveFriendsAdapter.FriendsViewHolder> {

    private final ArrayList<Friends> videos_arrayList;
    private Context context;
    private boolean show_msg;

    public ActiveFriendsAdapter(Activity activity, ArrayList<Friends> videos_arrayList, boolean show_msg) {
        this.context = activity;
        this.videos_arrayList = videos_arrayList;
        this.show_msg=show_msg;
    }


    @Override
    public void onBindViewHolder(final FriendsViewHolder homeViewHolder, final int i) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //Version 4.1 and Above
//            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/nastalique_new.ttf");   // adobearabic
//            homeViewHolder.headeing.setTypeface(font);
//            homeViewHolder.headline_date.setTypeface(font);
//        }else {
//            // Version 4.1 below
//            homeViewHolder.headeing.setPadding(0, 3, 0, 8);
//            homeViewHolder.headeing.setLineSpacing(13, 1);
//        }

        homeViewHolder.selected.setVisibility(View.GONE);
        if (show_msg){
            homeViewHolder.btn_msg.setVisibility(View.VISIBLE);
        }


        homeViewHolder.btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Messages.class);
                context.startActivity(intent);
            }
        });


        homeViewHolder.username.setText(videos_arrayList.get(i).getName());
//        homeViewHolder.headline_date.setText(videos_arrayList.get(i).getNews_date());
//        Utility.showImage(videos_arrayList.get(i).getGet_img_big(),homeViewHolder.vimg,homeViewHolder.cp);


    }

    @Override
    public int getItemCount() {
        if (videos_arrayList.size() <= 0)
            return 0;
        return videos_arrayList.size();
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_active_friends, viewGroup, false);

        return new FriendsViewHolder(itemView);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        protected TextView username;
        protected CircleImageView image;
        protected ImageView selected;
        protected ImageView btn_msg;

        public FriendsViewHolder(View v) {
            super(v);
            username = (TextView) v.findViewById(R.id.username);
            image = (CircleImageView) v.findViewById(R.id.profile_image);
            selected = (ImageView) v.findViewById(R.id.is_selected);
            btn_msg = (ImageView) v.findViewById(R.id.btn_msg);
        }
    }
}