package com.example.demo.network.plugin;

import com.example.demo.common.utils.LogUtil;
import com.example.demo.common.utils.ReflectUtils;
import com.example.demo.network.interceptor.AbsInterceptor;
import com.example.demo.network.interceptor.NetInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class OkHttpHook {

    private static final String TAG = "OkHttpHook";
    private volatile static OkHttpHook sInstance;

    private OkHttpHook() {

    }

    public static OkHttpHook getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpHook.class) {
                if (sInstance == null) {
                    sInstance = new OkHttpHook();
                }
            }
        }
        return sInstance;
    }

    public void addInterceptor(OkHttpClient client) {
        dealInterceptor(client);
    }

    public OkHttpClient addInterceptorWithReturn(OkHttpClient client) {
        dealInterceptor(client);
        return client;
    }

    private void dealInterceptor(OkHttpClient client) {
        LogUtil.d(TAG, "OkHttpHook addInterceptor.");
        List<Interceptor> interceptors = new ArrayList<>(client.interceptors());
        List<Interceptor> networkInterceptors = new ArrayList<>(client.networkInterceptors());
        noDuplicateAdd(interceptors, new NetInterceptor());
        ReflectUtils.reflect(client).field("interceptors", interceptors);
        ReflectUtils.reflect(client).field("networkInterceptors", networkInterceptors);

        LogUtil.d(TAG, "addInterceptor, success!");
    }

    private void noDuplicateAdd(List<Interceptor> interceptors, AbsInterceptor interceptor) {
        LogUtil.d(TAG, "noDuplicateAdd.");
        boolean hasInterceptor = false;
        for (Interceptor i : interceptors) {
            if (i instanceof AbsInterceptor) {
                AbsInterceptor absInterceptor = (AbsInterceptor) i;
                LogUtil.d(TAG, "noDuplicateAdd, tag_interceptor=" + absInterceptor.TAG_INTERCEPTOR);
                if (interceptor.TAG_INTERCEPTOR.equals(absInterceptor.TAG_INTERCEPTOR)) {
                    hasInterceptor = true;
                    break;
                }
            }
        }

        if (!hasInterceptor) {
            interceptors.add(interceptor);
            LogUtil.d(TAG, "noDuplicateAdd, add:" + interceptor.TAG_INTERCEPTOR);
        }
    }
}
