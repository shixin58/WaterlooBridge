package com.victor.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class ResUtils {

    public static String getString(int id) {
        return VictorApplication.getInstance().getString(id);
    }

    public static String[] getStringArray(int id) {
        return VictorApplication.getInstance().getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return VictorApplication.getInstance().getResources().getColor(id);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return VictorApplication.getInstance().getResources().getDisplayMetrics();
    }

    public static int dp2px(float dpValue) {
        float rs = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getDisplayMetrics());
        return (int) rs;
    }

    public static int sp2px(float spValue) {
        final float fontScale = getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
