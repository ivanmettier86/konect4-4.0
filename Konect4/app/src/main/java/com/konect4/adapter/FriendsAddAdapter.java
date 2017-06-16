package com.konect4.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konect4.R;
import com.konect4.model.Friends;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Muhammad Zeeshan on 6/5/2017.
 */

public class FriendsAddAdapter extends RecyclerView.Adapter<FriendsAddAdapter.FriendsViewHolder> {

    private final ArrayList<Friends> videos_arrayList;
    private Context context;

    public FriendsAddAdapter(Activity activity, ArrayList<Friends> videos_arrayList) {
        this.context = activity;
        this.videos_arrayList = videos_arrayList;
    }


    @Override
    public void onBindViewHolder(final FriendsAddAdapter.FriendsViewHolder homeViewHolder, final int position) {

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


        if (position==0){
            homeViewHolder.selected.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.checked_friend, null));
        }


        homeViewHolder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(homeViewHolder.selected.getDrawable().getConstantState().equals(ResourcesCompat.getDrawable(context.getResources(), R.drawable.checked_friend, null).getConstantState())){  // context.getResources().getDrawable(R.drawable.checked_friend).getConstantState())){
                    homeViewHolder.selected.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.unchecked_friend, null));  //context.getResources().getDrawable(R.drawable.unchecked_friend));
//                    filtered_list.get(position).setIs_selected(false);
//                    selected_count--;
                }
                else{
//                    selected_count++;
//                    if(selected_count>2){
//                        selected_count--;
//                        Functions.showToast((Activity)context, "You can select maximum 2 charities");
//                        return;
//                    }
                    homeViewHolder.selected.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.checked_friend, null));
//                    homeViewHolder.selected.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_friend));
//                    filtered_list.get(position).setIs_selected(true);
                }


//                String token32 = SharedPreference.getValueFromStorage(context.getApplicationContext(), "token", SharedPreference.USER_PREF_NAME);
//                SessionManager s = new SessionManager(context.getApplicationContext());
//                s.getKey("token");
//                if (token32==null){
//                    SharedPreference.SaveValueToStorage(context.getApplicationContext(), "token", s.getKey("token"), SharedPreference.USER_PREF_NAME);
//                    SharedPreference.SaveValueToStorage(context.getApplicationContext(), "LoginStatus", "true", SharedPreference.USER_PREF_NAME);
//                }
            }
        });


        homeViewHolder.username.setText(videos_arrayList.get(position).getName());
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
    public FriendsAddAdapter.FriendsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_active_friends, viewGroup, false);

        return new FriendsAddAdapter.FriendsViewHolder(itemView);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        protected TextView username;
        protected CircleImageView image;
        protected ImageView selected;

        public FriendsViewHolder(View v) {
            super(v);
            username = (TextView) v.findViewById(R.id.username);
            image = (CircleImageView) v.findViewById(R.id.profile_image);
            selected = (ImageView) v.findViewById(R.id.is_selected);
        }
    }
}