package com.lvgou.distribution.bean;

/**
 * Created by Snow on 2016/9/24.
 */
public class ApplyTeaacherBean {

    private String distributorid;
    private String weixin;
    private String isexperience;
    private String course;
    private String intro;
    private String sign;

    public String getDistributorid() {
        return distributorid;
    }

    public void setDistributorid(String distributorid) {
        this.distributorid = distributorid;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getIsexperience() {
        return isexperience;
    }

    public void setIsexperience(String isexperience) {
        this.isexperience = isexperience;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public ApplyTeaacherBean(String distributorid, String weixin, String isexperience, String course, String intro, String sign) {
        this.distributorid = distributorid;
        this.weixin = weixin;
        this.isexperience = isexperience;
        this.course = course;
        this.intro = intro;
        this.sign = sign;
    }

}
