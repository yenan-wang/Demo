package com.ngb.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.ngb.common.BaseApplication;
import com.ngb.common.BuildConfig;

import java.util.List;

public class AppUtil {
    private static final String TAG = AppUtil.class.getSimpleName();
    private static final boolean sIsRunAlone = BuildConfig.IS_RUN_ALONE;

    public static boolean isIsRunAlone() {
        return sIsRunAlone;
    }

    public static Context getContext() {
        return BaseApplication.getContext();
    }

    public static boolean checkAppIsInstalled(String packageName) {
        return checkAppIsInstalled(getContext(), packageName);
    }

    public static boolean checkAppIsInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.d(TAG, e.getMessage());
        }
        return packageInfo != null;
    }

    public static boolean isDebug() {
        ApplicationInfo info = getContext().getApplicationInfo();
        return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public boolean checkHasLaunch(Context context, String pkgName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfoList.size(); i++) {
            if (processInfoList.get(i).processName.equals(pkgName)) {
                return true;
            }
        }
        return false;
    }

}
