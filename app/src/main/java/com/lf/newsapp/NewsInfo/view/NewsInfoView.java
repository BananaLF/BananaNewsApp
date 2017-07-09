package com.lf.newsapp.NewsInfo.view;

import com.lf.newsapp.BaseRecyclerView.BaseView;
import com.lf.newsapp.Entity.Article;
import com.lf.newsapp.Entity.ImageUrl;
import com.lf.newsapp.Entity.Video;

import java.util.List;

/**
* Created by Administrator on 2017/03/24
*/

public interface NewsInfoView extends BaseView{
    //载入web
    void loadNewsInfoWeb(String url);
    //TODO 目前只解析出视频源 载入web为视频源
    void onLoadVideoWeb(Video videoBean);
    //TODO 目前只解析出图片源 载入web为图片源
    void onLoadImageWeb(List<ImageUrl> urls);
    //TODO 目前只解析出文章源 载入web为文章源（文章未建立实体）
    void onLoadArticleWeb(Article article);
}