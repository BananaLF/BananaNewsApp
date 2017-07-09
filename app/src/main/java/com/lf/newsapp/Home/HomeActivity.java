package com.lf.newsapp.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lf.newsapp.BaseWidget.BaseActivity;
import com.lf.newsapp.Chat.ChatFragment;
import com.lf.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/20.
 */
public class HomeActivity extends BaseActivity {
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationview)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //放置ToolBar
        ButterKnife.bind(this);
        setToolbar();
        setNaviView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        fragmentManager.beginTransaction().add(R.id.home_content,HomeFragment.getFragment(bundle)).commit();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO 目前Menu功能待定2017/3/21
        // getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }



    private void setNaviView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        //设置左上角显示三道横线
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(onMenuItemClick);
    }
    /**
     * 设置ToolBar
     */
    private void setToolbar(){
        // Title
        toolbar.setTitle(R.string.app_name);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        // Navigation Icon 要設定在 setSupoortActionBar 才有作用
        // 否則會出現 back button
        toolbar.setNavigationIcon(R.drawable.category);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

    }
    /**
     * 导航点击事件
     */
    private NavigationView.OnNavigationItemSelectedListener onMenuItemClick = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.action_search:
                    msg += "Click action_search";
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    fragmentManager.beginTransaction().replace(R.id.home_content, ChatFragment.getFragment(bundle)).commit();
                    break;
                case R.id.action_share:
                    msg += "Click action_share";
                    break;
                case R.id.action_collect:
                    msg += "Click action_collect";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }


    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}

