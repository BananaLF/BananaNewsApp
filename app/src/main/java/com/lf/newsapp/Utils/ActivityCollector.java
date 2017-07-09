package com.lf.newsapp.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理Activity出入栈的工具类
 * @author luozf
 * @version 1.0,2016-03-28
 * @since 1.0
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    /**
     * 入栈
     * @param activity
     */
    public static void addActivity(Activity activity)
    {
        if(!activities.contains(activity))
        {
            activities.add(activity);
        }
    }

    /**
     * 出栈
     * @param activity
     */
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }

    /**
     * 退出全部
     */
    public static void finishAll()
    {
        for(Activity activity:activities)
        {
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }
}
