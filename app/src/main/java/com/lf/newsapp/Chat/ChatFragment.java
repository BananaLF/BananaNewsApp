package com.lf.newsapp.Chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.kevin.ultimaterecyclerview.UltimateRecyclerView;
import com.kevin.wraprecyclerview.WrapRecyclerView;
import com.lf.newsapp.BaseRecyclerView.TmallFooterLayout;
import com.lf.newsapp.Entity.ChatMessage;
import com.lf.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/19.
 */
public class ChatFragment extends Fragment {
    @BindView(R.id.chat)
    UltimateRecyclerView mRecyclerView;
    WrapRecyclerView mWrapRecyclerView;
    private ChatAdapter chatAdapter;
    private static List<ChatMessage> chatMessages = new ArrayList<>();
    public static ChatFragment getFragment(Bundle bundle){
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        return chatFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for(int i = 0;i<10;i++){
            ChatMessage chatmessage = new ChatMessage();
            if(i %3 == 0){
                chatmessage.srcName = "left";
            }else {
                chatmessage.srcName = "right";
            }
            chatmessage.dstName = "hehe"+i;
            chatmessage.content = "我是一条消息";
            for (int j = 0;j<=i;j++) {
                chatmessage.content += "我是一条消息";
            }
            chatMessages.add(chatmessage);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        ButterKnife.bind(this,view);
        chatAdapter = new ChatAdapter(getActivity(),R.layout.left_chat_item,R.layout.right_chat_item,chatMessages);
        mWrapRecyclerView = mRecyclerView.getRefreshableView();
        mRecyclerView.setSecondFooterLayout(new TmallFooterLayout(getActivity()));
        mWrapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));//
        //设置adapter
        mWrapRecyclerView.setAdapter(chatAdapter);
        //设置Item增加、移除动画
        mWrapRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WrapRecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<WrapRecyclerView> refreshView) {
                Log.e("HomeListFragment","onRefresh");
                initData();
                mRecyclerView.onRefreshComplete();
                chatAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {

                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        List<ChatMessage> c = new ArrayList<ChatMessage>();
                        Log.e("HomeListFragment","onload");
                        ChatMessage chatmessage = new ChatMessage();
                        chatmessage.srcName = "left";
                        chatmessage.dstName = "hehe";
                        chatmessage.content = "我是一条消息";
                        c.add(chatmessage);
                        chatAdapter.notifyDataChangeForLoadMore(c);
                    }
                });

            }
        });
        return view;
    }


}
