package com.roy.devil.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bride.baselib.widget.BaseRecyclerAdapter;
import com.roy.devil.R;

import java.util.List;

/**
 * <p>Created by shixin on 2018/10/20.
 */
public class MusicAdapter extends BaseRecyclerAdapter<MusicAdapter.MyViewHolder, String> {

    public void setList(List<String> list) {
        if(list==null||list.isEmpty()) return;
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindVH(MyViewHolder viewHolder, int i) {
        viewHolder.tvName.setText(mList.get(i).substring(mList.get(i).lastIndexOf('/')+1));
    }

    @Override
    public MyViewHolder onCreateVH(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_music, viewGroup, false));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = this.itemView.findViewById(R.id.tv_name);
        }
    }
}
