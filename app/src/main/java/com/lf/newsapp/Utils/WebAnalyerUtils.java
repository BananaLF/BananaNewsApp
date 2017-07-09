package com.lf.newsapp.Utils;

import android.util.Log;

import com.lf.newsapp.BaseWidget.Constant;
import com.lf.newsapp.Entity.Article;
import com.lf.newsapp.Entity.LineElement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */
public class WebAnalyerUtils {
    private static final String TAG = "WebAnalyerUtils";
    public static Article analyisArticle(String html){
        Article article = new Article();
        //将相对路径转换为绝对路径
        Document doc = Jsoup.parse(html, Constant.TODAY_NEW_URL);
        Element articleMain = doc.getElementById("article-main");

        String title = getElementsByOnlyClass(articleMain.getElementsByClass("article-title")).text();
        article.setTitle(title);

        Element info = getElementsByOnlyClass(articleMain.getElementsByClass("articleInfo"));
        String source = getElementsByOnlyClass(info.getElementsByClass("src")).text();
        String time = getElementsByOnlyClass(info.getElementsByClass("time")).text();
        article.setSource(source)
                .setTime(time);


        Element arctileContent = getTodayConentElement(articleMain);
        article.setLineElements(getContent(arctileContent));
        Log.e("WebAnalyerUtils",title);
        return article;
    }

    /**
     * 获取手机人民网内容元素
     * @param element
     * @return
     */
    public Element getPhonePeopleWebConentElement(Element element){
        Element arctileContent = getElementsByOnlyClass(element.getElementsByClass("content"));
        return arctileContent;
    }
    /**
     * 获取今日头条内容元素
     * @param element
     * @return
     */
    public static Element getTodayConentElement(Element element){
        Element arctileContent = getElementsByOnlyClass(element.getElementsByClass("article-content"));
        return arctileContent;
    }
    /**
     * 获取正文内容（由于正文内容基本都是<p>格式</p>）
     * @param arctileContent
     * @return
     */

    public static List<LineElement> getContent(Element arctileContent){
        //获取属于正文的Elements
        Elements elements = arctileContent.getAllElements();
        List<LineElement> lineElements = new ArrayList<>();
        for(Element element : elements){
            LineElement lineElement = new LineElement();
            if(element.tagName().equals("img")){
                lineElement.setImgUrl(element.attr("src"));
            }else {
                lineElement.setDocElement(element.text());
            }
            lineElements.add(lineElement);
        }
        return lineElements;
    }
    /**
     * 将通过ByClass方法（classname唯一）获取到的Elements转换为Element
     * @param elements
     * @return
     */
    public static Element getElementsByOnlyClass(Elements elements){
        return elements.get(0);
    }
}
