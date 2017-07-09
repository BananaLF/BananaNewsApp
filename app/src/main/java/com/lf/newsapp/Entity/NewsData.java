package com.lf.newsapp.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public class NewsData implements Parcelable {
    /**
     * chinese_tag : 体育
     * media_avatar_url : http://p3.pstatp.com/large/bc2000a3611645791f7
     * article_genre : article
     * tag_url : news_sports
     * title : 13岁日本籍华裔立誓击败中国夺奥运金牌，来中国被打到怀疑人生
     * middle_mode : false
     * gallary_image_count : 5
     * image_list : [{"url":"http://p2.pstatp.com/list/ef5000fa3ce130815bc"},{"url":"http://p9.pstatp.com/list/ef5000fa3f737996347"},{"url":"http://p3.pstatp.com/list/e59000f7ce3e08668b6"}]
     * behot_time : 1479438367
     * source_url : /group/6351937181648732418/
     * source : 苏老湿聊体育
     * more_mode : true
     * is_feed_ad : false
     * comments_count : 9000
     * has_gallery : false
     * single_mode : true
     * image_url : http://p2.pstatp.com/list/190x124/ef5000fa3ce130815bc
     * group_id : 6351937181648732418
     * is_diversion_page : false
     * media_url : http://toutiao.com/m50282400523/
     */

    public String chinese_tag;
    public String media_avatar_url;
    public String article_genre;
    public String tag_url;
    public String title;
    public boolean middle_mode;
    public int gallary_image_count;
    public long behot_time;
    public String source_url;
    public String source;
    public boolean more_mode;
    public boolean is_feed_ad;
    public int comments_count;
    public boolean has_gallery;
    public boolean single_mode;
    public String image_url;
    public String group_id;
    public boolean is_diversion_page;
    public String media_url;
    public String video_duration_str;

    //TODO 目前不做视频2017/36/22

    //public Video video;

    /**
     * url : http://p2.pstatp.com/list/ef5000fa3ce130815bc
     */

    public List<ImageUrl> image_list;

    protected NewsData(Parcel in) {
        chinese_tag = in.readString();
        media_avatar_url = in.readString();
        article_genre = in.readString();
        tag_url = in.readString();
        title = in.readString();
        middle_mode = in.readByte() != 0;
        gallary_image_count = in.readInt();
        behot_time = in.readLong();
        source_url = in.readString();
        source = in.readString();
        more_mode = in.readByte() != 0;
        is_feed_ad = in.readByte() != 0;
        comments_count = in.readInt();
        has_gallery = in.readByte() != 0;
        single_mode = in.readByte() != 0;
        image_url = in.readString();
        group_id = in.readString();
        is_diversion_page = in.readByte() != 0;
        media_url = in.readString();
        video_duration_str = in.readString();
    }

    public static final Creator<NewsData> CREATOR = new Creator<NewsData>() {
        @Override
        public NewsData createFromParcel(Parcel in) {
            return new NewsData(in);
        }

        @Override
        public NewsData[] newArray(int size) {
            return new NewsData[size];
        }
    };

    @Override
    public String toString() {
        return "News{" +
                "chinese_tag='" + chinese_tag + '\'' +
                ", media_avatar_url='" + media_avatar_url + '\'' +
                ", article_genre='" + article_genre + '\'' +
                ", tag_url='" + tag_url + '\'' +
                ", title='" + title + '\'' +
                ", middle_mode=" + middle_mode +
                ", gallary_image_count=" + gallary_image_count +
                ", behot_time=" + behot_time +
                ", source_url='" + source_url + '\'' +
                ", source='" + source + '\'' +
                ", more_mode=" + more_mode +
                ", is_feed_ad=" + is_feed_ad +
                ", comments_count=" + comments_count +
                ", has_gallery=" + has_gallery +
                ", single_mode=" + single_mode +
                ", image_url='" + image_url + '\'' +
                ", group_id='" + group_id + '\'' +
                ", is_diversion_page=" + is_diversion_page +
                ", media_url='" + media_url + '\'' +
                ", video_duration_str='" + video_duration_str + '\'' +
                //", video=" + video +
                ", image_list=" + image_list +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(chinese_tag);
        parcel.writeString(media_avatar_url);
        parcel.writeString(article_genre);
        parcel.writeString(tag_url);
        parcel.writeString(title);
        parcel.writeByte((byte) (middle_mode ? 1 : 0));
        parcel.writeInt(gallary_image_count);
        parcel.writeLong(behot_time);
        parcel.writeString(source_url);
        parcel.writeString(source);
        parcel.writeByte((byte) (more_mode ? 1 : 0));
        parcel.writeByte((byte) (is_feed_ad ? 1 : 0));
        parcel.writeInt(comments_count);
        parcel.writeByte((byte) (has_gallery ? 1 : 0));
        parcel.writeByte((byte) (single_mode ? 1 : 0));
        parcel.writeString(image_url);
        parcel.writeString(group_id);
        parcel.writeByte((byte) (is_diversion_page ? 1 : 0));
        parcel.writeString(media_url);
        parcel.writeString(video_duration_str);
    }
}
