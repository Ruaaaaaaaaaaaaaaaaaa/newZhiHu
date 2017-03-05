package com.wmj.newzhihu.bean;

/**
 * Created by wumingjun1 on 2017/2/16.
 */

public class TopStories {
    private String image;
    private String type;
    private String id;
    private String ga_prefix;
    private String title;

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TopStories{" +
                "ga_prefix='" + ga_prefix + '\'' +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
