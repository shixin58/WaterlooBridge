package com.roy.devil.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.roy.devil.R;
import com.roy.devil.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> implements View.OnClickListener,
        View.OnLongClickListener {
    private final Context mContext;
    private final List<HomeModel> mList = new ArrayList<>();

    public HomeAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<HomeModel> list) {
        mList.clear();
        if(list!=null && !list.isEmpty()) {
            this.mList.addAll(list);
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
        holder.itemView.setOnLongClickListener(this);
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

    @Override
    public boolean onLongClick(View v) {
        if (v.getTag() instanceof HomeModel) {
            HomeModel model = (HomeModel) v.getTag();
            if (!TextUtils.isEmpty(model.desc) || !TextUtils.isEmpty(model.showName)) {
                String text = !TextUtils.isEmpty(model.desc) ? model.desc : model.showName;
                Snackbar snackbar = Snackbar.make(v, text, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.undo, v1 -> {
                    Toast.makeText(v1.getContext(), "undo", Toast.LENGTH_SHORT).show();
                });
                snackbar.show();
                return true;
            }
        }
        return false;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public HomeViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
