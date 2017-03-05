package com.wmj.newzhihu.netUtils;

import android.content.Context;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONObject;


/**
 * Created by wmj on 2017-3-5.
 */

public abstract class VolleyInterface {
    private Context mContext;
    public static Listener<JSONObject> mListener;
    public static ErrorListener mErrorListener;

    public VolleyInterface(Context context, Listener<JSONObject> listener, ErrorListener errorListener) {
        this.mContext = context;
        this.mListener = listener;
        this.mErrorListener = errorListener;
    }

    public abstract void onMySuccess(JSONObject result);
    public abstract void onMyError(VolleyError error);

    public Listener<JSONObject> loadingListener() {
        mListener = new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onMySuccess(response);
            }
        };
        return mListener;
    }

    public ErrorListener errorListener() {
        mErrorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onMyError(error);
            }
        };
        return mErrorListener;

    }

}