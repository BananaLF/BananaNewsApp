package com.lf.newsapp.Home.model;

/**
* Created by Administrator on 2017/03/21
*/

public interface NewsModel{
    void loadNewsData(String type);
    void loadMoreNewsData(String type,int next);
}