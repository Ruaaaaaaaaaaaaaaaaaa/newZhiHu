package com.wmj.newzhihu.bean;

/**
 * Created by wmj on 2017-3-5.
 */

public class Editor{
    private String url;
    private String bio;
    private String id;
    private String avatar;
    private String name;

    @Override
    public String toString() {
        return "Editor{" +
                "avater='" + avatar + '\'' +
                ", url='" + url + '\'' +
                ", bio='" + bio + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getAvater() {
        return avatar;
    }

    public void setAvater(String avater) {
        this.avatar = avater;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
