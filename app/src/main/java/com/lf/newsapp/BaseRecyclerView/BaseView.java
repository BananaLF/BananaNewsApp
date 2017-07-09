package com.lf.newsapp.BaseRecyclerView;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface BaseView {
    //打开进度条
    void showProgress();
    //关闭进度条
    void hideProgress();
    //没有获取到数据
    void onLoadEmptyData();
    //错误
    void onLoadDataError(String message);
}
