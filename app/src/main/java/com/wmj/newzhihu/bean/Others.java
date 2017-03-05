package com.wmj.newzhihu.bean;

/**
 * Created by wumingjun1 on 2017/2/19.
 */

public class Others {
    private String color;
    private String thumbnail;
    private String description;
    private String id;
    private String name;

    @Override
    public String toString() {
        return "Others{" +
                "color='" + color + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
