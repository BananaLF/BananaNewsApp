/*
 * 文件名：BaseFragment.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉Fragment基类
 * 修改时间：2016年9月20日
 * 修改内容：〈修改内容〉
 */
package com.lf.newsapp.BaseWidget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lf.newsapp.BaseWidget.StatusManager.StatusLayoutManager;
import com.lf.newsapp.R;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * 〈一句话功能简述〉Fragment基类，防止fragment hide show时重叠 〈功能详细描述〉
 *
 * @author luozf
 * @version [版本号, 2016年9月20日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BaseFragment extends RxFragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected StatusLayoutManager statusLayoutManager;
    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return setStatuseLayoutManager(inflater, container, savedInstanceState);
    }

    private View setStatuseLayoutManager(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_base,container,false);
        LinearLayout mainLinearLayout = (LinearLayout) view.findViewById(R.id.main_rl);
        statusLayoutManager = StatusLayoutManager.newBuilder(activity)
                .contentView(getContentView())
                .emptyDataView(R.layout.activity_emptydata)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading)
                .netWorkErrorView(R.layout.activity_networkerror)
                .retryViewId(R.id.button_try)
                .build();

        mainLinearLayout.addView(statusLayoutManager.getRootLayout(), 0);

        statusLayoutManager.showContent();
        return view;
    }
    public StatusLayoutManager getStatusLayoutManager(){
        return statusLayoutManager;
    }
    protected abstract int getContentView();
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }
}
