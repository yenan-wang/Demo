package com.example.demo.fourth.recyclerview.data;

import java.util.List;

public class CommodityVO extends BaseDataVO {
    //封面图链接
    private String mImageUrl;
    //商品标题
    private String mCommodityTitle;
    //价格
    private double mPrice;
    //付款人数
    private int mPaymentCount;
    //标题标签内容
    private List<String> mTitleLabelContent;
    //商品标签
    private List<String> mCommodityLabelContent;

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getCommodityTitle() {
        return mCommodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        mCommodityTitle = commodityTitle;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public int getPaymentCount() {
        return mPaymentCount;
    }

    public void setPaymentCount(int paymentCount) {
        mPaymentCount = paymentCount;
    }

    public List<String> getTitleLabelContent() {
        return mTitleLabelContent;
    }

    public void setTitleLabelContent(List<String> titleLabelContent) {
        mTitleLabelContent = titleLabelContent;
    }

    public List<String> getCommodityLabelContent() {
        return mCommodityLabelContent;
    }

    public void setCommodityLabelContent(List<String> commodityLabelContent) {
        mCommodityLabelContent = commodityLabelContent;
    }


}
