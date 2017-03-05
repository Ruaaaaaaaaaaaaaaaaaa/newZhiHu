package com.wmj.newzhihu.netUtils;

/**
 * Created by wmj on 2017-3-5.
 */

public class HttpPath {
    /**
     * 最新消息
     */
    public static final String LASTEST_NEWS = "http://news-at.zhihu.com/api/4/news/latest";

    /**
     * 消息内容获取与离线下载
     * 使用在 最新消息 中获得的 id，拼接在 http://news-at.zhihu.com/api/4/news/ 后，得到对应消息 JSON 格式的内容
     */
    public static final String NEWS_CONTENT = "http://news-at.zhihu.com/api/4/news/";

    /**
     * 过往消息
     * 若果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
     * 知乎日报的生日为 2013 年 5 月 19 日，若 before 后数字小于 20130520 ，只会接收到空消息
     * 输入的今日之后的日期仍然获得今日内容，但是格式不同于最新消息的 JSON 格式
     */
    public static final String BEFORE_NEWS = "http://news-at.zhihu.com/api/4/news/before/";

    /**
     * 主题日报列表
     */
    public static final String THEME_LISS = "http://news-at.zhihu.com/api/4/themes";

    /**
     * 主题日报内容
     */
    public static final String THEME_CONTENT = "http://news-at.zhihu.com/api/4/theme/";



}
