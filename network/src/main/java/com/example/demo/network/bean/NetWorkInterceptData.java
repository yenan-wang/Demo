package com.example.demo.network.bean;

import java.util.Map;

public class NetWorkInterceptData {

    private String mRequestMethod;
    private String mRequestUrl;
    private String mRequestProtocolName;
    private Map<String, String> mRequestHeaders;
    private String mRequestMediaType;
    private String mRequestCharset;
    private String requestBodyContent;

    private int mResponseCode;
    private String mResponseMessage;
    private Map<String, String> mResponseHeaders;
    private String mResponseMediaType;
    private String mResponseCharset;
    private String responseContent;

    public String getRequestMethod() {
        return mRequestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        mRequestMethod = requestMethod;
    }

    public String getRequestUrl() {
        return mRequestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        mRequestUrl = requestUrl;
    }

    public String getRequestProtocolName() {
        return mRequestProtocolName;
    }

    public void setRequestProtocolName(String requestProtocolName) {
        mRequestProtocolName = requestProtocolName;
    }

    public Map<String, String> getRequestHeaders() {
        return mRequestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        mRequestHeaders = requestHeaders;
    }

    public String getRequestMediaType() {
        return mRequestMediaType;
    }

    public void setRequestMediaType(String requestMediaType) {
        mRequestMediaType = requestMediaType;
    }

    public String getRequestCharset() {
        return mRequestCharset;
    }

    public void setRequestCharset(String requestCharset) {
        mRequestCharset = requestCharset;
    }

    public String getRequestBodyContent() {
        return requestBodyContent;
    }

    public void setRequestBodyContent(String requestBodyContent) {
        this.requestBodyContent = requestBodyContent;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(int responseCode) {
        mResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        mResponseMessage = responseMessage;
    }

    public Map<String, String> getResponseHeaders() {
        return mResponseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        mResponseHeaders = responseHeaders;
    }

    public String getResponseMediaType() {
        return mResponseMediaType;
    }

    public void setResponseMediaType(String responseMediaType) {
        mResponseMediaType = responseMediaType;
    }

    public String getResponseCharset() {
        return mResponseCharset;
    }

    public void setResponseCharset(String responseCharset) {
        mResponseCharset = responseCharset;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    @Override
    public String toString() {
        return "NetWorkInterceptData{" +
                "mRequestMethod='" + mRequestMethod + '\'' +
                ", mRequestUrl='" + mRequestUrl + '\'' +
                ", mRequestProtocolName='" + mRequestProtocolName + '\'' +
                ", mRequestHeaders=" + mRequestHeaders +
                ", mRequestMediaType='" + mRequestMediaType + '\'' +
                ", mRequestCharset='" + mRequestCharset + '\'' +
                ", requestBodyContent='" + requestBodyContent + '\'' +
                ", mResponseCode=" + mResponseCode +
                ", mResponseMessage='" + mResponseMessage + '\'' +
                ", mResponseHeaders=" + mResponseHeaders +
                ", mResponseMediaType='" + mResponseMediaType + '\'' +
                ", mResponseCharset='" + mResponseCharset + '\'' +
                ", responseContent='" + responseContent + '\'' +
                '}';
    }
}
