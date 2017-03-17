package com.wmj.newzhihu.mvp.model;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wmj on 2017-3-9.
 */

public class HomeModelImpl implements IHomeModel{
    private static final String TAG = "HomeModelImpl";

    @Override
    public void loadLastNews() {
        rx.Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });

    }

    @Override
    public void loadBeforeNews() {

    }

    public  interface OnLoadNewsListener{
        void onSuccess();
        void onFailure();
    }
}
