package com.roy.devil.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.max.baselib.ResUtils;
import com.max.baselib.widget.BaseRecyclerAdapter;
import com.roy.devil.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Created by shixin on 2018/4/19.
 */
public class BottomChooseDialog extends Dialog {

    private List<String> mList = new ArrayList<>();
    private BaseRecyclerAdapter.OnItemClickListener mOnItemClickListener;
    private View.OnClickListener mOnCancelClickListener;

    public BottomChooseDialog(@NonNull Context context) {
        super(context, R.style.dialog_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_choose);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ResUtils.getDisplayMetrics().widthPixels;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter myAdapter = new MyAdapter(mList);
        if(mOnItemClickListener!=null) {
            myAdapter.setOnItemClickListener(mOnItemClickListener);
        }
        recyclerView.setAdapter(myAdapter);

        if(mOnCancelClickListener!=null) {
            findViewById(R.id.tv_cancel).setOnClickListener(mOnCancelClickListener);
        }
    }

    public void setList(List<String> list) {
        if(list!=null) {
            mList.clear();
            mList.addAll(list);
        }
    }

    public void setList(String[] strings) {
        if(strings!=null) {
            mList.clear();
            Collections.addAll(mList, strings);
        }
    }

    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnCancelClickListener(View.OnClickListener listener) {
        mOnCancelClickListener = listener;
    }

    static class MyAdapter extends BaseRecyclerAdapter<MyAdapter.MyViewHolder> {

        private List<String> mList = new ArrayList<>();

        MyAdapter(List<String> list) {
            setList(list);
        }

        void setList(List<String> list) {
            if(list==null){
                return;
            }
            mList.clear();
            mList.addAll(list);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bottom_choose, parent, false));
        }

        @Override
        public void onBindVH(@NonNull MyViewHolder holder, int position) {
            holder.tvContent.setText(mList.get(position));
            holder.divider.setVisibility((position==mList.size()-1)?View.GONE:View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvContent;
            View divider;
            public MyViewHolder(View itemView) {
                super(itemView);
                tvContent = itemView.findViewById(R.id.tv_content);
                divider = itemView.findViewById(R.id.divider);
            }
        }
    }
}
