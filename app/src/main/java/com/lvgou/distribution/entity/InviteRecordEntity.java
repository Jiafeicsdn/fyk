package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/4/6 0006.
 */
public class InviteRecordEntity {

    private String id;
    private String name;
    private String time;
    private String isGet;
    private String state;
    private String experienceBuy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsGet() {
        return isGet;
    }

    public void setIsGet(String isGet) {
        this.isGet = isGet;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExperienceBuy() {
        return experienceBuy;
    }

    public void setExperienceBuy(String experienceBuy) {
        this.experienceBuy = experienceBuy;
    }

    public InviteRecordEntity(String experienceBuy, String state, String isGet, String time, String name, String id) {
        this.experienceBuy = experienceBuy;
        this.state = state;
        this.isGet = isGet;
        this.time = time;
        this.name = name;
        this.id = id;
    }

    public InviteRecordEntity() {
    }

    @Override
    public String toString() {
        return "InviteRecordEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", isGet='" + isGet + '\'' +
                ", state='" + state + '\'' +
                ", experienceBuy='" + experienceBuy + '\'' +
                '}';
    }
}
