package com.lvgou.distribution.entity;

import java.io.Serializable;

/**
 * Created by Snow on 2016/10/19.
 */
public class CircleUserEntity implements Serializable{

    private String ID;
    private String RealName;
    private String PicUrl;
    private String TuanBi;
    private String Attr;
    private String FengwenCount;
    private String FansCount;
    private int State;
    private int UserType;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getTuanBi() {
        return TuanBi;
    }

    public void setTuanBi(String tuanBi) {
        TuanBi = tuanBi;
    }

    public String getAttr() {
        return Attr;
    }

    public void setAttr(String attr) {
        Attr = attr;
    }

    public String getFengwenCount() {
        return FengwenCount;
    }

    public void setFengwenCount(String fengwenCount) {
        FengwenCount = fengwenCount;
    }

    public String getFansCount() {
        return FansCount;
    }

    public void setFansCount(String fansCount) {
        FansCount = fansCount;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }
}
