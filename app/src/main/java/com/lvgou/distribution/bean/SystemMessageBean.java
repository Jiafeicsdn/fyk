package com.lvgou.distribution.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/28.
 */
public class SystemMessageBean implements Serializable{

       private int ID;
       private int SendUserID;
       private String SendRealName;
       private int ReceiveUserID;
       private String ReceiveShopName;
       private String ReceiveRealName;
       private int IsRranspond;
       private String RranspondMobile;
       private String Title;
       private String Content;
       private int IsRead;
       private int SendType;
       private String CreateTime;
       private String ReadTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSendUserID() {
        return SendUserID;
    }

    public void setSendUserID(int sendUserID) {
        SendUserID = sendUserID;
    }

    public String getSendRealName() {
        return SendRealName;
    }

    public void setSendRealName(String sendRealName) {
        SendRealName = sendRealName;
    }

    public int getReceiveUserID() {
        return ReceiveUserID;
    }

    public void setReceiveUserID(int receiveUserID) {
        ReceiveUserID = receiveUserID;
    }

    public String getReceiveShopName() {
        return ReceiveShopName;
    }

    public void setReceiveShopName(String receiveShopName) {
        ReceiveShopName = receiveShopName;
    }

    public String getReceiveRealName() {
        return ReceiveRealName;
    }

    public void setReceiveRealName(String receiveRealName) {
        ReceiveRealName = receiveRealName;
    }

    public int getIsRranspond() {
        return IsRranspond;
    }

    public void setIsRranspond(int isRranspond) {
        IsRranspond = isRranspond;
    }

    public String getRranspondMobile() {
        return RranspondMobile;
    }

    public void setRranspondMobile(String rranspondMobile) {
        RranspondMobile = rranspondMobile;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }

    public int getSendType() {
        return SendType;
    }

    public void setSendType(int sendType) {
        SendType = sendType;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getReadTime() {
        return ReadTime;
    }

    public void setReadTime(String readTime) {
        ReadTime = readTime;
    }

    @Override
    public String toString() {
        return "SystemMessageBean{" +
                "ID=" + ID +
                ", SendUserID=" + SendUserID +
                ", SendRealName='" + SendRealName + '\'' +
                ", ReceiveUserID=" + ReceiveUserID +
                ", ReceiveShopName='" + ReceiveShopName + '\'' +
                ", ReceiveRealName='" + ReceiveRealName + '\'' +
                ", IsRranspond=" + IsRranspond +
                ", RranspondMobile='" + RranspondMobile + '\'' +
                ", Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                ", IsRead=" + IsRead +
                ", SendType=" + SendType +
                ", CreateTime='" + CreateTime + '\'' +
                ", ReadTime='" + ReadTime + '\'' +
                '}';
    }
}
