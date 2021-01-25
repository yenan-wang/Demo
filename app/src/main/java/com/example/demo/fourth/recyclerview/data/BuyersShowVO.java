package com.example.demo.fourth.recyclerview.data;

public class BuyersShowVO extends BaseDataVO {
    //买家秀封面
    private String mCoverUrl;
    //标题
    private String mTitle;
    //作者头像
    private String mAvatarUrl;
    //作者
    private String mAuthor;
    //赞数
    private int mPraiseNumber;

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public int getPraiseNumber() {
        return mPraiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        mPraiseNumber = praiseNumber;
    }

}
