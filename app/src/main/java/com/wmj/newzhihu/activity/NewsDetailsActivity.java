package com.wmj.newzhihu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.bean.BeforeNewsBean;
import com.wmj.newzhihu.bean.ContentBean;
import com.wmj.newzhihu.bean.LastNews;
import com.wmj.newzhihu.date.GetDateUtil;
import com.wmj.newzhihu.imageLoader.ImageLoader;
import com.wmj.newzhihu.imageLoader.ImageLoaderUtil;
import com.wmj.newzhihu.netUtils.HttpPath;
import com.wmj.newzhihu.netUtils.VolleyInterface;
import com.wmj.newzhihu.netUtils.VolleyRequest;
import com.wmj.newzhihu.utils.SpUtil;
import com.wmj.newzhihu.utils.WmjUtils;

import org.json.JSONObject;

public class NewsDetailsActivity extends AppCompatActivity {
    private String id;
    private ContentBean mContentBean;
    private WebView mWebView;
    private String mDate;
    private ImageView mIVTop;
    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1000){

                Gson gson = new GsonBuilder().serializeNulls().create();
                mContentBean= gson.fromJson(msg.getData().getString("date"), ContentBean.class);
                if(mContentBean.getImage()!=null){
                    ImageLoader imageLoader =new ImageLoader.Builder().url(mContentBean.getImage()).imageView(mIVTop).build();
                    ImageLoaderUtil.getInstance().loadImage(NewsDetailsActivity.this,imageLoader);
                }

                mToolbar.setTitle(mContentBean.getTitle());
                mDate = mContentBean.getBody();

                if (TextUtils.isEmpty( mDate)) {
                    mWebView.loadUrl( mContentBean.getShare_url());
                    return;
                }
                String body = mDate.replace("<div class=\"headline\">", "").replace("<div class=\"img-place-holder\">", "");
                String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + "<br/>" +body;
                mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                mProgressDialog.dismiss();

            }
            if (msg.what == 1001){
                mToolbar.setTitle("");
                mWebView.loadUrl("");
                WmjUtils.showToast(NewsDetailsActivity.this,"无缓存");
                mProgressDialog.dismiss();
            }
        }
    };

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
        mWebView.getSettings().setJavaScriptEnabled(true);
        mProgressDialog = new ProgressDialog(NewsDetailsActivity.this);
        mProgressDialog.setTitle("提示：");
        mProgressDialog.setMessage("正在加载中");
        mProgressDialog.setIcon(R.drawable.ic_launcher);
        if(SpUtil.getBooleanByPreferenceManager(NewsDetailsActivity.this,"current_text_size",false)){
            mWebView.getSettings().setTextZoom(120);
        }else{
            mWebView.getSettings().setTextZoom(100);
        }
        if((NewsDetailsActivity.this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK )== Configuration.UI_MODE_NIGHT_YES){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    mWebView.evaluateJavascript("document.body.style.backgroundColor=\"black\";document.body.style.color=\"white\";", null);
                mWebView.evaluateJavascript("document.body.style.backgroundColor=\"212a2f\";document.body.style.color=\"white\";", null);
            } else {
                mWebView.loadUrl("javascript:document.body.style.backgroundColor=\"#black\";document.body.style.color=\"white\";");
            }
        }
        mWebView.setWebViewClient(new webViewClient());
        volleyGet();
    }
    private class webViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if((NewsDetailsActivity.this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK )== Configuration.UI_MODE_NIGHT_YES){
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    mWebView.evaluateJavascript("document.body.style.backgroundColor=\"black\";document.body.style.color=\"white\";", null);
                    mWebView.evaluateJavascript("document.body.style.backgroundColor=\"212a2f\";document.body.style.color=\"white\";", null);
                } else {
                    mWebView.loadUrl("javascript:document.body.style.backgroundColor=\"#black\";document.body.style.color=\"white\";");
                }
            }

        }
    }

    private void volleyGet() {
        String url = HttpPath.NEWS_DETAILS+id;
        GetDateUtil.getDate(NewsDetailsActivity.this,url,mHandler);
        mProgressDialog.show();
//        VolleyRequest.RequestGet(this, url, "details",
//                new VolleyInterface(this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
//
//                    @Override
//                    public void onMySuccess(JSONObject result) {
//                        Gson gson = new GsonBuilder().serializeNulls().create();
//                        mContentBean= gson.fromJson(String.valueOf(result), ContentBean.class);
//                        if(mContentBean.getImage()!=null){
//                            ImageLoader imageLoader =new ImageLoader.Builder().url(mContentBean.getImage()).imageView(mIVTop).build();
//                            ImageLoaderUtil.getInstance().loadImage(NewsDetailsActivity.this,imageLoader);
//                        }
//
//                        mToolbar.setTitle(mContentBean.getTitle());
//                        mDate = mContentBean.getBody();
//
//                        if (TextUtils.isEmpty( mDate)) {
//                            mWebView.loadUrl( mContentBean.getShare_url());
//                            return;
//                        }
//                        String body = mDate.replace("<div class=\"headline\">", "").replace("<div class=\"img-place-holder\">", "");
//                        String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + "<br/>" +body;
//                        mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
//
//                    }
//
//                    @Override
//                    public void onMyError(VolleyError error) {
//                    }
//                });
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
