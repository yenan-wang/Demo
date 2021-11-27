package com.example.demo.network.interceptor;

import androidx.annotation.NonNull;

import com.example.demo.common.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.e(chain.request().headers());
        return chain.proceed(request);
    }
}
