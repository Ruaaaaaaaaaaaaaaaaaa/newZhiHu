package com.wmj.newzhihu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wumingjun1 on 2017/2/16.
 */

public class Stories implements Serializable{
    private List<String> images;
    private String type;
    private String id;
    private String ga_prefix;
    private String title;
    private boolean multipic;

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    /**
     *  1.  有图有文字
     *  2.  有图无文字
     *  3.  日期
     *  4.  主编图像
     */
    private int dateType = -1;

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
        return "Stories{" +
                "ga_prefix='" + ga_prefix + '\'' +
                ", images=" + images +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", multipic=" + multipic +
                '}';
    }
}
