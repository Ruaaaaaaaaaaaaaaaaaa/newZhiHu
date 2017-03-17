package com.wmj.newzhihu.imageLoader;

import android.widget.ImageView;

import com.wmj.newzhihu.R;

/**
 * Created by wmj on 2017-3-14.
 */

public class ImageLoader {
    private int type;  //类型（大图，中图，小图）
    private String url;
    private int placeHolder;
    private ImageView imageView;
    private int wifiStrategy;

    private ImageLoader(Builder builder){
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imageView = builder.imageView;
        this.wifiStrategy = builder.wifiStrategy;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public static class Builder{
        private int type;
        private String url;
        private int placeHolder;
        private ImageView imageView;
        private int wifiStrategy;

        public Builder(){
            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = "";
            this.placeHolder = R.drawable.ic_launcher;
            this.imageView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI;
        }

        public  Builder type(int type){
            this.type = type;
            return this;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder){
            this.placeHolder = placeHolder;
            return  this;
        }

        public Builder imageView(ImageView imageView){
            this.imageView = imageView;
            return this;
        }

        public  ImageLoader build(){
            return new ImageLoader(this);
        }
    }
}
