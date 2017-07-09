package com.lf.newsapp;

import android.content.Intent;
import android.os.Bundle;

import com.lf.newsapp.BaseWidget.BaseActivity;
import com.lf.newsapp.Home.HomeActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //三秒延时

        Observable.just("1")//Observable不能直接.delay()
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Intent intent = null;
                        intent = new Intent(SplashActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }
}
