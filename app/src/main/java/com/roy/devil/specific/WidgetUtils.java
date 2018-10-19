package com.roy.devil.specific;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;
import com.roy.devil.AppUtils;
import com.roy.devil.PreferenceFinals;
import com.roy.devil.R;

import java.util.List;

/**
 * <p>Created by shixin on 2018/4/5.
 */
public class WidgetUtils {

    /**
     * 选中某tab后tab栏滑动到适当位置
     * @param horizontalScrollView 可左右滚动的tab栏
     * @param buttons 所有tab
     * @param index 当前选中tab位置
     */
    public static void controlTabsPosition(final HorizontalScrollView horizontalScrollView,
                                           List<? extends View> buttons, final int index) {
        final int size = buttons.size();
        final View currentTab = buttons.get(index);
        final View leftTab = index>0?buttons.get(index-1):null;
        final View rightTab = (index<size-1)?buttons.get(index+1):null;
        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                int width = ResUtils.getDisplayMetrics().widthPixels;
                Rect globalVisibleRect = new Rect();
                Point globalOffset = new Point();
                boolean nonEmpty = currentTab.getGlobalVisibleRect(globalVisibleRect, globalOffset);
                if(nonEmpty){
                    // 当前tab可见
                    if(globalVisibleRect.right==width && globalVisibleRect.left+ currentTab.getWidth()>width){
                        // 在右侧且显示不全
                        horizontalScrollView.smoothScrollTo((int) (currentTab.getX()+ currentTab.getWidth()-width)
                                +(rightTab!=null?rightTab.getWidth()/2:0), 0);
                    }else if(globalVisibleRect.left==0 && globalVisibleRect.right< currentTab.getWidth()){
                        // 在左侧且显示不全
                        horizontalScrollView.smoothScrollTo((int) currentTab.getX()
                                -(leftTab!=null?leftTab.getWidth()/2:0), 0);
                    }else {
                        if(leftTab!=null && globalVisibleRect.left<leftTab.getWidth()/2){
                            // 左侧tab不可见时让其一半可见
                            horizontalScrollView.smoothScrollTo((int) currentTab.getX()
                                    -leftTab.getWidth()/2, 0);
                        }else if(rightTab!=null && globalVisibleRect.right>width-rightTab.getWidth()/2){
                            // 右侧tab不可见时让其一半可见
                            horizontalScrollView.smoothScrollTo((int) (currentTab.getX()+ currentTab.getWidth()-width)
                                    +rightTab.getWidth()/2, 0);
                        }
                    }
                }else {
                    // 当前tab不可见
                    if(horizontalScrollView.getScrollX() >= currentTab.getX()+ currentTab.getWidth()){
                        // 在左侧
                        horizontalScrollView.smoothScrollTo((int) currentTab.getX()
                                -(leftTab!=null?leftTab.getWidth()/2:0), 0);
                    }else {
                        // 在右侧
                        horizontalScrollView.smoothScrollTo((int) (currentTab.getX()+ currentTab.getWidth()-width)
                                +(rightTab!=null?rightTab.getWidth()/2:0), 0);
                    }
                }
            }
        });
    }

    /**
     * 综述页加入清单提示，安装或升级到v9.0显示一次
     */
    public static PopupWindow initSerialFollowTip(Context context, final View parent) {
        if(AppUtils.getVersionCode()==1
                &&!PreferenceUtils.getBoolean(PreferenceFinals.SERIAL_SUMMARY_INVENTORY_TIP)) {
            final View popupView = LayoutInflater.from(context).inflate(R.layout.view_serial_follow_popup_window, null);
            ImageView ivArrow = (ImageView) popupView.findViewById(R.id.arrow);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ivArrow.getLayoutParams();
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.UNSET;
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.rightMargin = ResUtils.dp2px(45) - layoutParams.width/2;
            ivArrow.setLayoutParams(layoutParams);
            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            mPopupWindow.setFocusable(true);
//            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            parent.post(new Runnable() {
                @Override
                public void run() {
                    int[] titleViewLocation = new int[2];
                    parent.getLocationOnScreen(titleViewLocation);
                    popupWindow.showAtLocation(parent, Gravity.RIGHT|Gravity.TOP, ResUtils.dp2px(15),
                            titleViewLocation[1]+parent.getHeight()-ResUtils.dp2px(6));
                }
            });
            popupView.findViewById(R.id.ivHide).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    PreferenceUtils.putBoolean(PreferenceFinals.SERIAL_SUMMARY_INVENTORY_TIP, true);
                    PreferenceUtils.commit();
                }
            });
            return popupWindow;
        }
        return null;
    }

    /**
     * 选车首页清单入口提示，安装或升级到v9.0显示一次
     */
    public static PopupWindow initInventoryTip(Context context, final View anchor) {
        if (AppUtils.getVersionCode()==1
                &&!PreferenceUtils.getBoolean(PreferenceFinals.SELECT_CAR_HOME_INVENTORY_TIP)) {
            final View popupView = LayoutInflater.from(context).inflate(R.layout.view_serial_follow_popup_window, null);
            TextView tvSlogan = (TextView) popupView.findViewById(R.id.tvSlogan);
            tvSlogan.setText(R.string.select_car_inventory_entry_guide);
            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // 点popupWindow外面消失
//            popupWindow.setFocusable(true);
//            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            anchor.post(new Runnable() {
                @Override
                public void run() {
                    popupWindow.showAsDropDown(anchor, 0, -ResUtils.dp2px(6));
                }
            });
            popupView.findViewById(R.id.ivHide).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    PreferenceUtils.putBoolean(PreferenceFinals.SELECT_CAR_HOME_INVENTORY_TIP, true);
                    PreferenceUtils.commit();
                }
            });
            return popupWindow;
        }
        return null;
    }
}
