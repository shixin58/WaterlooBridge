package com.roy.devil.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bride.ui_lib.BaseFragment;
import com.roy.devil.R;
import com.roy.devil.adapter.HomeAdapter;
import com.roy.devil.repository.HomeRepository;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class HomeFragment extends BaseFragment {

    private HomeAdapter mHomeAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        assembleData();
    }

    private void initView() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeAdapter = new HomeAdapter(getActivity());
        recyclerView.setAdapter(mHomeAdapter);
    }

    private void assembleData() {
        mHomeAdapter.setList(HomeRepository.getAllModels());
    }
}
