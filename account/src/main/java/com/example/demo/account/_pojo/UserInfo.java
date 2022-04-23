package com.example.demo.account._pojo;

public class UserInfo {

    private String mId;
    private int mSex;
    private String mNickName;
    private String mAccountId;
    private String mUrl;
    private long mRegisterTime;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(int sex) {
        mSex = sex;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getAccountId() {
        return mAccountId;
    }

    public void setAccountId(String accountId) {
        mAccountId = accountId;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public long getRegisterTime() {
        return mRegisterTime;
    }

    public void setRegisterTime(long registerTime) {
        mRegisterTime = registerTime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "mId='" + mId + '\'' +
                ", mSex=" + mSex +
                ", mNickName='" + mNickName + '\'' +
                ", mAccountId='" + mAccountId + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mRegisterTime=" + mRegisterTime +
                '}';
    }
}
