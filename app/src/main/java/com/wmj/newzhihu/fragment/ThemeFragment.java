package com.wmj.newzhihu.fragment;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DateSorter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.activity.NewsDetailsActivity;
import com.wmj.newzhihu.adapter.ThemeAdapter;
import com.wmj.newzhihu.bean.CacheJson;
import com.wmj.newzhihu.bean.Stories;
import com.wmj.newzhihu.bean.ThemeItemBean;
import com.wmj.newzhihu.date.GetDateUtil;
import com.wmj.newzhihu.imageLoader.ImageLoader;
import com.wmj.newzhihu.imageLoader.ImageLoaderUtil;
import com.wmj.newzhihu.netUtils.HttpPath;
import com.wmj.newzhihu.netUtils.HttpUtils;
import com.wmj.newzhihu.netUtils.VolleyInterface;
import com.wmj.newzhihu.netUtils.VolleyRequest;
import com.wmj.newzhihu.utils.WmjUtils;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ThemeAdapter mThemeAdapter;
    private List<Stories> mList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mImageView;
    private NestedScrollView mNestedScrollView;
    private TextView mTextViewTop;
    private LinearLayout mLLEdit;
    private ProgressDialog mProgressDialog;


    private int Themeid;
    private ThemeItemBean mThemeItemBean;

    public int getThemeid() {
        return Themeid;
    }

    public void setThemeid(int themeid) {
        Themeid = themeid;
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1001){
                mList.clear();
                mThemeAdapter.notifyDataSetChanged();
                ImageLoader imageLoader =new ImageLoader.Builder().url("").imageView(mImageView).build();
                ImageLoaderUtil.getInstance().loadImage(getContext(),imageLoader);
                mLLEdit.removeViews(1,mLLEdit.getChildCount()-1);
                mTextViewTop.setText("");
                mProgressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                WmjUtils.showToast(getContext(),"没有缓存");
            }
            if(msg.what==1002){
                mList.clear();
                mThemeAdapter.notifyDataSetChanged();
                ImageLoader imageLoader =new ImageLoader.Builder().url("").imageView(mImageView).build();
                ImageLoaderUtil.getInstance().loadImage(getContext(),imageLoader);
                mLLEdit.removeViews(1,mLLEdit.getChildCount()-1);
                mTextViewTop.setText("");
                mProgressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                WmjUtils.showToast(getContext(),"网络连接有问题，请检查您的网络");
            }
            if(msg.what==1000){
                Gson gson = new GsonBuilder().serializeNulls().create();
                Log.i("qqq",msg.getData().getString("date")+"");
                mThemeItemBean = gson.fromJson(msg.getData().getString("date"), ThemeItemBean.class);
                Log.i("qqq",mThemeItemBean+"");
                List<Stories> s = mThemeItemBean.getStories();
                mList.clear();
                mList.addAll(s);
                mThemeAdapter.notifyDataSetChanged();

                ImageLoader imageLoader =new ImageLoader.Builder().url(mThemeItemBean.getImage()).imageView(mImageView).build();
                ImageLoaderUtil.getInstance().loadImage(getContext(),imageLoader);
                mTextViewTop.setText(mThemeItemBean.getDescription());
                mLLEdit.removeViews(1,mLLEdit.getChildCount()-1);
                for(int i = 0;i<mThemeItemBean.getEditors().size();i++){
                    CircleImageView img = new CircleImageView(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WmjUtils.dp2px(getContext(),25),
                            WmjUtils.dp2px(getContext(),25));
                    params.setMargins(WmjUtils.dp2px(getContext(),15),0,0,0);
                    img.setLayoutParams(params);
                    Glide.with(getContext()).load(mThemeItemBean.getEditors().get(i).getAvater()).into(img);
                    mLLEdit.addView(img);
                }
                mNestedScrollView.scrollTo(mNestedScrollView.getScrollX(),0);
                mNestedScrollView.scrollTo(mNestedScrollView.getScrollX(),0);
                mSwipeRefreshLayout.setRefreshing(false);
                scrollToTop();
                mProgressDialog.dismiss();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        initComponent(view);
        initDate();
        initListener();
        scrollToTop();
        return view;
    }

    private void initListener() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDate();
            }
        });
    }

    private void refreshDate() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                volleyGet();
            }
        });
        scrollToTop();

    }

    private void initDate() {
        mList = new ArrayList<>();
        volleyGet();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mThemeAdapter = new ThemeAdapter(getContext(),mList);
        mRecyclerView.setAdapter(mThemeAdapter);

    }

    private void initComponent(View view ) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_theme);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mImageView = (ImageView) view.findViewById(R.id.top_image);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
        mTextViewTop = (TextView) view.findViewById(R.id.textview_top);
        mLLEdit = (LinearLayout) view.findViewById(R.id.LLedit);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("提示：");
        mProgressDialog.setMessage("正在加载中");
        mProgressDialog.setIcon(R.drawable.ic_launcher);
    }



    @Override
    public void onResume() {
        super.onResume();

    }
    public void scrollToTop(){
        mImageView.setFocusableInTouchMode(true);
        mImageView.requestFocus();
    }
    public void volleyGet() {
        if(getThemeid()==0)
            return;
        final String url = HttpPath.THEME_CONTENT+getThemeid();
        GetDateUtil.getDate(getContext(),url,mHandler);

        mProgressDialog.show();


//        CacheJson cacheJson = DataSupport.where("url = ?",""+url).findLast(CacheJson.class);
//        if(cacheJson!=null){
//            Log.i("qqq",cacheJson.toString());
//            Gson gson = new GsonBuilder().serializeNulls().create();
//            mThemeItemBean = gson.fromJson(cacheJson.getJsonResponse(), ThemeItemBean.class);
//            List<Stories> s = mThemeItemBean.getStories();
//            mList.clear();
//            mList.addAll(s);
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mThemeAdapter.notifyDataSetChanged();
//                    ImageLoader imageLoader =new ImageLoader.Builder().url(mThemeItemBean.getImage()).imageView(mImageView).build();
//                    ImageLoaderUtil.getInstance().loadImage(getContext(),imageLoader);
////                        Glide.with(getContext()).load(mThemeItemBean.getImage()).
////                                into(mImageView);
//                    mTextViewTop.setText(mThemeItemBean.getDescription());
//
//                    mLLEdit.removeViews(1,mLLEdit.getChildCount()-1);
//                    for(int i = 0;i<mThemeItemBean.getEditors().size();i++){
//                        CircleImageView img = new CircleImageView(getContext());
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WmjUtils.dp2px(getContext(),25),
//                                WmjUtils.dp2px(getContext(),25));
//                        params.setMargins(WmjUtils.dp2px(getContext(),15),0,0,0);
//                        img.setLayoutParams(params);
//                        //// TODO: 2017-3-15 circleview and imageview
////                            ImageLoader imageLoader2 =new ImageLoader.Builder().url(mThemeItemBean.getEditors().get(i).getAvater()).imageView(img).build();
////                            ImageLoaderUtil.getInstance().loadImage(getContext(),imageLoader2);
//                        Glide.with(getContext()).load(mThemeItemBean.getEditors().get(i).getAvater()).into(img);
//                        mLLEdit.addView(img);
//                    }
//                    mNestedScrollView.scrollTo(mNestedScrollView.getScrollX(),0);
//                    mNestedScrollView.scrollTo(mNestedScrollView.getScrollX(),0);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            });
//
//
//        }else if(HttpUtils.isNetworkConnected(getContext())){
//            VolleyRequest.RequestGet(getContext(), url, "theme",
//                    new VolleyInterface(getContext(), VolleyInterface.mListener, VolleyInterface.mErrorListener) {
//
//                        @Override
//                        public void onMySuccess(JSONObject result) {
//                            CacheJson cacheJson = new CacheJson();
//                            cacheJson.setUrl(url);
//                            cacheJson.setJsonResponse(String.valueOf(result));
//                            cacheJson.saveOrUpdateAsync("url=?",""+url).listen(new SaveCallback() {
//                                @Override
//                                public void onFinish(boolean success) {
//
//                                }
//                            });
//                            Gson gson = new GsonBuilder().serializeNulls().create();
//                            mThemeItemBean = gson.fromJson(String.valueOf(result), ThemeItemBean.class);
//
//                            List<Stories> s = mThemeItemBean.getStories();
//                            mList.clear();
//                            mList.addAll(s);
//                            mThemeAdapter.notifyDataSetChanged();
//
//                            ImageLoader imageLoader =new ImageLoader.Builder().url(mThemeItemBean.getImage()).imageView(mImageView).build();
//                            ImageLoaderUtil.getInstance().loadImage(getContext(),imageLoader);
////                        Glide.with(getContext()).load(mThemeItemBean.getImage()).
////                                into(mImageView);
//                            mTextViewTop.setText(mThemeItemBean.getDescription());
//
//                            mLLEdit.removeViews(1,mLLEdit.getChildCount()-1);
//                            for(int i = 0;i<mThemeItemBean.getEditors().size();i++){
//                                CircleImageView img = new CircleImageView(getContext());
//                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WmjUtils.dp2px(getContext(),25),
//                                        WmjUtils.dp2px(getContext(),25));
//                                params.setMargins(WmjUtils.dp2px(getContext(),15),0,0,0);
//                                img.setLayoutParams(params);
//                                //// TODO: 2017-3-15 circleview and imageview
////                            ImageLoader imageLoader2 =new ImageLoader.Builder().url(mThemeItemBean.getEditors().get(i).getAvater()).imageView(img).build();
////                            ImageLoaderUtil.getInstance().loadImage(getContext(),imageLoader2);
//                                Glide.with(getContext()).load(mThemeItemBean.getEditors().get(i).getAvater()).into(img);
//                                mLLEdit.addView(img);
//                            }
//                            mNestedScrollView.scrollTo(mNestedScrollView.getScrollX(),0);
//                            mNestedScrollView.scrollTo(mNestedScrollView.getScrollX(),0);
//                            mSwipeRefreshLayout.setRefreshing(false);
//                        }
//
//                        @Override
//                        public void onMyError(VolleyError error) {
//
//                        }
//                    });
//        }else {
//            mList.clear();
//            mThemeAdapter.notifyDataSetChanged();
//            ImageLoader imageLoader =new ImageLoader.Builder().url("").imageView(mImageView).build();
//            ImageLoaderUtil.getInstance().loadImage(getContext(),imageLoader);
//            mLLEdit.removeViews(1,mLLEdit.getChildCount()-1);
//            mTextViewTop.setText("");
//        }
//
//
    }
}
