package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/3/21 0021.
 */
public class MyGroupEntity {
    private String id;
    private String name;
    private String phone;
    private String tiem;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTiem() {
        return tiem;
    }

    public void setTiem(String tiem) {
        this.tiem = tiem;
    }

    public MyGroupEntity(String id, String tiem, String phone, String name) {
        this.id = id;
        this.tiem = tiem;
        this.phone = phone;
        this.name = name;
    }

    public MyGroupEntity() {
    }

    @Override
    public String toString() {
        return "MyGroupEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", tiem='" + tiem + '\'' +
                '}';
    }
}
