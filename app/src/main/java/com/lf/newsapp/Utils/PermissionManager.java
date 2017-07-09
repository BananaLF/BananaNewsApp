package com.lf.newsapp.Utils;

import android.content.Context;


/**
 * Created by Administrator on 2017/3/20.
 */
public class PermissionManager {
    //检测权限
    public static void checkPermission(final Context mContext, String permission){
//        RxPermissions.getInstance(mContext).requestEach(Manifest.permission.INTERNET,
//                Manifest.permission.ACCESS_NETWORK_STATE)
//                .subscribe(new Observer<Permission>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Permission permission) {
//                        if (permission.name.equals(Manifest.permission.INTERNET)) {
//                            if (!permission.granted) {
//                                Toast.makeText(mContext, "INTERNET您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } else if (permission.name.equals(Manifest.permission.ACCESS_NETWORK_STATE)) {
//                            if (!permission.granted) {
//                                Toast.makeText(mContext, "ACCESS_NETWORK_STATE您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}
