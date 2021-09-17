package com.example.demo.common.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;

public class SystemUtil {

    public static boolean isRTL(Context context) {
        return context.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    public static boolean isNightMode(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        int currentMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return Configuration.UI_MODE_NIGHT_YES == currentMode;
    }
}
