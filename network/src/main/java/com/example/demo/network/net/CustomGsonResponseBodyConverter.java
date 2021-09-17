package com.example.demo.network.net;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson mGson;
    private final Type mBodyType;

    CustomGsonResponseBodyConverter(Gson gson, Type bodyType) {
        mGson = gson;
        mBodyType = bodyType;
    }

    @Nullable
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String body = responseBody.toString();
        if (TextUtils.isEmpty(body)) {
            return (T) "";
        }
        ApiResponse<String> stringApiResponse = mGson.fromJson(body, ApiResponse.class);
        if (!stringApiResponse.isSuccessful()) {
            throw new ApiException(stringApiResponse.getCode(), stringApiResponse.getMessage());
        }
        T data = null;
        if (!TextUtils.isEmpty(stringApiResponse.getData())) {
            //解密
            byte[] decrypt = "".getBytes(StandardCharsets.UTF_8);
            if (decrypt != null) {
                String decryptString = new String(decrypt, StandardCharsets.UTF_8);
                data = mGson.fromJson(decryptString, mBodyType);
            } else {
                throw new ApiException(ApiCode.CODE_ERROR, "decrypt error.");
            }
        } else {
            //如果为空，则统一返回空字符串
            data = (T) "";
        }
        return data;
    }
}
