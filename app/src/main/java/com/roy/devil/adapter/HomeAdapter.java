package com.roy.devil.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roy.devil.R;
import com.roy.devil.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>
        implements View.OnClickListener{

    private Context mContext;
    private List<HomeModel> mList = new ArrayList<>();

    public HomeAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<HomeModel> list) {
        mList.clear();
        if(list!=null && !list.isEmpty()) {
            this.mList = list;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        HomeModel model = mList.get(position);
        holder.tvName.setText(model.showName);
        holder.itemView.setTag(model);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if(v.getTag() instanceof HomeModel) {
            HomeModel model = (HomeModel) v.getTag();
            Intent intent = new Intent(mContext, model.cls);
            mContext.startActivity(intent);
        }
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public HomeViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
