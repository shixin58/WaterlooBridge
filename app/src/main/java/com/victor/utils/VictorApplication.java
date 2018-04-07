package com.victor.utils;

import android.app.Application;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class VictorApplication extends Application {
    private static VictorApplication mApplication;

    public static VictorApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        PreferenceUtils.initialize(this);
    }
}
