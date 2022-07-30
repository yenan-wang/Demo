package com.example.demo.main.fifth.customview.bihua;

import com.example.demo.common.utils.LogUtil;
import com.example.demo.network.CommonRequestUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BihuaViewModel {

    public void getHanziData(String hanzi, OnRequestResult requestResult) {
        try {
            String s = URLEncoder.encode(hanzi, StandardCharsets.UTF_8.name());
            String url = "https://cdn.jsdelivr.net/npm/hanzi-writer-data@latest/" + s + ".json";
            CommonRequestUtil.request(url, new CommonRequestUtil.RequestResult() {
                @Override
                public void success(Object o) {
                    LogUtil.d(o);
                    requestResult.onSuccess(String.valueOf(o));
                }

                @Override
                public void fail(Throwable throwable) {
                    LogUtil.e("request fail:" + throwable.getMessage());
                    requestResult.onFail(throwable);
                }
            });
        } catch (UnsupportedEncodingException e) {
            LogUtil.e("catch:" +  e.getMessage());
        }
    }

    interface OnRequestResult {

        void onSuccess(String jsonString);

        void onFail(Throwable throwable);
    }
}
