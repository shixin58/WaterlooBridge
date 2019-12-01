package com.roy.devil.repository;

import com.bride.baselib.ResUtils;
import com.roy.devil.R;
import com.roy.devil.activities.CustomWidgetActivity;
import com.roy.devil.activities.DrawSmallIconActivity;
import com.roy.devil.activities.FollowAnimationActivity;
import com.roy.devil.activities.LazyViewPagerActivity;
import com.roy.devil.activities.MusicActivity;
import com.roy.devil.activities.PopupLayerActivity;
import com.roy.devil.activities.ViewPager2Activity;
import com.roy.devil.activities.ViewPagerActivity;
import com.roy.devil.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class HomeRepository {

    public static List<HomeModel> getAllModels() {
        List<HomeModel> list = new ArrayList<>();
        HomeModel homeModel = new HomeModel(FollowAnimationActivity.class,
                ResUtils.getString(R.string.follow_animation), ResUtils.getString(R.string.desc_follow_animation));
        list.add(homeModel);
        HomeModel homeModel1 = new HomeModel(DrawSmallIconActivity.class, ResUtils.getString(R.string.draw_small_icon));
        list.add(homeModel1);
        HomeModel homeModel2 = new HomeModel(CustomWidgetActivity.class, ResUtils.getString(R.string.custom_widget));
        list.add(homeModel2);
        HomeModel homeModel3 = new HomeModel(PopupLayerActivity.class, ResUtils.getString(R.string.popup_layer));
        list.add(homeModel3);
        HomeModel homeModel4 = new HomeModel(ViewPagerActivity.class, ResUtils.getString(R.string.view_pager));
        list.add(homeModel4);
        HomeModel homeModel5 = new HomeModel(LazyViewPagerActivity.class, ResUtils.getString(R.string.lazy_view_pager));
        list.add(homeModel5);
        HomeModel homeModel6 = new HomeModel(MusicActivity.class, ResUtils.getString(R.string.music));
        list.add(homeModel6);
        HomeModel homeModel7 = new HomeModel(ViewPager2Activity.class, ResUtils.getString(R.string.view_pager_2));
        list.add(homeModel7);
        return list;
    }
}
