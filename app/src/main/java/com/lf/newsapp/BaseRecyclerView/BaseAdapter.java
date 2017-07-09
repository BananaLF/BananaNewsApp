package com.lf.newsapp.BaseRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>
{
    public static final int NORMAL_TYPE = 0;
    public static final int LEFT_TYPE = 1;
    public static final int RIGHT_TYPE = 2;
    protected Context mContext;
    protected int mLayoutId;
    protected int mLeftLayoutId;
    protected int mRightLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private OnItemClickLister onItemClickLister;

    public interface OnItemClickLister{
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }

    public BaseAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public BaseAdapter(Context mContext, int mLeftLayoutId, int mRightLayoutId, List<T> mDatas) {
        this.mContext = mContext;
        this.mLeftLayoutId = mLeftLayoutId;
        this.mRightLayoutId = mRightLayoutId;
        this.mDatas = mDatas;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        BaseViewHolder viewHolder;
        if(viewType == NORMAL_TYPE) {
            viewHolder = BaseViewHolder.createViewHolder(mContext, parent, mLayoutId);
        }else if(viewType == LEFT_TYPE){
            viewHolder = BaseViewHolder.createViewHolder(mContext, parent, mLeftLayoutId);
        }else if(viewType == RIGHT_TYPE){
            viewHolder = BaseViewHolder.createViewHolder(mContext, parent, mRightLayoutId);
        }else {
            viewHolder = BaseViewHolder.createViewHolder(mContext, parent, mLayoutId);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position)
    {
        if(onItemClickLister != null){
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    onItemClickLister.onItemClick(view,position);
                }
            });
            holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getLayoutPosition();
                    onItemClickLister.onItemLongClick(view,position);
                    return false;
                }
            });
        }
//        Log.e("BaseAdapter",position+"");
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(BaseViewHolder holder, T t);
    public abstract int getItemViewTypeFromData(int position,T t);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewTypeFromData(position,mDatas.get(position));
    }

    public void setOnItemClickListener(OnItemClickLister onItemClickLister){
        this.onItemClickLister = onItemClickLister;
    }
    public  void notifyDataChangeForRefresh(List<T> newDatas){
        mDatas.clear();
        notifyDataChangeForLoadMore(newDatas);
    }
    public void notifyDataChangeForLoadMore(List<T> newDatas){
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    public T getItemData(int position){
        return mDatas.get(position);
    }
}
