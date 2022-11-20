package com.example.demo.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    private final SharedPreferences mSharedPreferences = AppUtil.getContext().getSharedPreferences("SP", Context.MODE_PRIVATE);
    private static SpUtil sSpUtil;

    private SpUtil() {

    }

    private static SpUtil getInstance() {
        if (sSpUtil == null) {
            synchronized (SpUtil.class) {
                if (sSpUtil == null) {
                    sSpUtil = new SpUtil();
                }
            }
        }
        return sSpUtil;
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getInstance().getEditor();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString (String key, String defaultValue) {
        return getInstance().mSharedPreferences.getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = getInstance().getEditor();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getString (String key, int defaultValue) {
        return getInstance().mSharedPreferences.getInt(key, defaultValue);
    }

    private SharedPreferences.Editor getEditor() {
        return getInstance().mSharedPreferences.edit();
    }
}
