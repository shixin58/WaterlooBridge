package com.victor.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.victor.utils.fragment.HomeFragment;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = HomeFragment.newInstance();
        fragmentTransaction.replace(R.id.container_fragment, homeFragment)
                .commitAllowingStateLoss();
    }
}
