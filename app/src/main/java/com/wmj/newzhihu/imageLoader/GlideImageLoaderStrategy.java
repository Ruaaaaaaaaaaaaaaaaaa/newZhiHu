package com.wmj.newzhihu.imageLoader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.wmj.newzhihu.utils.AppUtils;
import com.wmj.newzhihu.utils.SettingsUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wmj on 2017-3-14.
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {
    @Override
    public void loadImage(Context context, ImageLoader img) {
        // TODO: 2017-3-14  
        boolean flag = SettingsUtil.getOnlyWifiLoadImg(context);
        //如果不是在wifi下加载图片，直接加载
        if(!flag){
            loadNormal(context,img);
            return ;
        }
        int strategy = img.getWifiStrategy();
        if(strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI){
            int netType = AppUtils.getNetWorkType(context.getApplicationContext());
            //如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
            if(netType == AppUtils.NETWORKTYPE_WIFI){
                loadNormal(context,img);
            }else{
                //如果是在wifi下才加载图片，并且当前网络不是wifi，加载缓存
                loadCache(context,img);

            }
        }else{
            //如果不是在wifi下才加载图片 ps就是正常加载
            loadNormal(context,img);
        }
    }

    private void loadNormal(Context context, ImageLoader img) {
        Glide.with(context).load(img.getUrl()).placeholder(img.getPlaceHolder()).into(img.getImageView());
    }

    private void loadCache(Context context, ImageLoader img) {
        Glide.with(context).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return model;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(img.getUrl()).placeholder(img.getPlaceHolder()).diskCacheStrategy(DiskCacheStrategy.ALL).into(img.getImageView());
    }
}
