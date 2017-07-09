package com.lf.newsapp.Net;

import android.content.Context;

/**
 * DownSubscriber
 * @param <ResponseBody>
 */
public class DownSubscriber<ResponseBody> extends BaseSubscriber<ResponseBody> {
    CallBack callBack;
    Context mContext;
    public DownSubscriber(Context context, CallBack callBack) {
        super(context);
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