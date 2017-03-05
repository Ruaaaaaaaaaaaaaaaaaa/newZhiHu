package com.wmj.newzhihu.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.adapter.HomeAdapter;
import com.wmj.newzhihu.bean.BeforeNewsBean;
import com.wmj.newzhihu.bean.LastNews;
import com.wmj.newzhihu.bean.Stories;
import com.wmj.newzhihu.bean.TopStories;
import com.wmj.newzhihu.netUtils.HttpPath;
import com.wmj.newzhihu.netUtils.VolleyInterface;
import com.wmj.newzhihu.netUtils.VolleyRequest;
import com.wmj.newzhihu.utils.WmjUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.id.list;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeAdapter adapter;
    private List<BeforeNewsBean> mList;
    private LastNews mLastNews;
    private ViewPager mViewPager;
    private LinearLayout ll;
    private List<View> pageList;
    private PagerAdapter pagerAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int offset;
    private ImageView whiteCicle;
    private BeforeNewsBean mBeforeNewsBean;
    private NestedScrollView mNestedScollView;
    private int Scrolleight;
    private Calendar mCalendar;
    private String mDate;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what) {

                case 2:
                    if(pageList.size()!=0) {
                        mViewPager.setCurrentItem((mViewPager.getCurrentItem()+1 )% pageList.size());
                    }
                    mHandler.sendEmptyMessageDelayed(2,3000);
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponent(view);
        initDate();
        initListener();
        initViewPager();

        return view;
    }

    private void initDate() {

        mList = new ArrayList<>();
        adapter = new HomeAdapter(getContext(),mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
//        mCalendar = Calendar.getInstance();
//        StringBuffer sb = new StringBuffer();
//        sb.append(mCalendar.get(Calendar.YEAR));
//        if(mCalendar.get(Calendar.MONTH)+1<10){
//            sb.append(0).append(mCalendar.get(Calendar.MONTH)+1);
//        }
//        if(mCalendar.get(Calendar.DATE)<10){
//            sb.append(0).append(mCalendar.get(Calendar.DATE));
//        }

 //       mDate = sb.toString();

        volleyGetTopImage();
    }



    private void initComponent(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
        ll = (LinearLayout) view.findViewById(R.id.LLcircles);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mNestedScollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
    }

    private void initListener() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDate();
            }
        });
        mNestedScollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(0).getHeight()-v.getHeight()==v.getScrollY()){
                    volleyGet();
                }
            }
        });
    }

    private void refreshDate() {
        mList.clear();
        volleyGetTopImage();
        //// TODO: 2017-3-5
    }

    private void initViewPager() {
        pageList = new ArrayList<>();
        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(pageList.get(position));
                return pageList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(pageList.get(position));
            }
        };
        mViewPager.setAdapter(pagerAdapter);

        mHandler.sendEmptyMessage(2);

    }

    private void volleyGet() {

        String url = HttpPath.BEFORE_NEWS+ mDate;
        VolleyRequest.RequestGet(getContext(), url, "124",
                new VolleyInterface(getContext(), VolleyInterface.mListener, VolleyInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(JSONObject result) {
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        mBeforeNewsBean= gson.fromJson(String.valueOf(result), BeforeNewsBean.class);
                        mDate = mBeforeNewsBean.getDate();
                        mList.add(mBeforeNewsBean);
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                    }
                });
    }

    private void volleyGetTopImage() {
        String url = HttpPath.LASTEST_NEWS;
        VolleyRequest.RequestGet(getContext(), url, "TOP_IMAGE",
                new VolleyInterface(getContext(), VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                    
            @Override
            public void onMySuccess(JSONObject result) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                mLastNews= gson.fromJson(String.valueOf(result), LastNews.class);
                changeViewPager(mLastNews);
                mDate = mLastNews.getDate();
                BeforeNewsBean b = new BeforeNewsBean();
                b.setStories(mLastNews.getStories());
                b.setDate("今日新闻");
                mList.add(b);
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    private void changeViewPager(LastNews lastNews) {
        List<TopStories> topStories = lastNews.getTop_stories();
        ll.removeAllViews();
        for (int i = 0; i < topStories.size(); i++) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.viewpager_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.img);
            TextView tv = (TextView) view.findViewById(R.id.top_text);
            tv.setText(topStories.get(i).getTitle());
            Glide.with(getContext()).load(topStories.get(i).getImage()).
                    into(iv);
            ImageView circle = new ImageView(getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    WmjUtils.dp2px(getContext(), 10f)
                    , WmjUtils.dp2px(getContext(), 10f));
            circle.setLayoutParams(params);
            circle.setPadding(WmjUtils.dp2px(getContext(), 2f),
                    WmjUtils.dp2px(getContext(), 2f),
                    WmjUtils.dp2px(getContext(), 2f),
                    WmjUtils.dp2px(getContext(), 2f));
            circle.setImageDrawable(getResources().
                    getDrawable(R.drawable.ic_circle_grey, null));
            ll.addView(circle);
            pageList.add(view);
        }
        pagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(0);
        offset = 0;

        //oncreate 中获取不到view的宽高
        ll.post(new Runnable() {
            @Override
            public void run() {
                if (whiteCicle==null) {
                    whiteCicle = new ImageView(getContext());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            WmjUtils.dp2px(getContext(), 10f)
                            , WmjUtils.dp2px(getContext(), 10f));
                    params.leftMargin = ll.getLeft();
                    params.topMargin = ll.getTop();
                    whiteCicle.setLayoutParams(params);

                    whiteCicle.setPadding(WmjUtils.dp2px(getContext(), 2f),
                            WmjUtils.dp2px(getContext(), 2f),
                            WmjUtils.dp2px(getContext(), 2f),
                            WmjUtils.dp2px(getContext(), 2f));
                    whiteCicle.setImageDrawable(getResources().
                            getDrawable(R.drawable.ic_circle_white, null));
                    ((RelativeLayout) ll.getParent()).addView(whiteCicle);
                    final int l = ll.getLeft();
                    final int t = ll.getTop();
                    final int r = l+WmjUtils.dp2px(getContext(),10);
                    final int b = t+WmjUtils.dp2px(getContext(),10);
                    whiteCicle.layout(l+ offset,
                            t,
                            r+ offset,
                            b);
                    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        int  x=0;
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            //// TODO: 2017/2/21

//                                    Log.i("qqq", "position = " + position
//                                          + " positionOffset = " + positionOffset
//                                           + " positionOffsetPixels = " + positionOffsetPixels);
                            offset = (int) (WmjUtils.dp2px(getContext(), 10) * (position + positionOffset));
                            //Log.i("qqq","x1 = "+l+ offset);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                    WmjUtils.dp2px(getContext(), 10f)
                                    , WmjUtils.dp2px(getContext(), 10f));
                            params.leftMargin = ll.getLeft()+offset;
                            params.topMargin = ll.getTop();
                            whiteCicle.setLayoutParams(params);
                            whiteCicle.layout(l + offset,
                                    t,
                                    r + offset,
                                    b);
                            //Log.i("qqq","x2 = "+x);
                        }

                        @Override
                        public void onPageSelected(int position) {
                            whiteCicle.layout(l+offset,
                                    t,
                                    r+offset,
                                    b);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                            whiteCicle.layout(l+offset,
                                    t,
                                    r+offset,
                                    b);
                        }
                    });
                }
            }
        });
        pagerAdapter.notifyDataSetChanged();
    }
}
