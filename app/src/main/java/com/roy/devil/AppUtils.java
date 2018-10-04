package com.roy.devil;

import android.content.Context;

/**
 * <p>Created by shixin on 2018/4/7.
 */
public class AppUtils {

    public static String getVersionName() {
        Context context = VictorApplication.getInstance();
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getVersionCode() {
        Context context = VictorApplication.getInstance();
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
