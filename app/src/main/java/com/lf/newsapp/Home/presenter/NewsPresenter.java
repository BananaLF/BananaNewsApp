package com.lf.newsapp.Home.presenter;

import com.lf.newsapp.BaseRecyclerView.BasePresent;
import com.lf.newsapp.Entity.NewsData;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public interface NewsPresenter extends BasePresent {
    //View to Present
    void loadNewsData(String type);
    //Model to Present
    void onloadNewsData(List<NewsData> newsDatas);
    void setNext(int next);
    void loadMoreNewsData(String type);
    void onLoadMoreNewsData(List<NewsData> newsDatas);
}
