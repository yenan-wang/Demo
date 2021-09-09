package com.ngb.network.bean;

import com.google.gson.annotations.SerializedName;

public class DataBean {

    @SerializedName("time")
    private String mTime;
    @SerializedName("ftime")
    private String mFTime;
    @SerializedName("context")
    private String mContext;
    @SerializedName("location")
    private Object mLocation;

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getFTime() {
        return mFTime;
    }

    public void setFTime(String FTime) {
        mFTime = FTime;
    }

    public String getContext() {
        return mContext;
    }

    public void setContext(String context) {
        mContext = context;
    }

    public Object getLocation() {
        return mLocation;
    }

    public void setLocation(Object location) {
        mLocation = location;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "mTime='" + mTime + '\'' +
                ", mFTime='" + mFTime + '\'' +
                ", mContext='" + mContext + '\'' +
                ", mLocation=" + mLocation +
                '}';
    }
}
