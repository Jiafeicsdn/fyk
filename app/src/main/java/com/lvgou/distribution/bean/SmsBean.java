package com.lvgou.distribution.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/9.
 */
public class SmsBean implements Serializable{

    private String distributorid;
    private String tmpid;
    private String groupid;
    private String groupname;
    private String content;
    private String chb;
    private String mobiles;
    private String sign;

    public String getDistributorid() {
        return distributorid;
    }

    public void setDistributorid(String distributorid) {
        this.distributorid = distributorid;
    }

    public String getTmpid() {
        return tmpid;
    }

    public void setTmpid(String tmpid) {
        this.tmpid = tmpid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChb() {
        return chb;
    }

    public void setChb(String chb) {
        this.chb = chb;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
