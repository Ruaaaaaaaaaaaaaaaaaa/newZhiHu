package com.wmj.newzhihu.netUtils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by wmj on 2017-3-5.
 */

public class VolleyRequest {
    public static JsonObjectRequest jsonObjectRequest;
    public static Context context;
    public static void RequestGet(
            Context mcontext,String url,String tag,VolleyInterface vif){
        //防止重复请求，所以先取消tag标识的请求队列
        MyApplication.getHttpQueues().cancelAll(tag);

        jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url,
                null,
                vif.loadingListener(),
                vif.errorListener()
        );
        jsonObjectRequest.setTag(tag);
        MyApplication.getHttpQueues().add(jsonObjectRequest);

        //注意千万不要调用start来开启。这样写是不对的。
        //因为在源码里，当我们调用Volley.newRequestQueue()来实例化一个请求队列的时候
        //就已经使用queue.start(); 方法来开启了一个工作线程，所以我们如果多次调用
        //newRequestQueue来实例化请求队列就会多次调用start方法，这样做势必增加性能的消耗
        //所以我们一定要把volley的请求队列全局化（可以使用单例模式或在application初始化）。
        //并且我们不应当手动调用start。
//      MyApplication.getHttpQueues().start();
    }
//    public static void RequestPost(Context context,String url,String tag,final Map<String, String> params,
//                                   VolleyInterface vif){
//        MyApplication.getHttpQueues().cancelAll(tag);
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,null, vif.loadingListener(), vif.errorListener()){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return params;
//            }
//        };
//        jsonObjectRequest.setTag(tag);
//        MyApplication.getHttpQueues().add(jsonObjectRequest);
//    }

}