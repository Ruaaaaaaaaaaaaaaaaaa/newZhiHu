package com.wmj.newzhihu.bean;

import java.util.List;

/**
 * Created by wumingjun1 on 2017/2/16.
 */

public class LastNews {
    private String date;
    private List<Stories> stories;
    private List<TopStories> top_stories;

    @Override
    public String toString() {
        return "LastNews{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }

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

    public List<TopStories> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStories> top_stories) {
        this.top_stories = top_stories;
    }
}
