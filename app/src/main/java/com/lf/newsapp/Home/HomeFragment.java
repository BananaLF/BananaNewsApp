package com.lf.newsapp.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lf.newsapp.BaseWidget.BaseFragment;
import com.lf.newsapp.BaseWidget.Constant;
import com.lf.newsapp.BaseWidget.FragmentViewPagerAdapter;
import com.lf.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/5.
 */
public class HomeFragment extends BaseFragment{


    @BindView(R.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    FragmentViewPagerAdapter mViewPagerAdapter;

    private List<Fragment> fragments;
    private List<String> titles;
    private List<String> type;
    public static HomeFragment getFragment( Bundle bundle){
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //View view = inflater.inflate(R.layout.fragment_home_list,container,false);
        ButterKnife.bind(this,view);

        initData();
        initfragment();
        initview();
        return view;
    }

    private void initfragment() {
        fragments = new ArrayList<>();
        for(int i = 0; i<titles.size();i++){
            Bundle bundle = new Bundle();
            bundle.putString("type",type.get(i));
            fragments.add(HomeListFragment.getFragment(bundle));

        }
    }
    private void initData() {
        titles = new ArrayList<>();
        type = new ArrayList<>();
        for(int i = 0; i< Constant.title.length; i++){
            titles.add(Constant.title[i]);
            type.add(Constant.titlesCode[i]);
        }
    }
    private void initview() {
        //设置TabLayout可滚动，保证Tab数量过多时也可正常显示
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //两个参数分别对应Tab未选中的文字颜色和选中的文字颜色
        mTabLayout.setTabTextColors(Color.WHITE, Color.RED);
        //绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        //设置TabLayout的布局方式（GRAVITY_FILL、GRAVITY_CENTER）
        //mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //设置TabLayout的选择监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //点击Tab时回调
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            //重复点击Tab时回调
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //scrollToTop(mFragments.get(tab.getPosition()).getTypeList());
            }
        });
        mViewPagerAdapter = new FragmentViewPagerAdapter(getActivity().getSupportFragmentManager(),fragments,titles);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mViewPagerAdapter);
    }


}
