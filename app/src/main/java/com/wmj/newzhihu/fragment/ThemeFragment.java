package com.wmj.newzhihu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.adapter.ThemeAdapter;
import com.wmj.newzhihu.bean.Stories;
import com.wmj.newzhihu.bean.ThemeItemBean;
import com.wmj.newzhihu.netUtils.HttpPath;
import com.wmj.newzhihu.netUtils.VolleyInterface;
import com.wmj.newzhihu.netUtils.VolleyRequest;
import com.wmj.newzhihu.utils.WmjUtils;

import org.json.JSONObject;

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


    private String Themeid;
    private ThemeItemBean mThemeItemBean;

    public String getThemeid() {
        return Themeid;
    }

    public void setThemeid(String themeid) {
        Themeid = themeid;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        initComponent(view);
        initDate();
        initListener();

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                volleyGet();
            }
        }).start();
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
    }



    @Override
    public void onResume() {
        super.onResume();
        mImageView.setFocusableInTouchMode(true);
        mImageView.requestFocus();
    }
    public void volleyGet() {
        String url = HttpPath.THEME_CONTENT+getThemeid();
        VolleyRequest.RequestGet(getContext(), url, "124",
                new VolleyInterface(getContext(), VolleyInterface.mListener, VolleyInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(JSONObject result) {
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        mThemeItemBean = gson.fromJson(String.valueOf(result), ThemeItemBean.class);
                        List<Stories> s = mThemeItemBean.getStories();

                        mList.clear();
                        mList.addAll(s);
                        mThemeAdapter.notifyDataSetChanged();

                        Glide.with(getContext()).load(mThemeItemBean.getImage()).
                                into(mImageView);
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
                    }

                    @Override
                    public void onMyError(VolleyError error) {

                    }
                });
    }
}
