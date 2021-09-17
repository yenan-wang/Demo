package com.example.demo.common.utils;

public class ClickUtil {
    private static final int CLICK_INTERVAL = 500;
    private static long sLastClickTime;

    public static boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        long clickTimeDuration = clickTime - sLastClickTime;
        if ((clickTimeDuration > 0) && (clickTimeDuration < CLICK_INTERVAL)) {
            return true;
        }
        sLastClickTime = clickTime;
        return false;
    }
}
