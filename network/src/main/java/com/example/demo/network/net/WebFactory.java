package com.example.demo.network.net;

import com.example.demo.network.interceptor.HeaderIntercept;
import com.example.demo.network.interceptor.NetInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebFactory {


    private static final int DEFAULT_TIMEOUT = 10;
    private volatile static WebFactory sInstance;
    private String HOST = "http://www.kuaidi100.com";
    private Retrofit mRetrofit;
    private boolean mIsUseCustomGsonConverter;

    private WebFactory() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        //添加默认的超时设置
        okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //添加各种拦截器
        okHttpBuilder.addInterceptor(new HeaderIntercept());
        okHttpBuilder.addInterceptor(new NetInterceptor());

        OkHttpClient okHttpClient = okHttpBuilder.build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(mIsUseCustomGsonConverter ? CustomGsonConverterFactory.create() : GsonConverterFactory.create())
                .build();
    }

    public boolean isUseCustomGsonConverter() {
        return mIsUseCustomGsonConverter;
    }

    public void setUseCustomGsonConverter(boolean useCustomGsonConverter) {
        mIsUseCustomGsonConverter = useCustomGsonConverter;
    }

    public static WebFactory getInstance() {
        if (sInstance == null) {
            synchronized (WebFactory.class) {
                if (sInstance == null) {
                    sInstance = new WebFactory();
                }
            }
        }
        return sInstance;
    }

    public <T> T getService(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
