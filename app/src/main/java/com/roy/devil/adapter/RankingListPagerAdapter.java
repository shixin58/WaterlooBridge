package com.roy.devil.adapter;

import android.view.ViewGroup;

import com.roy.devil.fragment.RankingListFragment;
import com.roy.devil.widget.LazyFragmentPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * <p>Created by shixin on 2018/4/22.
 */
public class RankingListPagerAdapter extends LazyFragmentPagerAdapter {

    public RankingListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return RankingListFragment.CAR_LEVELS.length;
    }

    @Override
    protected Fragment getItem(ViewGroup container, int position) {
        return RankingListFragment.newInstance(RankingListFragment.CAR_LEVELS[position]);
    }
}
