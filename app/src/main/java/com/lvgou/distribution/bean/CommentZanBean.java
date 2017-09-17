package com.lvgou.distribution.bean;

/**
 * Created by Administrator on 2016/9/12.
 */
public class CommentZanBean {


    private String distributorid; // 用户id
    private String teacherId;    //讲师 id
    private String content;      // 评论内容
    private String tuanbi;      // 团币个数
    private String sign;        // 签名

    public String getDistributorid() {
        return distributorid;
    }

    public void setDistributorid(String distributorid) {
        this.distributorid = distributorid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTuanbi() {
        return tuanbi;
    }

    public void setTuanbi(String tuanbi) {
        this.tuanbi = tuanbi;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public CommentZanBean(String distributorid, String teacherId, String content, String tuanbi, String sign) {
        this.distributorid = distributorid;
        this.teacherId = teacherId;
        this.content = content;
        this.tuanbi = tuanbi;
        this.sign = sign;
    }
}
