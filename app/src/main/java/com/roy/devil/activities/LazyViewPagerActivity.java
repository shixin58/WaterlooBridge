package com.roy.devil.activities;

import android.os.Bundle;

import com.bride.ui_lib.BaseActivity;
import com.roy.devil.adapter.ConcreteLazyFragmentPagerAdapter;
import com.roy.devil.databinding.ActivityLazyViewPagerBinding;
import com.roy.devil.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * 懒加载
 * <p>Created by shixin on 2018/6/4.
 */
public class LazyViewPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ActivityLazyViewPagerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLazyViewPagerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initView();
    }

    private void initView() {
        List<Fragment> fragmentList = new ArrayList<>();
        LazyFragment fragment = LazyFragment.newInstance(0);
        fragmentList.add(fragment);
        LazyFragment fragment1 = LazyFragment.newInstance(1);
        fragmentList.add(fragment1);
        LazyFragment fragment2 = LazyFragment.newInstance(2);
        fragmentList.add(fragment2);
        LazyFragment fragment3 = LazyFragment.newInstance(3);
        fragmentList.add(fragment3);
        mBinding.viewPager.setOffscreenPageLimit(1);
        mBinding.viewPager.setAdapter(new ConcreteLazyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mBinding.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}
}
