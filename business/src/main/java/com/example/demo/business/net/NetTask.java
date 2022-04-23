package com.example.demo.business.net;

import com.example.demo.business.callBack.LoadTasksCallBack;

public interface NetTask<T, Data> {
    void execute(T param, LoadTasksCallBack<Data> callBack);
}
