package com.roy.devil.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.roy.devil.R;
import com.roy.devil.adapter.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * <p>Created by shixin on 2018/4/5.
 */
public class CustomWidgetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);
        initView();
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.banner);
        BannerAdapter bannerAdapter = new BannerAdapter();
        viewPager.setAdapter(bannerAdapter);

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.desert);
        list.add(R.drawable.mojave);
        list.add(R.drawable.sierra);
        list.add(R.drawable.sailboat_race);
        bannerAdapter.setList(list);
    }

    public void clickElevation(View v) {
        Toast.makeText(this, "clickElevation", Toast.LENGTH_SHORT).show();
    }
}
