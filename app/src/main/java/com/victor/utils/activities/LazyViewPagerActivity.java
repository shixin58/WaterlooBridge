package com.victor.utils.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.victor.utils.R;
import com.victor.utils.adapter.ConcreteLazyFragmentPagerAdapter;
import com.victor.utils.fragment.LazyFragment;
import com.victor.utils.widget.LazyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 懒加载
 * <p>Created by shixin on 2018/6/4.
 */
public class LazyViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    LazyViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_view_pager);
        ButterKnife.bind(this);
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
        mViewPager.setAdapter(new ConcreteLazyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
