package com.roy.devil.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bride.baselib.BaseFragment;
import com.roy.devil.R;

/**
 * <p>Created by shixin on 2018/6/6.
 */
public class BlankFragment extends BaseFragment {

    public static BlankFragment newInstance() {

        Bundle args = new Bundle();

        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
}
