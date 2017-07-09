package com.lf.newsapp.Entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public class ResultNews {


    /**
     * has_more : true
     * message : success
     * data : [NewsData...]
     * next : {"max_behot_time":1490180388}
     */

    private boolean has_more;
    private String message;
    private Next next;
    private List<NewsData> data;

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public List<NewsData> getData() {
        return data;
    }

    public void setData(List<NewsData> data) {
        this.data = data;
    }

    public static class Next {
        /**
         * max_behot_time : 1490180388
         */

        private int max_behot_time;

        public int getMax_behot_time() {
            return max_behot_time;
        }

        public void setMax_behot_time(int max_behot_time) {
            this.max_behot_time = max_behot_time;
        }
    }
}
