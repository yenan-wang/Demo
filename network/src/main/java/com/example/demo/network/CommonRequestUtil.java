package com.example.demo.network;

import androidx.annotation.NonNull;

import com.example.demo.network.interceptor.CommonRequestInterceptor;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class CommonRequestUtil {

    public static void request(String url, RequestResult requestResult) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.followRedirects(true);
        //okHttpClientBuilder.addInterceptor(new CommonRequestInterceptor());
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        Call call = okHttpClientBuilder.build().newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requestResult.fail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    Buffer buffer = responseBody.source().buffer().clone();
                    //buffer.readString()
                }
                //LogUtil.d(s);
            }
        });

        /*Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(url)
                .client(okHttpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.requestUrl("1808492017.mp3"*//*, ResponseBody.create(MediaType.parse("UTF-8"), "")*//*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        requestResult.success(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestResult.fail(throwable);
                    }
                });*/
    }

    public interface RequestResult {
        void success(Object o);

        void fail(Throwable throwable);
    }
}
