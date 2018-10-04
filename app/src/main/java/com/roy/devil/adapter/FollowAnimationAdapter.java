package com.roy.devil.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.max.baselib.ResUtils;
import com.roy.devil.R;

/**
 * <p>Created by shixin on 2018/2/8.
 */
public class FollowAnimationAdapter extends BaseAdapter {

    private String[] strings = new String[]{ResUtils.getString(R.string.dog), "cat", "tiger", "lion", "panda",
            "lobster", "crab", "shellfish", "rat", "dragon fruit"};
    private View.OnClickListener mOnClickListener;

    public FollowAnimationAdapter(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public String getItem(int i) {
        return strings[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_follow_animation, viewGroup, false);
        }
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(strings[i]);
        View flFollow = view.findViewById(R.id.flFollow);
        flFollow.setOnClickListener(mOnClickListener);
        return view;
    }
}
