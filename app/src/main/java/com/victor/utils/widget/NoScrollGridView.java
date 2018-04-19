package com.victor.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 高度WRAP_CONTENT下，解决GridView跟ScrollView滚动冲突的bug
 */
public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expendMeasure = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expendMeasure);
    }
}
