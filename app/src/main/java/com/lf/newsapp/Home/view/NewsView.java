package com.lf.newsapp.Home.view;

import com.lf.newsapp.BaseRecyclerView.BaseView;
import com.lf.newsapp.Entity.NewsData;

import java.util.List;

/**
* Created by Administrator on 2017/03/21
*/

public interface NewsView extends BaseView{

    //Present to View
    void onloadNewsData(List<NewsData> newsDatas);

    void onLoadMoreNewsData(List<NewsData> newsDatas);
}