package com.example.demo.network.interceptor;

import android.text.TextUtils;

import com.example.demo.common.utils.LogUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class ExpiredAndRetryInterceptor extends AbsInterceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        boolean isExpired = isExpired(originalResponse);
        //如果返回结果过期了，那么需要将过期值替换一个新值，再次发送请求
        if (isExpired) {
            try {
                //更新值后，构造的新请求
                Request newRequest = updateData(request);
                //先关闭原来的请求
                originalResponse.close();
                //再发送新请求
                return chain.proceed(newRequest);
            } catch (Exception e) {
                LogUtil.e("ExpiredAndRetryInterceptor, error:e=" + e.getMessage());
                //出错，则返回原response
                return originalResponse;
            }
        }
        //如果没有过期，则返回原response
        else {
            return originalResponse;
        }
    }

    //此处判断是否过期，是属于网络本身正常(返回200)，但业务中返回的自定义错误码
    private boolean isExpired(Response response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                Buffer buffer = responseBody.source().buffer();
                Charset charset = StandardCharsets.UTF_8;
                MediaType mediaType = responseBody.contentType();
                if (mediaType != null) {
                    Charset mediaTypeCharset = mediaType.charset();
                    if (mediaTypeCharset != null) {
                        charset = mediaTypeCharset;
                    }
                }
                String stringBuffer = buffer.clone().readString(charset);

                //stringBuffer拿到的就是业务方自己定义返回的json型结果字符串
                try {
                    JSONObject jsonObject = new JSONObject(stringBuffer);
                    //拿出代表错误码的那一个数据
                    int code = jsonObject.optInt("code");
                    //如果该错误码等于某个我需要替换新值之后再次尝试的错误码，那么就返回过期了，进行下一步操作
                    if (code == 1403) {
                        LogUtil.d("过期了");
                        return true;
                    }
                } catch (Exception e) {
                    LogUtil.e("isExpired: " + e.getMessage());
                }
            }
        }
        return false;
    }

    private Request updateData(Request originalRequest) throws Exception {
        //假如要替换的值在header中，那么这样替换
        if (!TextUtils.isEmpty(originalRequest.header("session"))) {
            Request.Builder newRequestBuilder = originalRequest.newBuilder();
            //移除旧值
            newRequestBuilder.removeHeader("session");
            //写入新值
            newRequestBuilder.addHeader("session", "newValue");
            //method和body都取原来的，不改动；
            newRequestBuilder.method(originalRequest.method(), originalRequest.body());
            //返回新的请求
            return newRequestBuilder.build();
        }

        //假如要替换的值在请求体requestBody中，那么这样替换
        RequestBody originalRequestBody = originalRequest.body();
        if (originalRequestBody != null) {
            Buffer buffer = new Buffer();
            originalRequestBody.writeTo(buffer);
            Charset charset = StandardCharsets.UTF_8;
            MediaType mediaType = originalRequestBody.contentType();
            if (mediaType != null) {
                Charset mediaTypeCharset = mediaType.charset();
                if (mediaTypeCharset != null) {
                    charset = mediaTypeCharset;
                }
            }
            String stringBuffer = buffer.clone().readString(charset);

            //读取到请求体之后，将其json化
            JSONObject jsonObject = new JSONObject(stringBuffer);
            //移除旧值
            jsonObject.remove("session");
            //写入新值
            jsonObject.put("session", "newValue");

            //构造新请求
            Request.Builder newRequestBuilder = originalRequest.newBuilder();
            //method取原来的，不改动；requestBody写入修改后的json
            newRequestBuilder.method(originalRequest.method(), RequestBody.create(originalRequestBody.contentType(), jsonObject.toString()));
            //返回新的请求
            return newRequestBuilder.build();
        }
        return originalRequest;
    }
}
