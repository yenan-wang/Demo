package com.example.demo.common.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {

    private static final String TAG = "AssetsUtil";

    public static String readAssetsFile(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String content = new String(bytes);
            return content;
        } catch (IOException e) {
            LogUtil.e(TAG, "readAssetsFile, " + e.getMessage());
        }
        return null;
    }
}
