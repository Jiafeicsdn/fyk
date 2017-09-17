package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/8/26.
 */
public class ReportInfoEntitiy {

    private String id;
    private String shopname;
    private String address;
    private String latitude;
    private String longitude;
    private String local_latitude;
    private String local_longitude;
    private String business;
    private String is_have; //  常报备店  0  新加入报备店 1
    private String size;
    private String img_path;

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getIs_have() {
        return is_have;
    }

    public void setIs_have(String is_have) {
        this.is_have = is_have;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocal_latitude() {
        return local_latitude;
    }

    public void setLocal_latitude(String local_latitude) {
        this.local_latitude = local_latitude;
    }

    public String getLocal_longitude() {
        return local_longitude;
    }

    public void setLocal_longitude(String local_longitude) {
        this.local_longitude = local_longitude;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ReportInfoEntitiy(String id, String shopname, String address, String latitude, String longitude, String local_latitude, String local_longitude, String business, String size, String is_have) {
        this.id = id;
        this.shopname = shopname;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.local_latitude = local_latitude;
        this.local_longitude = local_longitude;
        this.business = business;
        this.size = size;
        this.is_have = is_have;
    }

    public ReportInfoEntitiy() {
    }

    @Override
    public String
    toString() {
        return "ReportInfoEntitiy{" +
                "id='" + id + '\'' +
                ", shopname='" + shopname + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", local_latitude='" + local_latitude + '\'' +
                ", local_longitude='" + local_longitude + '\'' +
                ", business='" + business + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
