package com.example.demo.business.callBack;

public interface LoadTasksCallBack<Data> {

    void onSuccess(Data t);

    void onStart();

    void onFailed();

    void onFinish();
}
