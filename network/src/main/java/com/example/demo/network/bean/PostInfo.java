package com.example.demo.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostInfo {
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nu")
    private String mNU;
    @SerializedName("isCheck")
    private String mIsCheck;
    @SerializedName("condition")
    private String mCondition;
    @SerializedName("com")
    private String mCom;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("state")
    private String mState;
    @SerializedName("data")
    private List<DataBean> mData;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getNU() {
        return mNU;
    }

    public void setNU(String NU) {
        mNU = NU;
    }

    public String getIsCheck() {
        return mIsCheck;
    }

    public void setIsCheck(String isCheck) {
        mIsCheck = isCheck;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String condition) {
        mCondition = condition;
    }

    public String getCom() {
        return mCom;
    }

    public void setCom(String com) {
        mCom = com;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public List<DataBean> getData() {
        return mData;
    }

    public void setData(List<DataBean> data) {
        mData = data;
    }

    @Override
    public String toString() {
        return "PostInfo{" +
                "mMessage='" + mMessage + '\'' +
                ", mNU='" + mNU + '\'' +
                ", mIsCheck='" + mIsCheck + '\'' +
                ", mCondition='" + mCondition + '\'' +
                ", mCom='" + mCom + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mState='" + mState + '\'' +
                ", mData=" + mData +
                '}';
    }
}
