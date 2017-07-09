package com.lf.newsapp.Entity;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class ResultResponse {
    public VideoModel data;
    /**
     * data : {}
     * message : success
     * code : 0
     * total : 2
     */

    public String message;
    public int code;
    public int total;

    public VideoModel getData() {
        return data;
    }

    public void setData(VideoModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
    }
}