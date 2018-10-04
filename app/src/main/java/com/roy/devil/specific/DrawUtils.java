package com.roy.devil.specific;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.max.baselib.ResUtils;
import com.roy.devil.VictorApplication;

/**
 * <p>Created by shixin on 2018/3/23.
 */
public class DrawUtils {

    public static void drawTag(Context context, View view, String text){

        if(TextUtils.isEmpty(text)){
            return;
        }

        Bitmap bitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint trapezoidPaint = new Paint();
        trapezoidPaint.setColor(Color.parseColor("#FF7275"));
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(80, 80);
        path.lineTo(80, 40);
        path.lineTo(40, 0);
        path.close();
        canvas.drawPath(path, trapezoidPaint);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(24);
        paint.setAntiAlias(true);
        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);
        canvas.rotate(45, 40, 40);
        canvas.drawText(text, 40-textRect.centerX(), 40-paint.descent(), paint);
        canvas.rotate(-45, 40, 40);

        view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
    }

    /**
     * 车型名称后追加带框文字
     * @param origin 车型名称
     * @param iconInfo 追加文字
     * @param bgColorId 追加文字边框颜色resId
     * @param txtColorId 追加文字颜色resId
     */
    public static void assembleText(SpannableStringBuilder origin, String iconInfo, int bgColorId, int txtColorId){
        if(origin==null){
            origin = new SpannableStringBuilder();//长度为0，icon可正常显示，避免空指针
        }
        if(TextUtils.isEmpty(iconInfo)){
            return;
        }
        // 背景框
        Paint bgPaint = new Paint();
        bgPaint.setColor(ResUtils.getColor(bgColorId));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(0);
        bgPaint.setDither(true);
//        bgPaint.setAntiAlias(true);
        // 文字
        Paint paint = new Paint();
        paint.setColor(ResUtils.getColor(txtColorId));
        paint.setTextSize(ResUtils.sp2px(10f));
        paint.setAntiAlias(true);
        Rect rect = new Rect();
        paint.getTextBounds(iconInfo, 0, iconInfo.length(), rect);

        int padding = ResUtils.dp2px(2f);
        int margin = ResUtils.dp2px(1f);
        int topDistance = ResUtils.dp2px(3f);
        int radius = ResUtils.dp2px(1f);
        int offset = ResUtils.dp2px(1f);
        Bitmap bitmap = Bitmap.createBitmap(rect.width()+(margin+padding)*2,
                rect.height()+(margin+padding)*2+topDistance, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(new Rect(margin, topDistance+margin,
                bitmap.getWidth()-margin, bitmap.getHeight()-margin), bgPaint);
        /*canvas.drawRoundRect(new RectF(margin, topDistance+margin,
                bitmap.getWidth()-margin, bitmap.getHeight()-margin), radius, radius, bgPaint);*/
        canvas.drawText(iconInfo, margin+padding, topDistance+margin+padding-paint.ascent()-offset, paint);

        String placeHolder = " face";
        try {
            origin.append(placeHolder);
            origin.setSpan(new ImageSpan(VictorApplication.getInstance(), bitmap, DynamicDrawableSpan.ALIGN_BASELINE),
                    origin.length()-placeHolder.length()+1, origin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
