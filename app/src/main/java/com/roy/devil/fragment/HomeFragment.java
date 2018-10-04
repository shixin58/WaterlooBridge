package com.roy.devil.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.max.baselib.BaseFragment;
import com.roy.devil.R;
import com.roy.devil.adapter.HomeAdapter;
import com.roy.devil.repository.HomeRepository;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class HomeFragment extends BaseFragment {

    private HomeAdapter mHomeAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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
