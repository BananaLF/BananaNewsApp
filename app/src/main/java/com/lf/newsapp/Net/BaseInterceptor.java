package com.lf.newsapp.Net;

import android.content.Context;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp中添加请求头插入拦截器
 *
 * @author lf
 * @version 1.0, 2016/10/13
 * @since 1.0
 */
public class BaseInterceptor implements Interceptor {
    private Map<String, String> headers;
    private Context context;
    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request()
                .newBuilder();
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
        builder.addHeader("Cache-Control", "max-age=0");
        builder.addHeader("Upgrade-Insecure-Requests", "1");
        builder.addHeader("X-Requested-With", "XMLHttpRequest");
        /**
         * 添加请求头
         */
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }



        return chain.proceed(builder.build());

    }
}