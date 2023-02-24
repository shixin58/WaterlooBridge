package com.roy.devil.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bride.ui_lib.BaseFragment;
import com.roy.devil.R;
import com.roy.devil.databinding.FragmentLazyBinding;
import com.roy.devil.widget.LazyFragmentPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * <p>Created by shixin on 2018/6/4.
 */
public class LazyFragment extends BaseFragment implements LazyFragmentPagerAdapter.Deferrable,
        View.OnClickListener {
    private static final String TAG = LazyFragment.class.getSimpleName();

    private static final String TAG_BLANK = "tag_blank";

    private FragmentLazyBinding mBinding;

    private int mPosition = -1;

    public static LazyFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        LazyFragment fragment = new LazyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LazyFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPosition = getArguments().getInt("position", -1);
        Log.i(TAG, "onAttach "+mPosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate "+mPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentLazyBinding.inflate(inflater, container, false);
        Log.i(TAG, "onCreateView "+mPosition);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated "+mPosition);
        initData();
        initView();
    }

    private void initData() {}

    private void initView() {
        mBinding.tvPage.setText("No." + mPosition);
        mBinding.tvPage.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart "+mPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume "+mPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause "+mPosition);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop "+mPosition);
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView "+mPosition);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy "+mPosition);
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach "+mPosition);
        super.onDetach();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        Log.i(TAG, getArguments().getInt("position")+" setMenuVisibility - "+menuVisible);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, getArguments().getInt("position")+" setUserVisibleHint - "+isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, mPosition+" onHiddenChanged - "+hidden);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(TAG_BLANK+"_"+mPosition);
        if(fragment != null && !fragment.isRemoving()) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
            return;
        }

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        BlankFragment blankFragment = BlankFragment.newInstance();
        transaction.add(R.id.fragment_container, blankFragment, TAG_BLANK+"_"+mPosition);
        transaction.commitAllowingStateLoss();
    }
}
