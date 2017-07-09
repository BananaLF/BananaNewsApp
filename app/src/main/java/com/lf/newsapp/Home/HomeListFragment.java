package com.lf.newsapp.Home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.kevin.ultimaterecyclerview.UltimateRecyclerView;
import com.kevin.wraprecyclerview.WrapRecyclerView;
import com.lf.newsapp.BaseRecyclerView.BaseAdapter;
import com.lf.newsapp.BaseRecyclerView.TmallFooterLayout;
import com.lf.newsapp.BaseWidget.BaseFragment;
import com.lf.newsapp.BaseWidget.StatusManager.OnRetryListener;
import com.lf.newsapp.Entity.NewsData;
import com.lf.newsapp.Home.presenter.NewsPresenter;
import com.lf.newsapp.Home.presenter.NewsPresenterImpl;
import com.lf.newsapp.Home.view.NewsView;
import com.lf.newsapp.NewsInfo.NewsInfoActivity;
import com.lf.newsapp.NewsInfo.VideoPalyActivity;
import com.lf.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/21.
 */
public class HomeListFragment extends BaseFragment implements NewsView,OnRetryListener {

    @BindView(R.id.newslist)
    UltimateRecyclerView mRecyclerView;
    WrapRecyclerView mWrapRecyclerView;
    private NewsAdapter adapter;
    private static List<NewsData> mNewsData = new ArrayList<>();
    private NewsPresenter presenter;
    private static String tag ;
    private boolean isVisable = false;
    public static HomeListFragment getFragment( Bundle bundle){
        HomeListFragment homeListFragment = new HomeListFragment();
        homeListFragment.setArguments(bundle);
        return homeListFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(tag,getArguments().getString("type")+":onViewCreated");
        if(isVisable) {
            initData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tag = this.toString();
        Log.e(tag,getArguments().getString("type")+":onResume");

    }

    private void initData() {
        presenter = new NewsPresenterImpl(this,getActivity());
        presenter.loadNewsData(getArguments().getString("type"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //View view = inflater.inflate(R.layout.fragment_home_list,container,false);
        ButterKnife.bind(this,view);
        adapter = new NewsAdapter(getActivity(),R.layout.news_item,mNewsData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickLister() {
            @Override
            public void onItemClick(View view, int position) {
                NewsData newsData = adapter.getItemData(position-1);
                Log.e(tag,newsData.title+":"+position);
                Log.e(tag,((TextView)view.findViewById(R.id.content)).getText().toString());
                Log.e(tag,getArguments().getString("type")+":OnItemClickLister");

                Bundle bundle = new Bundle();
                bundle.putParcelable("data", newsData);
                if(newsData != null && newsData.tag_url!= null && newsData.tag_url.equals("video")){
                        VideoPalyActivity.startActivity(getActivity(),bundle);
                }else {
                    //NewsInfoAjaxActivity.startActivity(getActivity(),bundle);
                    NewsInfoActivity.startActivity(getActivity(), bundle);
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mWrapRecyclerView = mRecyclerView.getRefreshableView();
        mRecyclerView.setSecondFooterLayout(new TmallFooterLayout(getActivity()));
        mWrapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));//
        //设置adapter
        mWrapRecyclerView.setAdapter(adapter);
        //设置Item增加、移除动画
        mWrapRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mWrapRecyclerView.addItemDecoration( new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WrapRecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<WrapRecyclerView> refreshView) {
                Log.e("HomeListFragment","onRefresh");
                initData();
            }
        });
        mRecyclerView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Log.e("HomeListFragment","onload");
                presenter.loadMoreNewsData(getArguments().getString("type"));
            }
        });
        //FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.news_item,container);
        //mRecyclerView.setHeaderLayout((LoadingLayoutBase) frameLayout);
        return view;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_list;
    }

    /**
     * 加载获取到的数据
     * @param newsDatas
     */
    @Override
    public void onloadNewsData(List<NewsData> newsDatas) {
        mRecyclerView.onRefreshComplete();
        adapter.notifyDataChangeForRefresh(newsDatas);
    }

    @Override
    public void onLoadMoreNewsData(List<NewsData> newsDatas) {
        //清楚相同的新闻
        for(int i = 0;i<newsDatas.size();i++){
            NewsData newsData = newsDatas.get(i);
            for(int j = 0;j<mNewsData.size();j++){
                if(newsData.title.equals(mNewsData.get(j).title)){
                    newsDatas.remove(i);
                    break;
                }
            }
        }
        adapter.notifyDataChangeForLoadMore(newsDatas);
//        mNewsData.addAll(newsDatas);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
            //加载进度条
            //目前通过添加根部局一个进度条部件实现
        getStatusLayoutManager().showLoading();
    }

    @Override
    public void hideProgress() {
        getStatusLayoutManager().showContent();
    }

    @Override
    public void onLoadEmptyData() {
        Toast.makeText(getActivity(),"未获取到新数据",Toast.LENGTH_SHORT);
        getStatusLayoutManager().showEmptyData(this);
    }

    @Override
    public void onLoadDataError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT);
        getStatusLayoutManager().showNetWorkError(this);
    }

    @Override
    public void onRetry() {
        initData();
    }

    /**
     * 懒加载等fragmentr可见时再进行数据加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisable = getUserVisibleHint();
        if(getUserVisibleHint() && getStatusLayoutManager() != null){
             initData();
        }
    }
}
