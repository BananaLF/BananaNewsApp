package com.lf.newsapp.BaseWidget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fManager;
    private int mChildCount;

    public FragmentViewPagerAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        this.fManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //返回PagerAdapter.POSITION_NONE保证调用notifyDataSetChanged刷新Fragment。
    @Override
    public void notifyDataSetChanged() {
        // 重写这个方法，取到子Fragment的数量，用于下面的判断，以执行多少次刷新
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            // 这里利用判断执行若干次不缓存，刷新
            mChildCount--;
            // 返回这个是itemPOSITION_NONE
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    //配合TABView时使用
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
