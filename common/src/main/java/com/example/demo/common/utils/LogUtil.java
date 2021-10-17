package com.example.demo.common.utils;

import android.util.Log;

public class LogUtil {
    public static final String DEFAULT_TAG = "ngb_log";

    public static void i(Object info) {
        i(DEFAULT_TAG, info);
    }

    public static void i(String tag, Object info) {
        if (AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.i(tag, s);
        }
    }

    public static void d(Object info) {
        d(DEFAULT_TAG, info);
    }

    public static void d(String tag, Object info) {
        if (AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.d(tag, s);
        }
    }

    public static void v(Object info) {
        v(DEFAULT_TAG, info);
    }

    public static void v(String tag, Object info) {
        if (AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.v(tag, s);
        }
    }


    public static void w(Object info) {
        w(DEFAULT_TAG, info);
    }

    public static void w(String tag, Object info) {
        if (AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.w(tag, s);
        }
    }

    public static void e(Object info) {
        e(DEFAULT_TAG, info);
    }

    public static void e(String tag, Object info) {
        if (AppUtil.isDebug()) {
            String s = String.valueOf(info);
            Log.e(tag, s);
        }
    }
}
