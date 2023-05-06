package com.roy.devil.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.viewpager.widget.ViewPager;

import com.bride.ui_lib.BaseActivity;
import com.roy.devil.adapter.RankingList2PagerAdapter;
import com.roy.devil.databinding.ActivityViewPagerBinding;
import com.roy.devil.specific.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/4/22.
 */
public class ViewPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ActivityViewPagerBinding mBinding;

    List<RadioButton> mRadioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityViewPagerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initView();
    }

    private void initView() {
        RankingList2PagerAdapter pagerAdapter = new RankingList2PagerAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(pagerAdapter);
        mBinding.viewPager.addOnPageChangeListener(this);

        mRadioButtons = new ArrayList<>(8);
        mRadioButtons.add(mBinding.horizontalScrollview.btn00);
        mRadioButtons.add(mBinding.horizontalScrollview.btn01);
        mRadioButtons.add(mBinding.horizontalScrollview.btn02);
        mRadioButtons.add(mBinding.horizontalScrollview.btn03);
        mRadioButtons.add(mBinding.horizontalScrollview.btn04);
        mRadioButtons.add(mBinding.horizontalScrollview.btn05);
        mRadioButtons.add(mBinding.horizontalScrollview.btn06);
        mRadioButtons.add(mBinding.horizontalScrollview.btn07);
        for (View view : mRadioButtons) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        WidgetUtils.controlTabsPosition(mBinding.horizontalScrollview.getRoot(), mRadioButtons, mRadioButtons.indexOf(view));
        mBinding.viewPager.setCurrentItem(mRadioButtons.indexOf(view), true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        mBinding.horizontalScrollview.horizontalRadioGroup.check(mRadioButtons.get(position).getId());
        WidgetUtils.controlTabsPosition(mBinding.horizontalScrollview.getRoot(), mRadioButtons, position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
