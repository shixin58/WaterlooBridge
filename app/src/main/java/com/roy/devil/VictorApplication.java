package com.roy.devil;

import android.app.Application;

import com.max.baselib.PreferenceUtils;
import com.max.baselib.ResUtils;

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

        ResUtils.setContext(this);
        PreferenceUtils.initialize(this, "victor_utils");
    }
}
