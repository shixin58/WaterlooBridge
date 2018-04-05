package com.victor.utils.repository;

import com.victor.utils.R;
import com.victor.utils.ResUtils;
import com.victor.utils.activities.CustomWidgetActivity;
import com.victor.utils.activities.DrawSmallIconActivity;
import com.victor.utils.activities.FollowAnimationActivity;
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
        return list;
    }
}
