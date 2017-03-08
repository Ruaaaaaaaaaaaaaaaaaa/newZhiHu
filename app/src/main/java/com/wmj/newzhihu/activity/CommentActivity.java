package com.wmj.newzhihu.activity;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.adapter.CommentAdapter;
import com.wmj.newzhihu.bean.BeforeNewsBean;
import com.wmj.newzhihu.bean.Comment;
import com.wmj.newzhihu.bean.CommentBean;
import com.wmj.newzhihu.bean.LastNews;
import com.wmj.newzhihu.netUtils.HttpPath;
import com.wmj.newzhihu.netUtils.VolleyInterface;
import com.wmj.newzhihu.netUtils.VolleyRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends AppCompatActivity {
    private String id;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewLong;
    private RecyclerView mRecyclerViewShort;
    private CommentAdapter mAdapterLong;
    private CommentAdapter mAdapterShort;
    private List<Comment> mListLong;
    private List<Comment> mListShort;
    private CommentBean mShortCommentBean;
    private CommentBean mLongCommentBean;
    private TextView mTVLong;
    private TextView mTVShort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);
        id = getIntent().getStringExtra("id");

        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        initComponent();
        initDate();

    }

    private void initDate() {
        mListLong = new ArrayList<>();


        mListShort = new ArrayList<>();


        volleyLongComment();
        volleyShortComment();
        mAdapterShort = new CommentAdapter(CommentActivity.this,mListShort);
        mAdapterLong = new CommentAdapter(CommentActivity.this,mListLong);
        LinearLayoutManager layoutManagerShort = new LinearLayoutManager(CommentActivity.this);

        layoutManagerShort.setSmoothScrollbarEnabled(true);
        layoutManagerShort.setAutoMeasureEnabled(true);
        mRecyclerViewShort.setHasFixedSize(true);
        mRecyclerViewShort.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManagerLong = new LinearLayoutManager(CommentActivity.this);

        layoutManagerLong.setSmoothScrollbarEnabled(true);
        layoutManagerLong.setAutoMeasureEnabled(true);
        mRecyclerViewLong.setHasFixedSize(true);
        mRecyclerViewLong.setNestedScrollingEnabled(false);

        mRecyclerViewShort.setLayoutManager(layoutManagerShort);
        mRecyclerViewLong.setLayoutManager(layoutManagerLong);
        mRecyclerViewLong.setAdapter(mAdapterLong);
        mRecyclerViewShort.setAdapter(mAdapterShort);

    }

    private void volleyShortComment() {
        String url = HttpPath.COMMENT+id+"/short-comments";
        VolleyRequest.RequestGet(this, url, "short-comments",
                new VolleyInterface(this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(JSONObject result) {
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        mShortCommentBean = gson.fromJson(String.valueOf(result), CommentBean.class);
                        mListShort.addAll(mShortCommentBean.getComments());
                        mTVShort.setText(""+mListShort.size()+"条短评");
                        mAdapterShort.notifyDataSetChanged();
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                    }
                });
    }

    private void volleyLongComment() {
        String url = HttpPath.COMMENT+id+"/long-comments";
        VolleyRequest.RequestGet(this, url, "long-comments",
                new VolleyInterface(this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(JSONObject result) {
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        mLongCommentBean = gson.fromJson(String.valueOf(result), CommentBean.class);
                        mListLong.addAll(mLongCommentBean.getComments());
                        mTVLong.setText(""+mListLong.size()+"条长评");
                        mAdapterLong.notifyDataSetChanged();
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                    }
                });
    }

    private void initComponent() {
        mRecyclerViewLong = (RecyclerView) findViewById(R.id.recyclerView_long);
        mRecyclerViewShort = (RecyclerView) findViewById(R.id.recyclerView_short);
        mTVLong = (TextView) findViewById(R.id.long_comment);
        mTVShort = (TextView) findViewById(R.id.short_comment);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
