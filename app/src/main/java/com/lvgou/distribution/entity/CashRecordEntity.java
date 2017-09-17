package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/11/2.
 */
public class CashRecordEntity {


    private String id;
    private String title;
    private String tuanbi;
    private String time;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTuanbi() {
        return tuanbi;
    }

    public void setTuanbi(String tuanbi) {
        this.tuanbi = tuanbi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CashRecordEntity(String id, String title, String tuanbi, String time, String state) {
        this.id = id;
        this.title = title;
        this.tuanbi = tuanbi;
        this.time = time;
        this.state = state;
    }

    public CashRecordEntity() {
    }

    @Override
    public String toString() {
        return "CashRecordEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", tuanbi='" + tuanbi + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}


