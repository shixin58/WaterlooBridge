package com.roy.devil.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.roy.devil.fragment.RankingListFragment;

/**
 * FragmentStatePagerAdapter会销毁Fragment实例，保存状态；重新创建实例，恢复状态。
 * <p>Created by shixin on 2018/4/22.
 */
public class RankingList2PagerAdapter extends FragmentStatePagerAdapter {

    public RankingList2PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return RankingListFragment.CAR_LEVELS.length;
    }

    @Override
    public Fragment getItem(int position) {
        return RankingListFragment.newInstance(RankingListFragment.CAR_LEVELS[position]);
    }
}
