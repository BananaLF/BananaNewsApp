package com.lf.newsapp.NewsInfo.model;


import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.lf.newsapp.BaseWidget.Constant;
import com.lf.newsapp.Entity.ResultResponse;
import com.lf.newsapp.Entity.Video;
import com.lf.newsapp.Entity.VideoModel;
import com.lf.newsapp.Net.HttpData;
import com.lf.newsapp.NewsInfo.presenter.NewsInfoPresenter;
import com.lf.newsapp.Utils.WebAnalyerUtils;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
* Created by Administrator on 2017/03/24
*/

public class NewsInfoModelImpl implements NewsInfoModel{
    private Context mContext;
    private NewsInfoPresenter mNewsInfoPresenter;
    public static int flag = 0;
    public NewsInfoModelImpl(Context mContext, NewsInfoPresenter mNewsInfoPresenter) {
        this.mContext = mContext;
        this.mNewsInfoPresenter = mNewsInfoPresenter;
    }

    @Override
    public void loadNewsInfoWeb(String url,String type) {
        //去掉第一个/
        url = url.substring(1);
        if(type.equals("video")){
            loadNewInfoWebForVideo(url);
        }else {
            loadNewInfoWebForArtcle(url);
        }
    }

    private void loadNewInfoWebForArtcle(String url){
        HttpData.getInstance().getService().getVideoHtml(Constant.TODAY_NEW_URL+url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsInfoPresenter.onLoadDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String html = null;
                        try {
                            html = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                            mNewsInfoPresenter.onLoadDataError(e.getMessage());
                        }
                        mNewsInfoPresenter.onLoadArticleWeb(WebAnalyerUtils.analyisArticle(html));
                    }
                });
    }
    private void loadNewInfoWebForVideo(String url){
        HttpData.getInstance().getService().getVideoHtml(Constant.TODAY_NEW_URL+url)
                .flatMap(new Func1<ResponseBody, Observable<ResultResponse>>() {
                    @Override
                    public Observable<ResultResponse> call(ResponseBody response) {
                        String html = null;
                        try {
                            html = response.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                            flag = 1;
                            return Observable.error(e);
                        }

                        Pattern pattern = Pattern.compile("videoid:\'(.+)\'");
                        Matcher matcher = pattern.matcher(html);
                        if (matcher.find()) {
                            String videoId = matcher.group(1);
                            //将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()}，进行crc32加密。
                            String r = getRandom();
                            CRC32 crc32 = new CRC32();
                            String s = String.format(Constant.URL_VIDEO, videoId, r);
                            crc32.update(s.getBytes());
                            String crcString = crc32.getValue() + "";
                            String url = Constant.HOST_VIDEO + s + "&s=" + crcString;
                            Log.e("NewsInfoModelImpl",url);
                            flag = 3;
                            return HttpData.getInstance().getNewInstance().getService(mContext, Constant.HOST_VIDEO).getVideoData(url);
                        }
                        Log.e("NewsInfoModelImpl","未找到视频源");
                        flag = 2;
                        return Observable.empty();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.e("NewsInfoModelImpl","onCompleted");
                        if(flag == 1){
                            mNewsInfoPresenter.onLoadDataError("");
                        }else if(flag == 2){
                            mNewsInfoPresenter.onLoadEmptyData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("NewsInfoModelImpl",e.getMessage());
                        mNewsInfoPresenter.onLoadDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResultResponse videoModelResultResponse) {
//                        VideoModel.VideoListBean data = videoModelResultResponse.data.video_list;
                        VideoModel data = videoModelResultResponse.getData();
                        Log.e("NewsInfoModelImpl",data.video_id);
                        if (data.video_list.getVideo_3() != null) {
                            mNewsInfoPresenter.onLoadVideoWeb(updateVideo(data.video_list.getVideo_3()));
                            return;
                        }else if (data.video_list.getVideo_2() != null) {
                            mNewsInfoPresenter.onLoadVideoWeb(updateVideo(data.video_list.getVideo_2()));
                            return;
                        }else if (data.video_list.getVideo_1() != null) {
                            mNewsInfoPresenter.onLoadVideoWeb(updateVideo(data.video_list.getVideo_1()));
                            return;
                        }else{
                            mNewsInfoPresenter.onLoadDataError("无视频数据");
                        }
                        return ;
                    }});
    }
    private Video updateVideo(Video video) {
        video.main_url = new String(Base64.decode(video.main_url.getBytes(), Base64.DEFAULT));
        return video;
    }
    private String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}