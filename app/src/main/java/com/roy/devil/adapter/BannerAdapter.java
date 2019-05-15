package com.roy.devil.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.roy.devil.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * <p>Created by shixin on 2019/3/30.
 */
public class BannerAdapter extends PagerAdapter {
    private List<Integer> mList = new ArrayList<>();
    private SparseArray<ImageView> mImageViews = new SparseArray<>();

    public void setList(List<Integer> list) {
        if (list != null && !list.isEmpty()) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        position = position % mList.size();
        ImageView imageView = mImageViews.get(position);
        if (imageView == null) {
            imageView = (ImageView) LayoutInflater.from(container.getContext())
                    .inflate(R.layout.banner_item, container, false);
            mImageViews.put(position, imageView);
            Glide.get(container.getContext())
                    .getRequestManagerRetriever()
                    .get(container.getContext())
                    .asDrawable()
                    .load(mList.get(position))
                    .into(imageView);
            /*Glide.with(container.getContext())
                    .load(mList.get(position))
                    .into(imageView);*/
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
