package com.roy.devil.model;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class HomeModel {
    public Class<?> cls;
    public String showName;
    public String desc;

    public HomeModel(Class<?> cls, String showName) {
        this.cls = cls;
        this.showName = showName;
    }

    public HomeModel(Class<?> cls, String showName, String desc) {
        this.cls = cls;
        this.showName = showName;
        this.desc = desc;
    }
}
