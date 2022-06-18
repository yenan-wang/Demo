package com.example.demo.network.interceptor;

import com.example.demo.common.utils.LogUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class CommonRequestInterceptor extends AbsInterceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        Response priorResponse = dealPriorResponse(originalResponse);
        ResponseBody responseBody = priorResponse.body();
        if (responseBody != null) {
            BufferedSource bufferedSource = responseBody.source();
            Buffer buffer = bufferedSource.buffer().clone();
            String s = buffer.readString(StandardCharsets.UTF_8);
            LogUtil.d(s);
        }
        return priorResponse;
    }

    private Response dealPriorResponse(Response originalResponse) {
        while (originalResponse.priorResponse() != null) {
            Response priorResponse = originalResponse.priorResponse();
            int priorResponseCode = priorResponse.code();
            if (priorResponseCode == HttpURLConnection.HTTP_MOVED_PERM || priorResponseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                analyseResponse(priorResponse);
                return priorResponse;
            }
        }
        return originalResponse;
    }

    private void analyseResponse(Response response) {
        if (response == null) {
            return;
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return;
        }
        Buffer buffer = responseBody.source().buffer().clone();
        String s = buffer.readString(StandardCharsets.UTF_8);
        LogUtil.d("重定向：" + s);
    }
}