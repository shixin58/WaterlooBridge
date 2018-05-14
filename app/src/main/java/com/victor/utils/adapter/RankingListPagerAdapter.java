package com.victor.utils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.victor.utils.fragment.RankingListFragment;

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
