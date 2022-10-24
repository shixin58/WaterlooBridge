package com.roy.devil.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.viewpager.widget.ViewPager;

import com.bride.ui_lib.BaseActivity;
import com.roy.devil.R;
import com.roy.devil.adapter.RankingList2PagerAdapter;
import com.roy.devil.specific.WidgetUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>Created by shixin on 2018/4/22.
 */
public class ViewPager2Activity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.horizontal_scrollview)
    HorizontalScrollView mHorizontalScrollView;
    @BindView(R.id.horizontal_radio_group)
    RadioGroup mRadioGroup;
    @BindViews({R.id.btn00, R.id.btn01, R.id.btn02, R.id.btn03, R.id.btn04, R.id.btn05, R.id.btn06, R.id.btn07})
    List<RadioButton> mRadioButtons;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        RankingList2PagerAdapter pagerAdapter = new RankingList2PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @OnClick({R.id.btn00, R.id.btn01, R.id.btn02, R.id.btn03, R.id.btn04, R.id.btn05, R.id.btn06, R.id.btn07}) void onTabClick(View view) {
        WidgetUtils.controlTabsPosition(mHorizontalScrollView, mRadioButtons, mRadioButtons.indexOf(view));
        mViewPager.setCurrentItem(mRadioButtons.indexOf(view), true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mRadioGroup.check(mRadioButtons.get(position).getId());
        WidgetUtils.controlTabsPosition(mHorizontalScrollView, mRadioButtons, position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
