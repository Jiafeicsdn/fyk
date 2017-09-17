package com.lvgou.distribution.bean;

/**
 * Created by Snow on 2016/9/14.
 */
public class AddEditSmsBean {

    private String distributorid;
    private String tmpid;
    private String title;
    private String content;
    private String sign;

    public String getDistributorid() {
        return distributorid;
    }

    public void setDistributorid(String distributorid) {
        this.distributorid = distributorid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTmpid() {
        return tmpid;
    }

    public void setTmpid(String tmpid) {
        this.tmpid = tmpid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public AddEditSmsBean(String distributorid, String tmpid, String title, String content, String sign) {
        this.distributorid = distributorid;
        this.tmpid = tmpid;
        this.title = title;
        this.content = content;
        this.sign = sign;
    }
}
