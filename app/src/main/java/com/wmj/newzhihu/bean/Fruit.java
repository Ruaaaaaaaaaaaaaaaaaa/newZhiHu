package com.wmj.newzhihu.bean;

import java.util.List;

/**
 * Created by wumingjun1 on 2017/2/15.
 */

public class Fruit {
    private String name;
    private List<String> imageId;

    public Fruit(String name ,List<String> imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(List<String> imageId) {
        this.imageId = imageId;
    }

    public List<String> getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
