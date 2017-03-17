package com.wmj.newzhihu.mvp.model;

/**
 * Created by wmj on 2017-3-9.
 */

public interface IHomeModel {
    /**
     * 获取今日新闻及头部viewpager图像
     */
    void loadLastNews();

    /**
     * 获取往日新闻
     */
    void loadBeforeNews();

}
