package com.roy.devil;

import android.app.Application;

import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;
import com.squareup.leakcanary.LeakCanary;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        mApplication = this;

        ResUtils.setContext(this);

        PreferenceUtils.initialize(this, "victor_prefs");
    }
}
