package com.victor.utils.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.max.baselib.BaseFragment;
import com.victor.utils.R;
import com.victor.utils.widget.LazyFragmentPagerAdapter;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>Created by shixin on 2018/6/4.
 */
public class LazyFragment extends BaseFragment implements LazyFragmentPagerAdapter.Deferrable {

    private static final String TAG_BLANK = "tag_blank";

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
        View view = inflater.inflate(R.layout.fragment_lazy, container, false);
        ButterKnife.bind(this, view);
        return view;
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

    @OnClick(R.id.tv_position) void onPositionClick() {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(TAG_BLANK);
        if(fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitNow();
        }

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        BlankFragment blankFragment = BlankFragment.newInstance();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.fragment_container, blankFragment, TAG_BLANK).addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}
