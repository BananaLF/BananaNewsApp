package com.lf.newsapp.NewsInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

/**
 * Created by Administrator on 2017/4/7.
 */
public class NewsInfoAjaxActivity extends BaseActivity implements NewsInfoView,OnRetryListener  {
    @BindView(R.id.newsinfoajaxlinear)
    LinearLayout linearLayout;
    @BindView(R.id.newsinfoajaxtoolbar)
    Toolbar toolbar;
    private NewsData newsData;
    private NewsInfoPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        newsData = getIntent().getExtras().getParcelable("data");
        setToolbar();
        presenter = new NewsInfoPresenterImpl(this,this);
        if(newsData.tag_url != null) {
            presenter.loadNewsInfoWeb(newsData.source_url, newsData.tag_url);
        }else {
            presenter.loadNewsInfoWeb(newsData.source_url, "_all_");
        }
    }

    public static void startActivity(Context mContext, Bundle bundle){
        Intent intent = new Intent(mContext,NewsInfoAjaxActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_newsinfoajax;
    }
    private void setToolbar(){
        // Title
        toolbar.setTitle(newsData.chinese_tag);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        // Navigation Icon 要設定在 setSupoortActionBar 才有作用
        // 否則會出現 back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public TextView makeTextView(String context){
        TextView textView = new TextView(this);
        textView.setText("\t\t"+context);
        textView.append("\n");
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(textView);
        return  textView;
    }

    public ImageView makeImageView(String url){
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(imageView);
        Picasso.with(this).load(url).into(imageView);
        return imageView;
    }
    @Override
    public void loadNewsInfoWeb(String url) {

    }

    @Override
    public void onLoadVideoWeb(Video videoBean) {

    }

    @Override
    public void onLoadImageWeb(List<ImageUrl> urls) {

    }

    @Override
    public void onLoadArticleWeb(Article article) {
        makeTextView(article.getTitle());
        makeTextView(article.getSource()+":"+ article.getTime());
        for(int i = 0;i<article.getLineElements().size();i++){
            if(article.getLineElements().get(i).getImgUrl() != null){
                makeImageView(article.getLineElements().get(i).getImgUrl());
            }else {
                makeTextView(article.getLineElements().get(i).getDocElement());
            }
        }
    }


    @Override
    public void hideProgress() {
        getStatusLayoutManager().showContent();
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
    public void onRetry() {
        presenter.loadNewsInfoWeb(newsData.source_url,newsData.tag_url);
    }
}
