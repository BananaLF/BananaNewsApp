package com.lf.newsapp.Entity;

import java.util.List;

/**
 * 文章实体类.
 */
public class Article {
    /**标题**/
    private String title;
    /**正文段落元素**/
    private List<LineElement> lineElements;
    /**来源**/
    private String source;
    /**时间**/
    private String time;
    public String getTime() {
        return time;
    }

    public Article setTime(String time) {
        this.time = time;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<LineElement> getLineElements() {
        return lineElements;
    }

    public Article setLineElements(List<LineElement> lineElements) {
        this.lineElements = lineElements;
        return this;
    }

    public String getSource() {
        return source;
    }

    public Article setSource(String source) {
        this.source = source;
        return this;
    }


}
