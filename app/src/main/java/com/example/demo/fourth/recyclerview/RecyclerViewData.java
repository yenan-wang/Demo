package com.example.demo.fourth.recyclerview;

import com.example.demo.fourth.recyclerview.data.DemoDataType;

public class RecyclerViewData<T> {

    private T mData;
    @DemoDataType
    private int mType;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
