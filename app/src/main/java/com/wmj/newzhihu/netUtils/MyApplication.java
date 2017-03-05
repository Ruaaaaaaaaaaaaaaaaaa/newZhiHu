package com.wmj.newzhihu.netUtils;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by wmj on 2017-3-5.
 */

public class MyApplication extends Application {

    //建立一个请求队列
    public static RequestQueue queues;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        //实例化一个请求队列
        queues = Volley.newRequestQueue(getApplicationContext());
    }
    //建立方法返回请求队列
    public static RequestQueue getHttpQueues() {
        return queues;
    }
}
