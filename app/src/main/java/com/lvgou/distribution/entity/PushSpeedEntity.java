package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/3/17 0017.
 */
public class PushSpeedEntity {
    private String id;
    private String productID;
    private String title;// 名字
    private String intro;// 内容
    private String picUrls;
    private String sendTime;// 时间
    private String price_Market;// 市场价格  灰色价格
    private String price_Sell;// 售价  红色价格
    private String price_Profit;// 佣金

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getPrice_Market() {
        return price_Market;
    }

    public void setPrice_Market(String price_Market) {
        this.price_Market = price_Market;
    }

    public String getPrice_Sell() {
        return price_Sell;
    }

    public void setPrice_Sell(String price_Sell) {
        this.price_Sell = price_Sell;
    }

    public String getPrice_Profit() {
        return price_Profit;
    }

    public void setPrice_Profit(String price_Profit) {
        this.price_Profit = price_Profit;
    }

    public PushSpeedEntity(String id, String price_Profit, String price_Sell, String price_Market, String sendTime, String picUrls, String intro, String title, String productID) {
        this.id = id;
        this.price_Profit = price_Profit;
        this.price_Sell = price_Sell;
        this.price_Market = price_Market;
        this.sendTime = sendTime;
        this.picUrls = picUrls;
        this.intro = intro;
        this.title = title;
        this.productID = productID;
    }

    public PushSpeedEntity() {
    }

    @Override
    public String toString() {
        return "PushSpeedEntity{" +
                "id='" + id + '\'' +
                ", productID='" + productID + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", picUrls='" + picUrls + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", price_Market='" + price_Market + '\'' +
                ", price_Sell='" + price_Sell + '\'' +
                ", price_Profit='" + price_Profit + '\'' +
                '}';
    }
}
