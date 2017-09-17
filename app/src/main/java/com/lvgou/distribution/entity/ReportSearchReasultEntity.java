package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/8/23.
 */
public class ReportSearchReasultEntity {

    private String id;
    private String name;
    private String address;
    private String business;
    private String latitude;
    private String longitude;
    private String jingdu;
    private String weidu;
    private String markId;
    private String Logo;

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getJingdu() {
        return jingdu;
    }

    public void setJingdu(String jingdu) {
        this.jingdu = jingdu;
    }

    public String getWeidu() {
        return weidu;
    }

    public void setWeidu(String weidu) {
        this.weidu = weidu;
    }

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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ReportSearchReasultEntity(String id, String weidu, String jingdu, String longitude, String latitude, String business, String address, String name, String markId) {
        this.id = id;
        this.weidu = weidu;
        this.jingdu = jingdu;
        this.longitude = longitude;
        this.latitude = latitude;
        this.business = business;
        this.address = address;
        this.name = name;
        this.markId = markId;
    }

    public ReportSearchReasultEntity() {

    }

    @Override
    public String toString() {
        return "ReportSearchReasultEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", business='" + business + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", jingdu='" + jingdu + '\'' +
                ", weidu='" + weidu + '\'' +
                '}';
    }
}
