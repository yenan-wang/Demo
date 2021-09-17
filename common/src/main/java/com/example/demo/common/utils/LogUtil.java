package com.example.demo.common.utils;

import android.util.Log;

public class LogUtil {

    public static void i(String tag, Object info) {
        if(AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.i(tag, s);
        }
    }

    public static void d(String tag, Object info) {
        if(AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.d(tag, s);
        }
    }

    public static void v(String tag, Object info) {
        if(AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.v(tag, s);
        }
    }

    public static void w(String tag, Object info) {
        if(AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.w(tag, s);
        }
    }

    public static void e(String tag, Object info) {
        if(AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.e(tag, s);
        }
    }
}
