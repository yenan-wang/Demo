package com.example.demo.main.fourth.recyclerview.data;

public class BaseDataVO {
    protected String mId;

    protected long mStartTime;

    protected long mEndTime;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long endTime) {
        mEndTime = endTime;
    }

    @Override
    public String toString() {
        return "BaseDataVO{" +
                "mId='" + mId + '\'' +
                ", mStartTime='" + mStartTime + '\'' +
                ", mEndTime='" + mEndTime + '\'' +
                '}';
    }
}
