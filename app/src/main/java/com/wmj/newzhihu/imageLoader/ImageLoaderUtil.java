package com.wmj.newzhihu.imageLoader;

import android.content.Context;

/**
 * Created by wmj on 2017-3-14.
 */

public class ImageLoaderUtil {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private  static ImageLoaderUtil mInstance;
    private BaseImageLoaderStrategy mStrategy;

    private ImageLoaderUtil(){
        mStrategy = new GlideImageLoaderStrategy();
    }

    public  static  ImageLoaderUtil getInstance(){
        if(mInstance==null)
            synchronized(ImageLoaderUtil.class){
                if(mInstance==null){
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        return  mInstance;
    }

    public void loadImage(Context context, ImageLoader img){
        mStrategy.loadImage(context,img);
    }

    public void setLoadImgStrategy(BaseImageLoaderStrategy stategy){
        mStrategy = stategy;
    }
}
