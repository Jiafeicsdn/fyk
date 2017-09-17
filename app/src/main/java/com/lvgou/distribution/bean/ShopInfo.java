package com.lvgou.distribution.bean;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ShopInfo{

    public ShopInfo(int ID, int distributorID, String distributorRealName, int reportSellerID, String shopName, String adderss, String latitude, String longitude, String business) {
        this.ID = ID;
        DistributorID = distributorID;
        DistributorRealName = distributorRealName;
        ReportSellerID = reportSellerID;
        ShopName = shopName;
        Adderss = adderss;
        Latitude = latitude;
        Longitude = longitude;
        Business = business;
    }

    //    "ID": 210,
//            "DistributorID": -1,
//            "DistributorRealName": "",
//            "ReportSellerID": 34,
//            "ShopName": "杭州途购管家",
//            "Adderss": "",
//            "Latitude": "",
//            "Longitude": "",
//            "Business": ""
    public int ID;
    public int DistributorID;
    public String DistributorRealName;
    public int ReportSellerID;
    public String ShopName;
    public String Adderss;



    public String Latitude;
    public String Longitude;
    public String Business;



}
