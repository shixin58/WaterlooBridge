package com.victor.utils.specific;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.victor.utils.ResUtils;

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
}
