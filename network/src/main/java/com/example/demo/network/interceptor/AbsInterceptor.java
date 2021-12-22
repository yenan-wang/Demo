package com.example.demo.network.interceptor;

import okhttp3.Interceptor;

abstract public class AbsInterceptor implements Interceptor {

    public String TAG_INTERCEPTOR = this.getClass().getSimpleName();
}
