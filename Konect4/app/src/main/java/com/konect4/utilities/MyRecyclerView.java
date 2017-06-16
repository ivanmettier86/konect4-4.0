package com.konect4.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Muhammad Zeeshan on 6/2/2017.
 */

public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = heightMeasureSpec;
        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT)
            heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}