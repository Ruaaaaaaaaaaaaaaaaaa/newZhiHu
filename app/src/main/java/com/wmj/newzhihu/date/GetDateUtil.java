package com.wmj.newzhihu.date;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wmj.newzhihu.bean.CacheJson;
import com.wmj.newzhihu.bean.Stories;
import com.wmj.newzhihu.bean.ThemeItemBean;
import com.wmj.newzhihu.imageLoader.ImageLoader;
import com.wmj.newzhihu.imageLoader.ImageLoaderUtil;
import com.wmj.newzhihu.netUtils.HttpUtils;
import com.wmj.newzhihu.netUtils.VolleyInterface;
import com.wmj.newzhihu.netUtils.VolleyRequest;
import com.wmj.newzhihu.utils.WmjUtils;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by wmj on 2017-3-20.
 */

public class GetDateUtil {
    public static  void getDate(final Context context, final String url,  final Handler handler){
        if(HttpUtils.isNetworkConnected(context)){
            getFromNet(context,url,handler);
        }else{
            getFromCache(context,url,handler);
        }
    }

    static void getFromNet(final Context context, final String url , final Handler handler){
        if(HttpUtils.isNetworkConnected( context)) {
            VolleyRequest.RequestGet(context, url, url,
                    new VolleyInterface(context, VolleyInterface.mListener, VolleyInterface.mErrorListener) {

                        @Override
                        public void onMySuccess(JSONObject result) {
                            String str =  String.valueOf(result);
                            Log.i("qqq",str);
                            CacheJson cacheJson = new CacheJson();
                            cacheJson.setUrl(url);
                            cacheJson.setJsonResponse(str);
                            cacheJson.saveOrUpdateAsync("url=?", "" + url).listen(new SaveCallback() {
                                @Override
                                public void onFinish(boolean success) {

                                }
                            });
                            Message msg = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("date",str);
                            msg.setData(bundle);
                            msg.what = 1000;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onMyError(VolleyError error) {
                            handler.sendEmptyMessage(1002);
                        }
                    });
        }
    }

    static void getFromCache(final Context context, final String url , final Handler handler){
        WmjUtils.showToast(context,"无网络 从缓存中获取");
        CacheJson cacheJson = DataSupport.where("url = ?",""+url).findLast(CacheJson.class);
        if(cacheJson!=null){
            Log.i("qqq","iop"+cacheJson.toString());
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("date",cacheJson.getJsonResponse());
            msg.setData(bundle);
            msg.what = 1000;
            handler.sendMessage(msg);
        }else{
            handler.sendEmptyMessage(1001);
        }
    }
}
