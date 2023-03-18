package com.example.demo.network.interceptor;

import androidx.annotation.NonNull;

import com.example.demo.network.bean.NetWorkInterceptData;
import com.ngb.wyn.common.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class NetInterceptor extends AbsInterceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtil.e("插桩的interceptor");

        NetWorkInterceptData data = new NetWorkInterceptData();

        //请求处理
        Request request = chain.request();
        //line
        String method = request.method(); //请求方式
        HttpUrl url = request.url();      //请求链接
        data.setRequestMethod(method);
        data.setRequestUrl(url.url().toString());
        Connection connection = chain.connection();
        if (connection != null) {
            Protocol protocol = connection.protocol();
            String protocolName = protocol.name(); //请求协议名
            data.setRequestProtocolName(protocolName);
        }
        //header
        Headers requestHeaders = request.headers();
        Map<String, String> requestMap = new HashMap<>();
        for (int i = 0; i < requestHeaders.size(); i++) {
            String name = requestHeaders.name(i);   //请求头key
            String value = requestHeaders.value(i); //请求头value
            requestMap.put(name, value);
        }
        data.setRequestHeaders(requestMap);
        //body
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Charset charset = StandardCharsets.UTF_8;
            MediaType mediaType = requestBody.contentType();
            if (mediaType != null) {
                String type = mediaType.type();              //mediaType
                data.setRequestMediaType(type);
                Charset charsetRequest = mediaType.charset();//charset
                if (charsetRequest != null) {
                    charset = charsetRequest;
                }
            }
            data.setRequestCharset(charset.name());
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            String requestBodyContent = buffer.readString(charset); //请求体内容
            data.setRequestBodyContent(requestBodyContent);
        }

        //执行请求
        Response response = chain.proceed(request);

        //响应处理
        //line
        int code = response.code();                  //响应code
        String message = response.message();         //响应消息
        data.setResponseCode(code);
        data.setResponseMessage(message);
        //header
        Headers responseHeaders = response.headers();//响应头
        Map<String, String> responseMap = new HashMap<>();
        for (int i = 0; i < requestHeaders.size(); i++) {
            String name = responseHeaders.name(i);   //响应头key
            String value = responseHeaders.value(i); //响应头value
            responseMap.put(name, value);
        }
        data.setResponseHeaders(responseMap);
        //body
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            BufferedSource bufferedSource = responseBody.source();
            bufferedSource.request(Long.MAX_VALUE);
            Charset charset = StandardCharsets.UTF_8;
            MediaType mediaType = responseBody.contentType();
            if (mediaType != null) {
                String type = mediaType.type();                //mediaType
                data.setResponseMediaType(type);
                Charset responseCharset = mediaType.charset(); //charset
                if (responseCharset != null) {
                    charset = responseCharset;
                }
            }
            data.setResponseCharset(charset.name());
            Buffer buffer = bufferedSource.buffer().clone();
            String responseContent = buffer.readString(charset); //响应体内容
            data.setResponseContent(responseContent);
        }

        LogUtil.d(data.toString());
        return response;
    }
}
