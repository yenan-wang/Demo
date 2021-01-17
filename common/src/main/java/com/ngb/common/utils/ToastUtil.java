package com.ngb.common.utils;

import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {

    public static void toastShort(String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast.makeText(AppUtil.getContext(), content, Toast.LENGTH_SHORT).show();
        }
    }

    public static void toastLong(String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast.makeText(AppUtil.getContext(), content, Toast.LENGTH_LONG).show();
        }
    }
}
