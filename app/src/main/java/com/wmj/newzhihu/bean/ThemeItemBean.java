package com.wmj.newzhihu.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wumingjun1 on 2017/2/20.
 */

public class ThemeItemBean extends DataSupport implements Serializable{

    private int themeid;

    public int getThemeid() {
        return themeid;
    }

    public void setThemeid(int themeid) {
        this.themeid = themeid;
    }

    private List<Stories> stories = new ArrayList<>();
    private String description;
    private String background;
    private String color;
    private String name;
    private String image;
    private List<Editor> editors = new ArrayList<>();
    private String image_source;

    @Override
    public String toString() {
        return "ThemeItemBean{" +
                "background='" + background + '\'' +
                ", stories=" + stories +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", editors=" + editors +
                ", image_source='" + image_source + '\'' +
                '}';
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
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

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stories> getStories() {
        return stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }


}
