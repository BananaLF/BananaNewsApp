package com.lf.newsapp.Utils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public class StringUtils<T> {
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
}
