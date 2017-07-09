package com.lf.newsapp.Home;

import android.content.Context;
import android.widget.ImageView;

import com.lf.newsapp.BaseRecyclerView.BaseAdapter;
import com.lf.newsapp.BaseRecyclerView.BaseViewHolder;
import com.lf.newsapp.Entity.NewsData;
import com.lf.newsapp.R;
import com.lf.newsapp.Utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public class NewsAdapter extends BaseAdapter<NewsData> {
    private Context mContext;

    public NewsAdapter(Context context, int layoutId, List<NewsData> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
    }



    @Override
    public void convert(BaseViewHolder holder, NewsData newsData) {
        holder.setText(R.id.content,newsData.title);
        holder.setText(R.id.date, DateUtils.getFormatDate(newsData.behot_time));
        holder.setText(R.id.authorname,newsData.source);
        Picasso.with(mContext).load(newsData.image_url).into((ImageView) holder.getView(R.id.thumb));
//        Log.e("NewsAdapter",newsData.title);
    }

    @Override
    public int getItemViewTypeFromData(int position, NewsData newsData) {
        return BaseAdapter.NORMAL_TYPE;
    }
}
