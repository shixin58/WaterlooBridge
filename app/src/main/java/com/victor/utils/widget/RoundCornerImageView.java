package com.victor.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.victor.utils.R;

/**
 * <p>Created by shixin on 2018/4/3.
 */
public class RoundCornerImageView extends AppCompatImageView {

    private Path path = new Path();
    private RectF rectF = new RectF();
    private float[] radii = new float[8];
    private DrawFilter drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG);

    public RoundCornerImageView(Context context) {
        super(context);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView, 0, 0);
            float radius = a.getDimension(R.styleable.RoundCornerImageView_corner_radius, 0f);
            for(int i=0;i<radii.length;i++) {
                radii[i] = radius;
            }
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rectF.set(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rectF, radii, Path.Direction.CW);
        canvas.setDrawFilter(drawFilter);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
