package com.lf.newsapp.Entity;

/**
 * 段落实体类
 */
public class LineElement {
    private String docElement;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public LineElement setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getDocElement() {
        return docElement;
    }

    public LineElement setDocElement(String docElement) {
        this.docElement = docElement;
        return this;
    }
}
