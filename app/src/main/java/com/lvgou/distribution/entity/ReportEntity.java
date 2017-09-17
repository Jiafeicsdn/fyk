package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/7/2 0002.
 */
public class ReportEntity {

    private String id;
    private String name;
    private String date;
    private String people_num;
    private String reportSellerId;
    private String state;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeople_num() {
        return people_num;
    }

    public void setPeople_num(String people_num) {
        this.people_num = people_num;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReportSellerId() {
        return reportSellerId;
    }

    public void setReportSellerId(String reportSellerId) {
        this.reportSellerId = reportSellerId;
    }

    public ReportEntity(String id, String state, String people_num, String date, String name, String reportSellerId) {
        this.id = id;
        this.state = state;
        this.people_num = people_num;
        this.date = date;
        this.name = name;
        this.reportSellerId = reportSellerId;
    }

    public ReportEntity() {
    }

    @Override
    public String toString() {
        return "ReportEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", people_num='" + people_num + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
