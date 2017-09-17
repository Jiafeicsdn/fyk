package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/10/14.
 */
public class SendGroupRecorderEntity {

    private String id;
    private String title;
    private String img_state;
    private String time;
    private String days;
    private String destination;// 目的地
    private String city;// 城市
    private String sex;
    private String price;
    private String distributorID;// 数据 对应 用户 id
    private String ownerId;// 用户  id
    private String grade;// 星级
    private String isEmploy;//  是否录用


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_state() {
        return img_state;
    }

    public void setImg_state(String img_state) {
        this.img_state = img_state;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDistributorID() {
        return distributorID;
    }

    public void setDistributorID(String distributorID) {
        this.distributorID = distributorID;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getIsEmploy() {
        return isEmploy;
    }

    public void setIsEmploy(String isEmploy) {
        this.isEmploy = isEmploy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public SendGroupRecorderEntity(String id, String title, String img_state, String time, String days, String city, String destination, String sex, String price, String distributorID, String ownerId, String grade, String isEmploy) {
        this.id = id;
        this.title = title;
        this.img_state = img_state;
        this.time = time;
        this.days = days;
        this.city = city;
        this.destination = destination;
        this.sex = sex;
        this.price = price;
        this.distributorID = distributorID;
        this.ownerId = ownerId;
        this.grade = grade;
        this.isEmploy = isEmploy;
    }

    @Override
    public String toString() {
        return "SendGroupRecorderEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", img_state='" + img_state + '\'' +
                ", time='" + time + '\'' +
                ", days='" + days + '\'' +
                ", destination='" + destination + '\'' +
                ", sex='" + sex + '\'' +
                ", price='" + price + '\'' +
                ", distributorID='" + distributorID + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", grade='" + grade + '\'' +
                ", isEmploy='" + isEmploy + '\'' +
                '}';
    }
}
