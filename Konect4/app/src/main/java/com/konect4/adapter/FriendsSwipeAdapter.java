package com.konect4.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.konect4.R;

import java.util.List;

/**
 * Created by Muhammad Zeeshan on 6/12/2017.
 */

public class FriendsSwipeAdapter  extends BaseAdapter {

    private List<String> data;
    private Context context;

    public FriendsSwipeAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            // normally use a viewholder
            v = inflater.inflate(R.layout.item_swipe_friends, parent, false);
        }
        ((TextView) v.findViewById(R.id.textView2)).setText("Username Goes Here"); //+data.get(position));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String)getItem(position);
                Log.i("MainActivity", item);
            }
        });

        return v;
    }
}