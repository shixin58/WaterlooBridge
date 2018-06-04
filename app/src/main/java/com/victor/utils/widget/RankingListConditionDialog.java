package com.victor.utils.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.victor.utils.R;
import com.victor.utils.ResUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Created by shixin on 2018/5/31.
 */
public class RankingListConditionDialog extends Dialog {

    private int mTopOffset;

    private List<String> mList = new ArrayList<>();
    private int mSelectedPosition;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public RankingListConditionDialog(@NonNull Context context) {
        super(context, R.style.ranking_list_condition_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ranking_list_condition);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();
        params.y = mTopOffset;
        params.width = ResUtils.getDisplayMetrics().widthPixels;
        window.setAttributes(params);
        window.setGravity(Gravity.TOP);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        initView();
    }

    private void initView() {
        GridView gridView = findViewById(R.id.grid_view);
        InnerAdapter adapter = new InnerAdapter();
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(mOnItemClickListener);
    }

    public void setTopOffset(int topOffset) {
        mTopOffset = topOffset;
    }

    public void setList(@NonNull List<String> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setList(@NonNull String[] strings) {
        mList.clear();
        Collections.addAll(mList, strings);
    }

    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private class InnerAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_select_car_level_list, null);
            }
            TextView tv = convertView.findViewById(R.id.selectcarlevellist_text);
            tv.setText(mList.get(position));

            tv.setSelected(mSelectedPosition == position);

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
