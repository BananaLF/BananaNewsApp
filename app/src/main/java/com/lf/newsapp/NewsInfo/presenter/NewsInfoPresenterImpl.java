package com.lf.newsapp.NewsInfo.presenter;


import android.content.Context;

import com.lf.newsapp.Entity.Article;
import com.lf.newsapp.Entity.ImageUrl;
import com.lf.newsapp.Entity.Video;
import com.lf.newsapp.NewsInfo.model.NewsInfoModel;
import com.lf.newsapp.NewsInfo.model.NewsInfoModelImpl;
import com.lf.newsapp.NewsInfo.view.NewsInfoView;

import java.util.List;

/**
* Created by Administrator on 2017/03/24
*/

public class NewsInfoPresenterImpl implements NewsInfoPresenter{
    private NewsInfoView mNewsInfoView;
    private NewsInfoModel mNewsInfoModel;
    private Context mContext;

    public NewsInfoPresenterImpl(NewsInfoView mNewsInfoView, Context mContext) {
        this.mNewsInfoView = mNewsInfoView;
        this.mContext = mContext;
        mNewsInfoModel = new NewsInfoModelImpl(mContext,this);
    }

    @Override
    public void loadNewsInfoWeb(String url,String type) {
        mNewsInfoView.showProgress();
        mNewsInfoModel.loadNewsInfoWeb(url,type);
    }

    @Override
    public void onLoadVideoWeb(Video videoBean) {
        mNewsInfoView.hideProgress();
        //TODO
        mNewsInfoView.onLoadVideoWeb(videoBean);
    }


    @Override
    public void onLoadImageWeb(List<ImageUrl> urls) {
        mNewsInfoView.hideProgress();
        //TODO
    }

    @Override
    public void onLoadArticleWeb(Article article) {
        mNewsInfoView.hideProgress();
        mNewsInfoView.onLoadArticleWeb(article);
    }


    @Override
    public void onLoadEmptyData() {
        mNewsInfoView.onLoadEmptyData();
    }

    @Override
    public void onLoadDataError(String message) {
        mNewsInfoView.onLoadDataError(message);
    }
}