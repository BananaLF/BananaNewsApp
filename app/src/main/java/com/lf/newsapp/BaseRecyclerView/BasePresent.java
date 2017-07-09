package com.lf.newsapp.BaseRecyclerView;

/**
 * Created by Administrator on 2017/3/22.
 */
public abstract interface BasePresent {
    void onLoadEmptyData();
    //错误
    void onLoadDataError(String message);
}
