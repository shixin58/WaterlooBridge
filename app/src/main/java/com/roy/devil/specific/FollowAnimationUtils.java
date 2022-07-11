package com.roy.devil.specific;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import com.bride.baselib.ResUtils;
import com.roy.devil.R;

/**
 * <p>Created by shixin on 2018/2/22.
 */
public class FollowAnimationUtils {
    private static final String TAG = FollowAnimationUtils.class.getSimpleName();

    private static final int WIDTH_DP_BUTTON_INVENTORY = 90;
    private static final int WIDTH_DP_BUTTON_FOLLOW = 50;
    private static final int MULTIPLE = 10;
    private static final float DENSITY = ResUtils.getDisplayMetrics().density;

    // 进入页面默认展开，1.2s后收起，收起动画持续0.3s
    // 页面上下滚动时收起
    public static void collapseInventory(View rootInventory, long startOffset) {
        final View buttonInventory = rootInventory.findViewById(R.id.button_inventory);
        if(buttonInventory.getVisibility()!= View.VISIBLE) {
            return;
        }
        // tween animation补间动画。作用于View，dispatchDraw来调整画布，不改变View的大小位置。
        TranslateAnimation collapseAnimation = new TranslateAnimation(0, WIDTH_DP_BUTTON_INVENTORY* DENSITY, 0, 0);
        collapseAnimation.setDuration(300* MULTIPLE);
        // 线性插值器，补间器。修改动画节奏，控制动画变化速率。
        collapseAnimation.setInterpolator(new LinearInterpolator());
        collapseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "onAnimationEnd");
                buttonInventory.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, "onAnimationRepeat");
            }
        });
        collapseAnimation.setStartOffset(startOffset*MULTIPLE);
        if(rootInventory.getAnimation()!=null) {
            rootInventory.getAnimation().setAnimationListener(null);
        }
        rootInventory.clearAnimation();
        rootInventory.startAnimation(collapseAnimation);
    }

    // 收起状态下，点击清单入口时展开，展开动画持续0.3s
    public static void expandInventory(View rootInventory) {
        final View buttonInventory = rootInventory.findViewById(R.id.button_inventory);
        if(buttonInventory.getVisibility()!= View.GONE){
            return;
        }
        TranslateAnimation expandAnimation = new TranslateAnimation(WIDTH_DP_BUTTON_INVENTORY* DENSITY, 0, 0, 0);
        expandAnimation.setDuration(300* MULTIPLE);
        expandAnimation.setInterpolator(new LinearInterpolator());
        rootInventory.clearAnimation();
        buttonInventory.setVisibility(View.VISIBLE);
        rootInventory.startAnimation(expandAnimation);
    }

    // 关注边框向左收缩
    public static void shrinkRoundRectangle(View followView) {
        final View viewShrinkRing = followView.findViewById(R.id.view_shrink_ring);
        viewShrinkRing.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, .48f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = viewShrinkRing.getLayoutParams();
                layoutParams.width = (int) (WIDTH_DP_BUTTON_FOLLOW* DENSITY *value);
                viewShrinkRing.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewShrinkRing.setVisibility(View.GONE);
                ViewGroup.LayoutParams layoutParams = viewShrinkRing.getLayoutParams();
                layoutParams.width = (int) (WIDTH_DP_BUTTON_FOLLOW* DENSITY);
                viewShrinkRing.setLayoutParams(layoutParams);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        valueAnimator.setDuration(100* MULTIPLE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    // 小球抛出
    public static void throwBall(View flFollow, View rootInventory, final View ivPoint) {
        // 小球起点
        int[] positionStart = new int[2];
        flFollow.getLocationInWindow(positionStart);
        Point startPoint = new Point(positionStart[0], (int) (positionStart[1]+24* DENSITY -10* DENSITY));
        // 小球终点
        int[] positionEnd = new int[2];
        rootInventory.getLocationInWindow(positionEnd);
        boolean buttonInventoryVisible = rootInventory.findViewById(R.id.button_inventory).getVisibility()== View.VISIBLE;
        Point endPoint = new Point(positionEnd[0]
                -(int)(buttonInventoryVisible?0f:WIDTH_DP_BUTTON_INVENTORY* DENSITY)+(int) (15* DENSITY),
                positionEnd[1]+(int) (10* DENSITY));

        int pointX = (startPoint.x + endPoint.x) / 2;
        int pointY = startPoint.y - (int) (30* DENSITY);
        Point controlPoint = new Point(pointX, pointY);

        // 属性动画ValueAnimator对动画系统不识别的属性类型通过ofObject()创建，需要设置类型估值算法TypeEvaluator、并实现evaluate()。
        final ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(controlPoint), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // 更新小蓝点位置
                View parentView = (View) ivPoint.getParent();
                int[] parentPosition = new int[2];
                parentView.getLocationInWindow(parentPosition);

                Point point = (Point) animator.getAnimatedValue();
                ivPoint.setX(point.x - parentPosition[0]);
                ivPoint.setY(point.y - parentPosition[1]);
                ivPoint.invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ivPoint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivPoint.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        valueAnimator.setStartDelay(100L * MULTIPLE);
        valueAnimator.setDuration(200L * MULTIPLE);
        // 默认AccelerateDecelerateInterpolator
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }
}
