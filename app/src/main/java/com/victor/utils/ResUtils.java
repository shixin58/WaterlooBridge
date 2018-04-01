package com.victor.utils;

import android.util.DisplayMetrics;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class ResUtils {

    public static String getString(int id) {
        return VictorApplication.getInstance().getString(id);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return VictorApplication.getInstance().getResources().getDisplayMetrics();
    }
}
