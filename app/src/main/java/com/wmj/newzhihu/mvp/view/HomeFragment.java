package com.wmj.newzhihu.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wmj.newzhihu.R;
import com.wmj.newzhihu.bean.BeforeNewsBean;
import com.wmj.newzhihu.bean.LastNews;
import com.wmj.newzhihu.bean.Stories;
import com.wmj.newzhihu.bean.TopStories;
import com.wmj.newzhihu.mvp.presenter.HomePresenterImpl;
import com.wmj.newzhihu.mvp.presenter.IHomePresenter;

import java.util.List;

/**
 * Created by wmj on 2017-3-9.
 */

public class HomeFragment extends Fragment implements IHomeView{
    private static final String TAG = "HomeFragment";
    private IHomePresenter mIHomePresenter;
    private RecyclerView mRecyclerView;
    private ViewPager mViewPager;
    private LinearLayout ll;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NestedScrollView mNestedScollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIHomePresenter = new HomePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponent(view);
        initListener();
        initAdapter();
        return view;
    }

    private void initAdapter() {
    }

    private void initComponent(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
        ll = (LinearLayout) view.findViewById(R.id.LLcircles);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mNestedScollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(mOnrefreshListener);
        mNestedScollView.setOnScrollChangeListener(mOnScrollChangetListener);
    }
    private SwipeRefreshLayout.OnRefreshListener mOnrefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mIHomePresenter.refreshNews();
        }
    };
    private NestedScrollView.OnScrollChangeListener mOnScrollChangetListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if(v.getChildAt(0).getHeight()-v.getHeight()==v.getScrollY()){
                mIHomePresenter.loadMore();
            }
        }
    };

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void changeLastNews(LastNews lastNews) {

    }

    @Override
    public void changeLastNewsFail() {

    }

    @Override
    public void changeBeforeNews(BeforeNewsBean beforeNewsBean) {

    }

    @Override
    public void changeBeforeNewsFail() {

    }


}
