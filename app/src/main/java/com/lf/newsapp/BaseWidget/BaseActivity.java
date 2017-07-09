/*
 * 文件名：BaseActivity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉本应用所有Activity的父类
 * 修改时间：2016年8月29日
 * 修改内容：〈修改内容〉
 */
package com.lf.newsapp.BaseWidget;
/**
 * 〈一句话功能简述〉本应用所有Activity的父类,方便退出所有Activity 〈功能详细描述〉
 *
 * @author luozf
 * @version [版本号, 2016年8月29日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lf.newsapp.BaseWidget.StatusManager.StatusLayoutManager;
import com.lf.newsapp.R;
import com.lf.newsapp.SplashActivity;
import com.lf.newsapp.Utils.ActivityCollector;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity {
    protected StatusLayoutManager statusLayoutManager;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(this instanceof SplashActivity)){
            initWindow();
        }
        setStatuseLayoutManager(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    private void setStatuseLayoutManager(@Nullable Bundle savedInstanceState){
        setContentView(R.layout.activity_base);
        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.main_rl);
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(getContentView())
                .emptyDataView(R.layout.activity_emptydata)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading)
                .netWorkErrorView(R.layout.activity_networkerror)
                .retryViewId(R.id.button_try)
                .build();

        mainLinearLayout.addView(statusLayoutManager.getRootLayout(), 0);

        statusLayoutManager.showContent();
    }
    protected abstract int getContentView();

    public StatusLayoutManager getStatusLayoutManager(){
        return statusLayoutManager;
    }
    /**
     * 禁止App字体随修改系统字体而发生变化
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 沉浸式效果
     */
    private void initWindow(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            setTranslucentStatus(true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintColor(getResources().getColor(R.color.status_blue));
//        tintManager.setStatusBarTintEnabled(true);
    }

    /**
     * 让内容向下错出一个状态栏的高度
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**别人的**/
    public ProgressDialog showProgress(String title, String message) {
        return showProgress(title, message, -1);
    }

    public ProgressDialog showProgress(String title, String message, int theme) {
        if (mProgressDialog == null) {
            if (theme > 0)
                mProgressDialog = new ProgressDialog(this, theme);
            else
                mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCanceledOnTouchOutside(false);// 不能取消
            mProgressDialog.setIndeterminate(true);// 设置进度条是否不明确
        }

        if (!TextUtils.isEmpty(title))
            mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        return mProgressDialog;
    }



    @Override
    protected void onStop() {
        super.onStop();


        mProgressDialog = null;
    }
}
