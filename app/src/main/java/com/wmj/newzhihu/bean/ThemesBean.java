package com.wmj.newzhihu.bean;

import java.util.List;

/**
 * Created by wumingjun1 on 2017/2/19.
 */

public class ThemesBean {
    private String limit;
    private List<Others> subscribed;
    private List<Others> others;

    @Override
    public String toString() {
        return "themesBean{" +
                "limit='" + limit + '\'' +
                ", subscribed=" + subscribed +
                ", others=" + others +
                '}';
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public List<Others> getOthers() {
        return others;
    }

    public void setOthers(List<Others> others) {
        this.others = others;
    }

    public List<Others> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<Others> subscribed) {
        this.subscribed = subscribed;
    }
}
