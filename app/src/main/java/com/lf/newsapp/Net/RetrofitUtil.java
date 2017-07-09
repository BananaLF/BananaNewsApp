package com.lf.newsapp.Net;

import android.content.Context;
import android.util.Log;

import com.lf.newsapp.Utils.checkUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ***
 *
 * @author luozf
 * @version 1.0, 2016/10/12
 * @since 1.0
 */
public class RetrofitUtil {
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static final int DEFAULT_TIMEOUT = 5;
    private static Map<String,String> headers = null;
    private static CookieManger sCookieManger=null;
    private static Cache cache = null;
    private static File httpCacheDirectory;
    public static String baseUrl;
    /**
     * 获取Retrofit对象
     * @param mBaseUrl 基础Url 注意末尾没有/
     * @return
     */
    protected static Retrofit getRetrofit(Context context, String mBaseUrl) {
        baseUrl = mBaseUrl;
        if (null == mRetrofit) {
            if(null == sCookieManger){
                sCookieManger = new CookieManger(context);
            }
            if ( httpCacheDirectory == null) {
                httpCacheDirectory = new File(context.getCacheDir(), "Novate_Http_cache");
            }
            try {
                if (cache == null) {
                    cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
                }
            } catch (Exception e) {
                Log.e("OKHttp", "Could not create http cache", e);
            }
            if (null == mOkHttpClient) {
                headers = new HashMap<>();
                headers.put("Connection","keep-alive");
                mOkHttpClient = new OkHttpClient.Builder()
                        .addNetworkInterceptor(
                               new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                        .cookieJar(sCookieManger)
                        //.cache(cache)
                        .addInterceptor(new BaseInterceptor(checkUtils.checkNotNull(headers, "header == null")))
                        //.addInterceptor(new CaheInterceptor(context))
                        //.addNetworkInterceptor(new CaheInterceptor(context))
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                        // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                        .build();


            }


        }
        //Retrofit2后使用build设计模式
        mRetrofit = new Retrofit.Builder()
                //设置服务器路径
                .baseUrl(mBaseUrl)
                //添加转化库，默认是Gson
                .addConverterFactory(GsonConverterFactory.create())
                //添加回调库，采用RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //设置使用okhttp网络请求
                .client(mOkHttpClient)
                .build();
        return mRetrofit;
    }
}
