package com.konect4.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.internal.Utility;
import com.konect4.R;
import com.konect4.model.MessageData;
import com.konect4.utilities.Functions;

import java.util.ArrayList;

/**
 * Created by Muhammad Zeeshan on 6/6/2017.
 */

public class MessagingAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater = null;
    private ArrayList<MessageData> arrayList;

    public MessagingAdapter(Activity activity, ArrayList<MessageData> arrayList) {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList=arrayList;
    }

    public int getCount() {
        if (arrayList.size() <= 0)
            return 0;
        return arrayList.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public static class ViewHolder {
        TextView username;
        TextView date_time;
        TextView message;
        ImageView profile_image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();


            if (position%2==0){
                vi = inflater.inflate(R.layout.item_message_my, null);
            }
            else{
                vi = inflater.inflate(R.layout.item_message_other, null);
            }

            holder.username = (TextView) vi.findViewById(R.id.username);
            holder.date_time = (TextView) vi.findViewById(R.id.date_time);
            holder.message = (TextView) vi.findViewById(R.id.message);
            holder.profile_image = (ImageView) vi.findViewById(R.id.profile_image);


            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        Functions.showImage("http://blog.kievukraine.info/1016.jpg", holder.profile_image, null);

        return vi;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

}