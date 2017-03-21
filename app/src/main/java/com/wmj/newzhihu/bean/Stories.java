package com.wmj.newzhihu.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wumingjun1 on 2017/2/16.
 */

public class Stories extends DataSupport implements Serializable{
    private List<String> images;
    private String type;

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    @SerializedName("id")
    private String storyId;
    private String ga_prefix;
    private String title;
    private boolean multipic;

    private int themeid;

    public int getThemeid() {
        return themeid;
    }

    public void setThemeid(int themeid) {
        this.themeid = themeid;
    }

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
                ", id='" + storyId + '\'' +
                ", title='" + title + '\'' +
                ", multipic=" + multipic +
                '}';
    }
}
