package com.lf.newsapp.Utils;

import com.lf.newsapp.Entity.NewsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */
public class NewsDataUtils {
    /**
     * 过滤掉图片
     * @param newsDatas
     * @return
     */
    public static List<NewsData> filterNoImage(List<NewsData> newsDatas){
        List<NewsData> result = new ArrayList<>();
        for(int i = 0;i<newsDatas.size();i++){
            NewsData newsData = newsDatas.get(i);
            if(newsData.image_url == null || newsData.image_url.length() == 0 || newsData.image_url.contains(" ")){
                if(newsData.image_list == null || newsData.image_list.size() == 0) {
                    continue;
                }else {
                    newsDatas.get(i).image_url = newsData.image_list.get(0).getUrl();
                }
            }
            result.add(newsData);
        }
        //过滤掉重复的新闻
        return filterReapt(result);
    }

    /**
     * 去掉重复
     * @param newsDatas
     * @return
     */
    public static  List<NewsData> filterReapt(List<NewsData> newsDatas){
        List<NewsData> result = new ArrayList<>();
        for(int i = 0;i<newsDatas.size();i++){
            int j = i+1;
            NewsData newsData = newsDatas.get(i);
            for(;j<newsDatas.size();j++){
                if(newsData.title.equals(newsDatas.get(j).title)){
                    break;
                }
            }
            if(j == newsDatas.size()){
                result.add(newsData);
            }
        }
        return result;
    }
}
