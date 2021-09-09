package com.ngb.network.net;

class ApiResponse<T> {

    private int mCode;

    private String mMessage;

    private T data;

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccessful() {
        return mCode == ApiCode.CODE_OK;
    }
}
