package com.example.demo.business.net;

import androidx.annotation.Nullable;

import com.example.demo.business.callBack.IpInfoLoadTasksCallback;
import com.example.demo.business.callBack.LoadTasksCallBack;
import com.example.demo.business.model.IpInfo;
import com.ngb.wyn.common.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;

public class IpInfoTask implements NetTask<String, IpInfo> {

    private static IpInfoTask INSTANCE = null;
    private static final String HOST = "https://ip.taobao.com/service/getIpInfo.php";

    private IpInfoTask() {
    }

    public static IpInfoTask getInstance() {
        if (INSTANCE == null) {
            synchronized (IpInfoTask.class) {
                if (INSTANCE == null) {
                    INSTANCE = new IpInfoTask();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void execute(String param, LoadTasksCallBack<IpInfo> callBack) {
        new RequestBody(){
            @Override
            public long contentLength() throws IOException {
                return super.contentLength();
            }

            @Nullable
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        };
        Request.Builder builder = new Request.Builder();
        builder.url("https://ip.taobao.com/service/getIpInfo.php")
                .post(new FormBody.Builder().add("ip", "59.108.54.37").build());
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                BufferedSource bufferedSource = responseBody.source();
                bufferedSource.request(Long.MAX_VALUE);
                MediaType mediaType = responseBody.contentType();
                if (mediaType != null) {
                    Charset charset = mediaType.charset(StandardCharsets.UTF_8);
                    String s = bufferedSource.buffer().clone().readString(charset);
                    LogUtil.d(s);
                }
            }
        });
        /*RequestParams requestParams = new RequestParams();
        requestParams.addFormDataPart("ip", param);
        HttpRequest.post(HOST, requestParams, new BaseHttpRequestCallback<IpInfo>() {
            @Override
            public void onStart() {
                super.onStart();
                callBack.onStart();
            }

            @Override
            protected void onSuccess(IpInfo ipInfo) {
                super.onSuccess(ipInfo);
                callBack.onSuccess(ipInfo);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callBack.onFinish();
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                callBack.onFailed();
            }

        });*/
    }
}
