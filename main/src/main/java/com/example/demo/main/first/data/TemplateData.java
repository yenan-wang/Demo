package com.example.demo.main.first.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.demo.main.BR;

public class TemplateData extends BaseObservable {

    private String mUserName;
    private String mUrl;
    private long mTime;
    private long mLikeCount;

    @Bindable
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public long getLikeCount() {
        return mLikeCount;
    }

    public void setLikeCount(long likeCount) {
        mLikeCount = likeCount;
        notifyPropertyChanged(BR.likeCount);
    }

    @Override
    public String toString() {
        return "TemplateData{" +
                "mUserName='" + mUserName + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mTime=" + mTime +
                ", mLikeCount=" + mLikeCount +
                '}';
    }
}
