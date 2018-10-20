package com.roy.devil.adapter;

import com.roy.devil.fragment.RankingListFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * <p>Created by shixin on 2018/4/22.
 */
public class RankingListPagerAdapter extends FragmentPagerAdapter {

    public RankingListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return RankingListFragment.newInstance(RankingListFragment.CAR_LEVELS[position]);
    }

    @Override
    public int getCount() {
        return RankingListFragment.CAR_LEVELS.length;
    }
}
