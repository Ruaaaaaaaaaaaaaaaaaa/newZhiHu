package com.wmj.newzhihu.mvp.presenter;

import com.wmj.newzhihu.mvp.model.HomeModelImpl;
import com.wmj.newzhihu.mvp.model.IHomeModel;
import com.wmj.newzhihu.mvp.view.IHomeView;

/**
 * Created by wmj on 2017-3-9.
 */

public class HomePresenterImpl implements IHomePresenter,HomeModelImpl.OnLoadNewsListener{
    private static final String TAG = "HomePresenterImpl";
    private IHomeView mIHomeView;
    private IHomeModel mIHomeModel;
    public HomePresenterImpl(IHomeView homeview){
        this.mIHomeView = homeview;
        mIHomeModel = new HomeModelImpl();
    }



    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void loadMore() {

    }
}
