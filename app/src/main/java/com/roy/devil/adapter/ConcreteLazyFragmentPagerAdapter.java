package com.roy.devil.adapter;

import android.view.ViewGroup;

import com.roy.devil.widget.LazyFragmentPagerAdapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * ViewPager懒加载
 */
public class ConcreteLazyFragmentPagerAdapter extends LazyFragmentPagerAdapter {

    private final List<Fragment> fragmentsList;

    public ConcreteLazyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    protected Fragment getItem(ViewGroup container, int position) {
        return fragmentsList.get(position);
    }
}
