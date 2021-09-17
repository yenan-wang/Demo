package com.example.demo.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.example.demo.common.BaseApplication;
import com.example.demo.common.BuildConfig;

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

    public static boolean checkHasLaunch(Context context, String pkgName) {
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

    /**
     * 获取当前应用程序的包名
     *
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppPackageName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }

    /**
     * 获取程序图标
     *
     * @param context 上下文
     * @param packageName 应用包名
     * @return 返回该包名应用的图标
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            //获取到应用信息
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.d(TAG, "error:" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取程序的版本号
     *
     * @param context 上下文
     * @param packageName 包名
     * @return 返回该应用的版本
     */
    public static String getAppVersion(Context context, String packageName) {
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.d(TAG, "error:" + e.getMessage());
        }
        return "";
    }


    /**
     * 获取程序的名字
     *
     * @param context 上下文
     * @param packageName 包名
     * @return 返回该包名应用的名字
     */
    public static String getAppName(Context context, String packageName) {
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.d(TAG, "error:" + e.getMessage());

        }
        return "";
    }

    /**
     * 获取程序的权限
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 该包名申请的权限
     */
    public static String[] getAllPermissions(Context context, String packageName) {
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            //获取到所有的权限
            return packageInfo.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.d(TAG, "error:" + e.getMessage());
        }
        return null;
    }


    /**
     * 获取程序的签名
     *
     * @param context 上下文
     * @param packageName 包名
     * @return 该包名的签名
     */
    public static String getAppSignature(Context context, String packageName) {
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            //获取当前应用签名
            return packageInfo.signatures[0].toCharsString();

        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.d(TAG, "error:" + e.getMessage());
        }
        return packageName;
    }

    /**
     * 获取当前展示的Activity名称
     * @param context 上下文
     * @return 返回当前展示的activity名称
     */
    private static String getCurrentActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

}
