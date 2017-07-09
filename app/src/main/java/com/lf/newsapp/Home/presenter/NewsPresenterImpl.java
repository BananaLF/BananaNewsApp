package com.lf.newsapp.Home.presenter;


import android.content.Context;

import com.lf.newsapp.Entity.NewsData;
import com.lf.newsapp.Home.model.NewsModel;
import com.lf.newsapp.Home.model.NewsModelImpl;
import com.lf.newsapp.Home.view.NewsView;

import java.util.List;

/**
* Created by Administrator on 2017/03/21
*/

public class NewsPresenterImpl implements NewsPresenter{
    public static int next = 0;
    private NewsView mNewsView;
    private NewsModel mNewsModel;
    private Context mContext;
    public NewsPresenterImpl(NewsView mNewsView,Context mContext) {
        this.mNewsView = mNewsView;
        this.mContext = mContext;
        mNewsModel = new NewsModelImpl(this,this.mContext);
    }

    @Override
    public void loadNewsData(String type) {
        mNewsView.showProgress();
        mNewsModel.loadNewsData(type);
    }

    @Override
    public void onloadNewsData(List<NewsData> newsDatas) {
        mNewsView.hideProgress();
        mNewsView.onloadNewsData(newsDatas);
    }

    @Override
    public void setNext(int next) {
        this.next = next;
    }

    @Override
    public void loadMoreNewsData(String type) {
        //mNewsView.showProgress();
        mNewsModel.loadMoreNewsData(type,next);
    }


    @Override
    public void onLoadMoreNewsData(List<NewsData> newsDatas) {
        //mNewsView.hideProgress();
        mNewsView.onLoadMoreNewsData(newsDatas);
    }

    @Override
    public void onLoadEmptyData() {
        mNewsView.onLoadEmptyData();
    }

    @Override
    public void onLoadDataError(String message) {
        mNewsView.onLoadDataError(message);
    }
}