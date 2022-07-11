package com.roy.devil.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bride.baselib.ResUtils;
import com.roy.devil.R;

/**
 * <p>Created by shixin on 2018/2/8.
 */
public class FollowAnimationAdapter extends BaseAdapter {
    private final String[] strings = new String[]{ResUtils.getString(R.string.dog), "cat", "tiger", "lion", "panda",
            "lobster", "crab", "shellfish", "rat", "dragon fruit"};
    private final View.OnClickListener mOnClickListener;

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
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            // convertView复用提升性能。LayoutInflater简单工厂
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_follow_animation, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.tv_title);
            viewHolder.flFollow = convertView.findViewById(R.id.flFollow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(strings[i]);
        viewHolder.flFollow.setOnClickListener(mOnClickListener);
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        View flFollow;
    }
}
