package com.wmj.newzhihu.mvp.view;

import com.wmj.newzhihu.bean.BeforeNewsBean;
import com.wmj.newzhihu.bean.LastNews;
import com.wmj.newzhihu.bean.Stories;
import com.wmj.newzhihu.bean.TopStories;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by wmj on 2017-3-9.
 */

public interface IHomeView {
    void showProgress();
    void hideProgress();
    void changeLastNews(LastNews lastNews);
    void changeLastNewsFail();
    void changeBeforeNews(BeforeNewsBean beforeNewsBean);
    void changeBeforeNewsFail();
}
