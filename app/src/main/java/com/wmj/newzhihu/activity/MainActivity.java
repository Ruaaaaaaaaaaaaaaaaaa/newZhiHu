package com.wmj.newzhihu.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wmj.newzhihu.R;
import com.wmj.newzhihu.adapter.MenuItemAdapter;
import com.wmj.newzhihu.fragment.HomeFragment;
import com.wmj.newzhihu.fragment.ThemeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mLvLeftMenu;
    private MenuItemAdapter menuAdapter;
    private List<MenuItemAdapter.LvMenuItem> mLvMenuItem = null;
    private FrameLayout mFrameLayout;
    private Fragment mFragment;
    private Fragment[] fragments ={new HomeFragment(),new ThemeFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        initToolBar();
        initDate();
        initFragment();
    }


    private void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,mToolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerToggle.syncState();
        mDrawerLayout.setDrawerListener(drawerToggle);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        }

    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment,fragments[1]).hide(fragments[1]);
        ft.add(R.id.fragment,fragments[0]);
        mFragment = fragments[0];
        ft.commit();
    }
    private void changeFragment(Fragment fragment){
        if(fragment == mFragment){
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(!fragment.isAdded()){
            ft.add(R.id.fragment,fragment);
        }
        ft.hide(mFragment).show(fragment);
        mFragment = fragment;
        ft.commit();
    }

    private void initComponent() {
        mToolbar = (Toolbar) findViewById(R.id.include).findViewById(R.id.toolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);
        mFrameLayout = (FrameLayout) findViewById(R.id.include).findViewById(R.id.fragment);
    }

    private void initDate() {
        mLvLeftMenu.addHeaderView(LayoutInflater.from(MainActivity.this).inflate(R.layout.nav_header, mLvLeftMenu, false));

        mLvMenuItem = new ArrayList<>();
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem(R.drawable.ic_home,"首页"));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("日常心理学",""+13));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("用户推荐日报",""+12));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("电影日报",""+3));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("不许无聊",""+11));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("设计日报",""+4));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("大公司日报",""+5));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("财经日报",""+6));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("互连网安全",""+10));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("开始游戏",""+2));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("音乐日报",""+7));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("动漫日报",""+9));
        mLvMenuItem.add(new MenuItemAdapter.LvMenuItem("体育日报",""+8));

        menuAdapter = new MenuItemAdapter(this, mLvMenuItem);
        mLvLeftMenu.setAdapter(menuAdapter);
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    return;
                }

                if (i == 1) {
                    mToolbar.setTitle("首页");
                    mToolbar.getMenu().clear();
                    mToolbar.inflateMenu(R.menu.toolbar);
                    changeFragment(fragments[0]);

                } else {
                    int positon = i - 1;
                    mToolbar.setTitle(mLvMenuItem.get(positon).getName());
                    mToolbar.getMenu().clear();
                    mToolbar.inflateMenu(R.menu.test);
                    changeFragment(fragments[1]);
                    ((ThemeFragment)(fragments[1])).setThemeid( Integer.parseInt(mLvMenuItem.get(positon).getId()));
                    ((ThemeFragment)(fragments[1])).volleyGet();
                }
                mDrawerLayout.closeDrawers();

            }
        });
    }


    private void changeMode(MenuItem item) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
//        recreate();
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            //SharedPreferencesUtil.setBoolean(mActivity, Constants.ISNIGHT, false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            //SharedPreferencesUtil.setBoolean(mActivity, Constants.ISNIGHT, true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        //调用 recreate(); 使设置生效

        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.message:
                Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
                break;
            case R.id.changeMode:
                changeMode(item);
                break;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
            case R.id.isFavorite:
                if(item.getTitle()=="add"){
                    item.setIcon(R.drawable.ic_sub);
                    item.setTitle("sub");
                }else{
                    item.setIcon(R.drawable.ic_add);
                    item.setTitle("add");
                }
                break;
            default:
        }
        return true;
    }


}
