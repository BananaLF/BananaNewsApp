package com.lf.newsapp.Net;

/**
 * ***
 *
 * @author luozf
 * @version 1.0, 2016/10/13
 * @since 1.0
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.lf.newsapp.Utils.NetworkUtil;
import rx.Subscriber;

/**
 * BaseSubscriber
 * Created by Tamic on 2016-7-15.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    /**
     * 数据请求开始
     */
    @Override
    public void onStart() {
        super.onStart();

        if (!NetworkUtil.isNetworkAvailable(context)) {

            Toast.makeText(context, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, NetworkUtil.getNetStateString(context), Toast.LENGTH_SHORT).show();
            // 一定好主动调用下面这一句
            onCompleted();

            return;
        }else {
            Log.e("BaseSubscriber", "开始获取数据");
            //Toast.makeText(context,"开始获取数据", Toast.LENGTH_SHORT).show();
        }

        // 显示进度条
        //showLoadingProgress();
    }

    /**
     * 数据请求完成
     */
    @Override
    public void onCompleted() {
        //关闭等待进度条
        //closeLoadingProgress();

        Log.e("BaseSubscriber", "请求完成 ");

    }

    /**
     * 异常处理
     * @param e
     */
    @Override
    public void onError(Throwable e) {

        Log.e("BaseSubscriber", e.getMessage());
        // todo error somthing
    }

}
