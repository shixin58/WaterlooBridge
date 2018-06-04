package com.victor.utils.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.utils.R;
import com.victor.utils.widget.LazyFragmentPagerAdapter;

/**
 * <p>Created by shixin on 2018/6/4.
 */
public class LazyFragment extends Fragment implements LazyFragmentPagerAdapter.Deferrable {

    private int mPosition;

    public static LazyFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        LazyFragment fragment = new LazyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lazy, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mPosition = getArguments().getInt("position");
    }

    private void initView() {
        TextView tvPosition = getView().findViewById(R.id.tv_position);
        tvPosition.setText("No."+mPosition);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("Max", mPosition+" setUserVisibleHint-"+isVisibleToUser);
    }
}
