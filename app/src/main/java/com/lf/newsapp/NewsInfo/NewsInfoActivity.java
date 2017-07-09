package com.lf.newsapp.NewsInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.lf.newsapp.BaseWidget.BaseActivity;
import com.lf.newsapp.BaseWidget.Constant;
import com.lf.newsapp.BaseWidget.ProgressWebView;
import com.lf.newsapp.Entity.NewsData;
import com.lf.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/21.
 */
public class NewsInfoActivity extends BaseActivity  {
    @BindView(R.id.newsinfolinear)
    LinearLayout linearLayout;
    @BindView(R.id.webview)
    ProgressWebView webView;
    @BindView(R.id.newsinfotoolbar)
    Toolbar toolbar;
    private NewsData newsData;
    public static void startActivity(Context mContext,Bundle bundle){
        Intent intent = new Intent(mContext,NewsInfoActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        newsData = getIntent().getExtras().getParcelable("data");
        setToolbar();
        WebSettings webSettings = webView.getSettings();;
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                    //hitTestResult==null解决重定向问题
                     if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                         view.loadUrl(url);
                         return true;
                     }
                return super.shouldOverrideUrlLoading(view,url);
            }
        });
        String url = Constant.TODAY_NEW_URL+newsData.source_url.substring(1);
        Log.e("NewsInfoActivty",url);
        webView.loadUrl(url);
    }
    private void setToolbar(){
        // Title
        toolbar.setTitle(newsData.chinese_tag);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        // Navigation Icon 要設定在 setSupoortActionBar 才有作用
        // 否則會出現 back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    protected int getContentView() {
        return R.layout.activity_newsinfo;
    }
//    public TextView makeTextView(String context){
//        TextView textView = new TextView(this);
//        textView.setText("\t\t"+context);
//        textView.append("\n");
//        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//        linearLayout.addView(textView);
//        return  textView;
//    }
//
//    public ImageView makeImageView(String url){
//        ImageView imageView = new ImageView(this);
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//        linearLayout.addView(imageView);
//        Picasso.with(this).load(url).into(imageView);
//        return imageView;
//    }
//    @Override
//    public void loadNewsInfoWeb(String url) {
//
//    }
//
//    @Override
//    public void onLoadVideoWeb(Video videoBean) {
//
//    }
//
//    @Override
//    public void onLoadImageWeb(List<ImageUrl> urls) {
//
//    }
//
//    @Override
//    public void onLoadArticleWeb(Article article) {
//        makeTextView(article.getTitle());
//        makeTextView(article.getSource()+":"+ article.getTime());
//        for(int i = 0;i<article.getLineElements().size();i++){
//            if(article.getLineElements().get(i).getImgUrl() != null){
//                makeImageView(article.getLineElements().get(i).getImgUrl());
//            }else {
//                makeTextView(article.getLineElements().get(i).getDocElement());
//            }
//        }
//    }
//
//
//    @Override
//    public void hideProgress() {
//        getStatusLayoutManager().showContent();
//    }
//
//    @Override
//    public void showProgress() {
//        getStatusLayoutManager().showLoading();
//    }
//
//    @Override
//    public void onLoadEmptyData() {
//        getStatusLayoutManager().showEmptyData(this);
//    }
//
//    @Override
//    public void onLoadDataError(String message) {
//        getStatusLayoutManager().showNetWorkError(this);
//    }
//
//    @Override
//    public void onRetry() {
//        //presenter.loadNewsInfoWeb(newsData.source_url,newsData.tag_url);
//    }


}
