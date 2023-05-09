package com.roy.devil.adapter;

import com.roy.devil.fragment.RankingListFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * <p>Created by shixin on 2018/4/22.
 */
public class RankingListPagerAdapter extends FragmentStateAdapter {

    public RankingListPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return RankingListFragment.newInstance(RankingListFragment.CAR_LEVELS[position]);
    }

    @Override
    public int getItemCount() {
        return RankingListFragment.CAR_LEVELS.length;
    }
}
