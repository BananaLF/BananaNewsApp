package com.lf.newsapp.Chat;

import android.content.Context;
import android.text.TextUtils;

import com.lf.newsapp.BaseRecyclerView.BaseAdapter;
import com.lf.newsapp.BaseRecyclerView.BaseViewHolder;
import com.lf.newsapp.Entity.ChatMessage;
import com.lf.newsapp.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
public class ChatAdapter extends BaseAdapter<ChatMessage> {

    private Context mContext;


    public ChatAdapter(Context mContext, int mLeftLayoutId, int mRightLayoutId, List<ChatMessage> mDatas) {
        super(mContext, mLeftLayoutId, mRightLayoutId, mDatas);
        this.mContext = mContext;
    }

    @Override
    public void convert(BaseViewHolder holder, ChatMessage chatMessage) {
            holder.setText(R.id.content,chatMessage.content);
    }

    @Override
    public int getItemViewTypeFromData(int position, ChatMessage chatMessage) {
        if(TextUtils.isEmpty(chatMessage.srcName)){
            return 0;
        }else {
            if(chatMessage.srcName.equals("left")){
                return RIGHT_TYPE;
            }else {
                return LEFT_TYPE;
            }
        }

    }
}
