package com.lf.newsapp.Home.model;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.lf.newsapp.BaseWidget.Constant;
import com.lf.newsapp.Entity.ResultNews;
import com.lf.newsapp.Home.presenter.NewsPresenter;
import com.lf.newsapp.Net.BaseSubscriber;
import com.lf.newsapp.Net.NetCacheManager;
import com.lf.newsapp.Utils.NewsDataUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

/**
* Created by Administrator on 2017/03/21
*/

public class NewsModelImpl implements NewsModel{

    private NewsPresenter mNewsPresenter;
    private Context mContext;
    public NewsModelImpl(NewsPresenter mNewsPresenter,Context mContext) {
        this.mNewsPresenter = mNewsPresenter;
        this.mContext = mContext;
    }

    @Override
    public void loadNewsData(String type) {
        Map<String,String> map = new HashMap<>();
        map.put("category",type);
        map.put("utm_source","toutiao");
        map.put("widen","1");
        map.put("max_behot_time_tmp","0");
        map.put("as","A1C528E25E76FB8");
        map.put("cp","582EC64FEBD84E1");
        map.put("max_behot_time","0");
        new NetCacheManager.Builder()
                .setmBaseUrl(Constant.TODAY_NEW_URL)
                .setmPath(Constant.TODAY_NEW_PATH)
                .setParameters(map)
                .setNetCacheType(NetCacheManager.GET_TYPE)
                .setContext(mContext)
                .build()
                .loadData(new BaseSubscriber<ResponseBody>(mContext) {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        //网络请求出错执此方法
                        Log.e("UserModelImpl", e.toString());
                        e.printStackTrace();
                        mNewsPresenter.onLoadDataError(e.getMessage());
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //网络请求成功执此方法，responseBody为统一返回值
                        String Responsestr = null;
                        try {
                            Log.i("UserModelImpl", responseBody.toString());
                            Responsestr = responseBody.string();
                            Gson gson = new Gson();
                            ResultNews result = gson.fromJson(Responsestr,ResultNews.class);

                            if(result == null || result.getData().size() <= 0){
                                mNewsPresenter.onLoadEmptyData();
                            }else {
                                mNewsPresenter.onloadNewsData(NewsDataUtils.filterNoImage(result.getData()));
                                mNewsPresenter.setNext(result.getNext().getMax_behot_time());
                            }
                        } catch (JsonIOException e){
                            mNewsPresenter.onLoadDataError("数据解析出错");
                        }catch (IOException e) {
                            e.printStackTrace();
                            mNewsPresenter.onLoadDataError(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void loadMoreNewsData(String type, int next) {
        Map<String,String> map = new HashMap<>();
        map.put("category",type);
        map.put("utm_source","toutiao");
        map.put("tadrequire","true");
        map.put("widen","1");
        map.put("max_behot_time_tmp",next+"");
        map.put("as","A195F81DB397BE8");//A195F81DB397BE8  A1C528E25E76FB8  tadrequire=true
        map.put("cp","58D3D71BBE38FE1");//58D3D71BBE38FE1  582EC64FEBD84E1
        map.put("max_behot_time",next+"");
        new NetCacheManager.Builder()
                .setmBaseUrl(Constant.TODAY_NEW_URL)
                .setmPath(Constant.TODAY_NEW_PATH)
                .setParameters(map)
                .setNetCacheType(NetCacheManager.GET_TYPE)
                .setContext(mContext)
                .build()
                .loadData(new BaseSubscriber<ResponseBody>(mContext) {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        //网络请求出错执此方法
                        Log.e("UserModelImpl", e.toString());
                        e.printStackTrace();
                        mNewsPresenter.onLoadDataError(e.getMessage());
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //网络请求成功执此方法，responseBody为统一返回值
                        String Responsestr = null;
                        try {
                            Log.i("UserModelImpl", responseBody.toString());
                            Responsestr = responseBody.string();
                            Gson gson = new Gson();
                            ResultNews result = gson.fromJson(Responsestr,ResultNews.class);

                            if(result == null || result.getData().size() <= 0){
                                //mNewsPresenter.onLoadEmptyData();
                            }else {
                                mNewsPresenter.onLoadMoreNewsData(NewsDataUtils.filterNoImage(result.getData()));
                                mNewsPresenter.setNext(result.getNext().getMax_behot_time());
                            }
                        } catch (JsonIOException e){
                            mNewsPresenter.onLoadDataError("数据解析出错");
                        }catch (IOException e) {
                            e.printStackTrace();
                            mNewsPresenter.onLoadDataError(e.getMessage());
                        }
                    }
                });
    }


}