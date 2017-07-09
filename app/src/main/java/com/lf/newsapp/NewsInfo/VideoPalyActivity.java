package com.lf.newsapp.NewsInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lf.newsapp.BaseWidget.BaseActivity;
import com.lf.newsapp.BaseWidget.StatusManager.OnRetryListener;
import com.lf.newsapp.Entity.Article;
import com.lf.newsapp.Entity.ImageUrl;
import com.lf.newsapp.Entity.NewsData;
import com.lf.newsapp.Entity.Video;
import com.lf.newsapp.NewsInfo.presenter.NewsInfoPresenter;
import com.lf.newsapp.NewsInfo.presenter.NewsInfoPresenterImpl;
import com.lf.newsapp.NewsInfo.view.NewsInfoView;
import com.lf.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by Administrator on 2017/3/24.
 */
public class VideoPalyActivity extends BaseActivity implements NewsInfoView,OnRetryListener {
    private NewsData newsData;
    private NewsInfoPresenter presenter;
    public static void startActivity(Context mContext, @Nullable  Bundle bundle){
        Intent intent = new Intent(mContext,VideoPalyActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    @BindView(R.id.videocontroller)
    JCVideoPlayer jcVideoPlayer;
    @Override
    protected int getContentView() {
        return R.layout.activity_videoplay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new NewsInfoPresenterImpl(this,this);
        newsData = getIntent().getExtras().getParcelable("data");
        presenter.loadNewsInfoWeb(newsData.source_url,"video");
        Picasso.with(this).load(newsData.image_url).into(jcVideoPlayer.ivThumb);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void loadNewsInfoWeb(String url) {

    }

    @Override
    public void onLoadVideoWeb(Video videoBean) {
        jcVideoPlayer.setUp(videoBean.main_url,newsData.title);
    }



    @Override
    public void onLoadImageWeb(List<ImageUrl> urls) {

    }

    @Override
    public void onLoadArticleWeb(Article article) {

    }

    @Override
    public void showProgress() {
        getStatusLayoutManager().showLoading();
    }

    @Override
    public void onLoadEmptyData() {
        getStatusLayoutManager().showEmptyData(this);
    }

    @Override
    public void onLoadDataError(String message) {
        getStatusLayoutManager().showNetWorkError(this);
    }

    @Override
    public void hideProgress() {
        getStatusLayoutManager().showContent();
    }

    @Override
    public void onRetry() {
        presenter.loadNewsInfoWeb(newsData.source_url,"video");
    }
}
