package com.lf.newsapp.Net;

import android.content.Context;
import android.util.Log;

import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;

/**
 * ***
 *
 * @author lf
 * @version 1.0, 2016/10/11
 * @since 1.0
 */
public class NetCacheManager {
    private static final String TAG = "NetCacheManger" ;
    private int mNetCacheType = GET_TYPE;//类型默认为GET类型
    private Map<String,String> headers;
    private Map<String,String> parameters;
    private Context mContext;
    //http://blog.csdn.net/liuhongwei123888/svc/GetCategoryArticleList?id=834259&type=foot
    private String mBaseUrl = "";
    private String mPath = "";
    public static final int GET_TYPE = 0;//GET类型
    public static final int POST_TYPE = 1;//POST类型
    public static final int DELETE_TYPE = 2;//DELETE类型
    public static final int POST_DOWNLOAD_TYPE = 3;//POST_DOWNLOAD类型

    public static Observable getObservable() {
        return observable;
    }

    private static Observable observable;

    public Observable loadData(Observer<ResponseBody> observer){
        if(mBaseUrl.isEmpty()){
            Log.e(TAG,"mBaseUrl 为空.请检查baseUrl的值 ");
            return null;
        }
        switch (mNetCacheType){
            case GET_TYPE:
                return getData(observer);

            case POST_TYPE:
                return postData(observer);

            case DELETE_TYPE:
                return getData(observer);

            case POST_DOWNLOAD_TYPE:
                return postDownLoadFile(observer);

        }
        return null;
    }
    public NetCacheManager(Builder builder) {
        this.mBaseUrl = builder.mBaseUrl;
        this.mPath = builder.mPath ;
        this.mNetCacheType = builder.mNetCacheType;
        this.headers = builder.headers;
        this.parameters = builder.parameters;
//        this.mCache2DatamanagerListener = builder.mCache2DatamanagerListener;
        this.mContext = builder.mContext;
    }

    /*GET请求获取网络数据*/
    private Observable getData(Observer<ResponseBody> observer) {
        if (mContext != null) {
            if(HttpData.getInstance().baseUrl == null || HttpData.getInstance().baseUrl.equals(mBaseUrl)) {
               return HttpData.getInstance().useGetMethod(mContext, mBaseUrl, mPath, parameters, observer);
            }else {
               return HttpData.getInstance().getNewInstance().useGetMethod(mContext, mBaseUrl, mPath, parameters, observer);
            }
        }
        return null;
    }

    /*POST请求获取网络数据*/
    private Observable postData(Observer<ResponseBody> observer) {
        if (mContext != null) {
            if(HttpData.getInstance().baseUrl == null|| HttpData.getInstance().baseUrl.equals(mBaseUrl)) {
               return HttpData.getInstance().usePostMethod(mContext, mBaseUrl, mPath,parameters,observer);
            }else {
               return HttpData.getInstance().getNewInstance().usePostMethod(mContext, mBaseUrl, mPath, parameters, observer);
            }

        }
        return null;
    }

    /*POST下载文件*/
    private Observable postDownLoadFile(Observer<ResponseBody> observer){
        if(mContext != null){
            if(HttpData.getInstance().baseUrl == null|| HttpData.getInstance().baseUrl.equals(mBaseUrl)) {
               return HttpData.getInstance().usePostDownLoadMethod(mContext,mBaseUrl,mPath,parameters,observer);
            }else {
               return HttpData.getInstance().getNewInstance().usePostDownLoadMethod(mContext, mBaseUrl, mPath, parameters, observer);
            }
        }
        return null;
    }
    public static class Builder{
        private int mNetCacheType = GET_TYPE;//类型默认为GET类型
//        private Cache2DatamanagerListener mCache2DatamanagerListener;
        private static String mBaseUrl = "";
        private static String mPath = "";
        private Map<String,String> headers;
        private Map<String,String> parameters;

        public Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        private Context mContext;

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setmBaseUrl(String mBaseUrl) {
            Builder.mBaseUrl = mBaseUrl;
            return this;
        }

//        public Builder setCache2DatamanagerListener(Cache2DatamanagerListener cache2DatamanagerListener) {
//            mCache2DatamanagerListener = cache2DatamanagerListener;
//            return this;
//        }

        public Builder setNetCacheType(int netCacheType) {
            mNetCacheType = netCacheType;
            return this;
        }

        public Builder setmPath(String mPath) {
            Builder.mPath = mPath;
            return this;
        }

        public Builder setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
            return this;
        }
        public NetCacheManager build(){
            return new NetCacheManager(this);
        }
    }
}
