package com.lvgou.distribution.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/13.
 */
public class MyZanListBean implements Serializable{
    private String ID;
    private String FengwenID;
    private String FengwenDistributorID;
    private String FengwenDistributorName;
    private String FengwenTitle;
    private String DistributorID;
    private String DistributorName;
    private int UserType;
    private int IsRZ;
    private int IsRead;
    private String CreateTime;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFengwenID() {
        return FengwenID;
    }

    public void setFengwenID(String fengwenID) {
        FengwenID = fengwenID;
    }

    public String getFengwenDistributorID() {
        return FengwenDistributorID;
    }

    public void setFengwenDistributorID(String fengwenDistributorID) {
        FengwenDistributorID = fengwenDistributorID;
    }

    public String getFengwenDistributorName() {
        return FengwenDistributorName;
    }

    public void setFengwenDistributorName(String fengwenDistributorName) {
        FengwenDistributorName = fengwenDistributorName;
    }

    public String getFengwenTitle() {
        return FengwenTitle;
    }

    public void setFengwenTitle(String fengwenTitle) {
        FengwenTitle = fengwenTitle;
    }

    public String getDistributorID() {
        return DistributorID;
    }

    public void setDistributorID(String distributorID) {
        DistributorID = distributorID;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public int getIsRZ() {
        return IsRZ;
    }

    public void setIsRZ(int isRZ) {
        IsRZ = isRZ;
    }

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
