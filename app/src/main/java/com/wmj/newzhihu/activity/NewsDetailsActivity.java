package com.wmj.newzhihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.bean.BeforeNewsBean;
import com.wmj.newzhihu.bean.ContentBean;
import com.wmj.newzhihu.bean.LastNews;
import com.wmj.newzhihu.netUtils.HttpPath;
import com.wmj.newzhihu.netUtils.VolleyInterface;
import com.wmj.newzhihu.netUtils.VolleyRequest;
import com.wmj.newzhihu.utils.WmjUtils;

import org.json.JSONObject;

public class NewsDetailsActivity extends AppCompatActivity {
    private String id;
    private ContentBean mContentBean;
    private WebView mWebView;
    private String mDate;
    private ImageView mIVTop;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 2017-3-8
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        id = getIntent().getStringExtra("id");
        mIVTop = (ImageView) findViewById(R.id.iv_topImage);
        mWebView = (WebView) findViewById(R.id.webview);
        volleyGet();
    }

    private void volleyGet() {
        String url = HttpPath.NEWS_DETAILS+id;
        VolleyRequest.RequestGet(this, url, "details",
                new VolleyInterface(this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(JSONObject result) {
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        mContentBean= gson.fromJson(String.valueOf(result), ContentBean.class);

                        Glide.with(NewsDetailsActivity.this).load(mContentBean.getImage()).into( mIVTop);
                        mToolbar.setTitle(mContentBean.getTitle());
                        mDate = mContentBean.getBody();

                        if (TextUtils.isEmpty( mDate)) {
                            mWebView.loadUrl( mContentBean.getShare_url());
                            return;
                        }
                        String body = mDate.replace("<div class=\"headline\">", "").replace("<div class=\"img-place-holder\">", "");
                        String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + "<br/>" +body;
                        mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);

                    }

                    @Override
                    public void onMyError(VolleyError error) {
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
                break;
            }
            case R.id.action_share: {
                WmjUtils.showToast(NewsDetailsActivity.this,"分享");
                break;
            }
            case R.id.action_comment: {
                Intent intent = new Intent(NewsDetailsActivity.this,CommentActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
