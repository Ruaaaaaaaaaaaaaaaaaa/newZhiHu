package com.wmj.newzhihu.activity;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DateSorter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.bean.CacheJson;
import com.wmj.newzhihu.utils.SettingsUtil;
import com.wmj.newzhihu.utils.WmjUtils;

import org.litepal.crud.DataSupport;

public class SettingActivity extends PreferenceActivity {
    private static final String TAG = "SettingActivity";
    private Toolbar toolbar;
    private AppCompatDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addPreferencesFromResource(R.xml.pref_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
//        public void bind(ListView listView) {
//            listView.setOnItemClickListener(this);
//            listView.setAdapter(getRootAdapter());
//
//            onAttachedToActivity();
//        }
        findPreference("remove_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                removeCache();
                return false;
            }
        });
        findPreference("check_version").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                WmjUtils.showToast(SettingActivity.this,"检查版本");
                return false;
            }
        });
        findPreference("back_comment").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                WmjUtils.showToast(SettingActivity.this,"意见反馈");
                return false;
            }
        });

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    /**
     * Glide自带清除缓存的功能,分别对应Glide.get(context).clearDiskCache();(清除磁盘缓存)
     * 与Glide.get(context).clearMemory();(清除内存缓存)两个方法.
     * 其中clearDiskCache()方法必须运行在子线程,clearMemory()方法必须运行在主线程,
     * 这是这两个方法所强制要求的,详见源码.
     */
    private void removeCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(SettingActivity.this).clearDiskCache();
            }
        }).start();
        Glide.get(SettingActivity.this).clearMemory();
        WmjUtils.showToast(SettingActivity.this,"缓存已清除");
        DataSupport.deleteAll(CacheJson.class);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getDelegate().setContentView(layoutResID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    private void setSupportActionBar(Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }
    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    private AppCompatDelegate getDelegate() {
        if (delegate == null) {
            delegate = AppCompatDelegate.create(this, null);
        }
        return delegate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
