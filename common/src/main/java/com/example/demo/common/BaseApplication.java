package com.example.demo.common;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.demo.common.utils.AppUtil;

public class BaseApplication extends Application {
    private static Application sApplication;
    private static BaseApplication sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        if (AppUtil.isDebug()) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(getInstance());
    }

    public static Application getInstance() {
        return sApplication;
    }

    public static Context getContext() {
        return sContext;
    }

}
