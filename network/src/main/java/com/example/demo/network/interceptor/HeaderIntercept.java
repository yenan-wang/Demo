package com.example.demo.network.interceptor;

import androidx.annotation.NonNull;

import com.example.demo.common.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderIntercept implements Interceptor {
    public static final String HEAD_PKG_NAME = "pkgName";
    public static final String HEAD_VERSION_CODE = "version_code";
    public static final String HEAD_APP_VERSION = "app_version";
    public static final String HEAD_ANDROID_VERSION = "android_version";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader(HEAD_PKG_NAME, "ngb_pkg_name")
                .addHeader(HEAD_VERSION_CODE, "1021")
                .addHeader(HEAD_APP_VERSION, "1.0.0")
                .addHeader(HEAD_ANDROID_VERSION, "10")
                .build();
        LogUtil.d(request.headers());
        return chain.proceed(request);
    }
}
