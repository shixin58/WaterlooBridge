package com.victor.utils.repository;

import com.victor.utils.R;
import com.victor.utils.ResUtils;
import com.victor.utils.activities.CustomWidgetActivity;
import com.victor.utils.activities.DrawSmallIconActivity;
import com.victor.utils.activities.FollowAnimationActivity;
import com.victor.utils.activities.PopupLayerActivity;
import com.victor.utils.activities.ViewPagerActivity;
import com.victor.utils.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class HomeRepository {

    public static List<HomeModel> getAllModels() {
        List<HomeModel> list = new ArrayList<>();
        HomeModel homeModel = new HomeModel(FollowAnimationActivity.class, ResUtils.getString(R.string.follow_animation));
        list.add(homeModel);
        HomeModel homeModel1 = new HomeModel(DrawSmallIconActivity.class, ResUtils.getString(R.string.draw_small_icon));
        list.add(homeModel1);
        HomeModel homeModel2 = new HomeModel(CustomWidgetActivity.class, ResUtils.getString(R.string.custom_widget));
        list.add(homeModel2);
        HomeModel homeModel3 = new HomeModel(PopupLayerActivity.class, ResUtils.getString(R.string.popup_layer));
        list.add(homeModel3);
        HomeModel homeModel4 = new HomeModel(ViewPagerActivity.class, ResUtils.getString(R.string.view_pager));
        list.add(homeModel4);
        return list;
    }
}
