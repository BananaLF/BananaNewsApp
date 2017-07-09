package com.lf.newsapp.Net;

import android.content.Context;

import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ***
 *
 * @author luozf
 * @version 1.0, 2016/10/11
 * @since 1.0
 */
public class HttpData extends RetrofitUtil {



    protected static HttpService service ;
    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpData INSTANCE = new HttpData();
    }

    //获取单例
    public static HttpData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //获取新的实例
    public  HttpData getNewInstance() {
        service = null;
        HttpData data = new HttpData();
        return data;
    }

    public static HttpService getService() {
        return service;
    }
    public static HttpService getService(Context context,String baseUrl) {
        service = getRetrofit(context, baseUrl).create(HttpService.class);
        return service;
    }
    /**
     * Get请求方法
     * @param context
     * @param baseUrl
     * @param path
     * @param map
     * @param observer
     */
    public Observable useGetMethod(Context context, String baseUrl, String path, Map<String,String> map, Observer<ResponseBody> observer){
        service = getRetrofit(context, baseUrl).create(HttpService.class);
        //Observable observable  = service.getNetDataWithPathParams(Path,map);
        Observable observable = service.getNetData(path,map);
        setSubscribe(observable,observer);
        return observable;
    }

    /**
     * POST请求方法
     * @param context
     * @param baseUrl
     * @param path
     * @param map
     * @param observer
     */
    public Observable usePostMethod(Context context, String baseUrl, String path,Map<String,String> map, Observer<ResponseBody> observer){
        service = getRetrofit(context, baseUrl).create(HttpService.class);
        //Observable observable  = service.getNetDataWithPathParams(Path,map);
        Observable observable = service.postNetData(path,map);
        setSubscribe(observable,observer);
        return observable;
    }

    public Observable usePostDownLoadMethod(Context context, String baseUrl, String path,Map<String,String> map, Observer<ResponseBody> observer){
        service = getRetrofit(context, baseUrl).create(HttpService.class);
        Observable observable = service.downloadFile(path,map);
        setSubscribe(observable,observer);
        return observable;
    }
    /**
     * 插入观察者
     *
     * @param <T>
     * @param observable
     * @param observer
     */
    public static <T> void setSubscribe(Observable<ResponseBody> observable, Observer<ResponseBody> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
    /**
     * DownSubscriber
     * @param <ResponseBody>
     */
    class DownSubscriber<ResponseBody> extends Subscriber<ResponseBody> {
        CallBack callBack;
        Context mContext;
        public DownSubscriber(Context context, CallBack callBack) {
            this.callBack = callBack;
            this.mContext = context;
        }

        @Override
        public void onStart() {
            super.onStart();
            if (callBack != null) {
                callBack.onStart();
            }
        }

        @Override
        public void onCompleted() {
            if (callBack != null) {
                callBack.onCompleted();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(mContext, (okhttp3.ResponseBody) responseBody);

        }
    }
}
