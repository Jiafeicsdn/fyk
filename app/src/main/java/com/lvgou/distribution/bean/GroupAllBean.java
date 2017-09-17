package com.lvgou.distribution.bean;

/**
 * Created by Snow on 2016/9/29.
 */
public class GroupAllBean {

    private String distributorid;
    private String travelTime;
    private String countryPath;
    private String day;
    private String startPrice;
    private String endPrice;
    private String pageindex;
    private String sign;

    public String getDistributorid() {
        return distributorid;
    }

    public void setDistributorid(String distributorid) {
        this.distributorid = distributorid;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getCountryPath() {
        return countryPath;
    }

    public void setCountryPath(String countryPath) {
        this.countryPath = countryPath;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public String getPageindex() {
        return pageindex;
    }

    public void setPageindex(String pageindex) {
        this.pageindex = pageindex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public GroupAllBean(String distributorid, String travelTime, String countryPath, String day, String startPrice, String endPrice, String pageindex, String sign) {
        this.distributorid = distributorid;
        this.travelTime = travelTime;
        this.countryPath = countryPath;
        this.day = day;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.pageindex = pageindex;
        this.sign = sign;
    }

    public GroupAllBean() {
    }
}
