package com.lvgou.distribution.entity;

import java.io.Serializable;

/**
 * Created by Swow on 2016/10/18.
 */
public class CircleRecommentEntity implements Serializable {

    private String ID;
    private String Title;
    private String PicUrl;
    private String DistributorID;
    private String SourceDistributorName;
    private String ZanCount;
    private String CommentCount;
    private String SourceDistributorID;
    private String Zaned;
    private String SubTitle;
    private int Hits;
    private String UserType;

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getDistributorID() {
        return DistributorID;
    }

    public void setDistributorID(String distributorID) {
        DistributorID = distributorID;
    }

    public String getSourceDistributorName() {
        return SourceDistributorName;
    }

    public void setSourceDistributorName(String sourceDistributorName) {
        SourceDistributorName = sourceDistributorName;
    }

    public String getZanCount() {
        return ZanCount;
    }

    public void setZanCount(String zanCount) {
        ZanCount = zanCount;
    }

    public String getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(String commentCount) {
        CommentCount = commentCount;
    }

    public String getSourceDistributorID() {
        return SourceDistributorID;
    }

    public void setSourceDistributorID(String sourceDistributorID) {
        SourceDistributorID = sourceDistributorID;
    }

    public String getZaned() {
        return Zaned;
    }

    public void setZaned(String zaned) {
        Zaned = zaned;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public int getHits() {
        return Hits;
    }

    public void setHits(int hits) {
        Hits = hits;
    }

    @Override
    public String toString() {
        return "CircleRecommentEntity{" +
                "ID='" + ID + '\'' +
                ", Title='" + Title + '\'' +
                ", PicUrl='" + PicUrl + '\'' +
                ", DistributorID='" + DistributorID + '\'' +
                ", SourceDistributorName='" + SourceDistributorName + '\'' +
                ", ZanCount='" + ZanCount + '\'' +
                ", CommentCount='" + CommentCount + '\'' +
                ", SourceDistributorID='" + SourceDistributorID + '\'' +
                ", Zaned='" + Zaned + '\'' +
                ", SubTitle='" + SubTitle + '\'' +
                ", Hits=" + Hits +
                ", UserType='" + UserType + '\'' +
                '}';
    }
}
