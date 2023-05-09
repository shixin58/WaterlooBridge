package com.roy.devil.activities;

import android.os.Bundle;

import com.bride.ui_lib.BaseActivity;
import com.google.android.material.tabs.TabLayoutMediator;
import com.roy.devil.adapter.RankingListPagerAdapter;
import com.roy.devil.databinding.ActivityViewPager2Binding;
import com.roy.devil.fragment.RankingListFragment;

/**
 * <p>Created by shixin on 2018/4/22.
 */
public class ViewPager2Activity extends BaseActivity {
    private ActivityViewPager2Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityViewPager2Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initView();
    }

    private void initView() {
        RankingListPagerAdapter pagerAdapter = new RankingListPagerAdapter(getSupportFragmentManager(), getLifecycle());
        mBinding.viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager, (tab, position) -> {
            tab.setText(RankingListFragment.CAR_LEVEL_NAMES.get(RankingListFragment.CAR_LEVELS[position]));
        }).attach();
    }
}
