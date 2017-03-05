package com.wmj.newzhihu.bean;

import java.util.List;

/**
 * Created by wumingjun1 on 2017/2/16.
 */

public class BeforeNewsBean {
    private String date;
    private List<Stories> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Stories> getStories() {
        return stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }

    @Override
    public String toString() {
        return "BeforeNews{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                '}';
    }
}
