package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/5/16 0016.
 */
public class TuanYuanEntity {

    private String id;
    private String name;
    private String img_path;

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

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public TuanYuanEntity(String id, String name, String img_path) {
        this.id = id;
        this.name = name;
        this.img_path = img_path;
    }

    public TuanYuanEntity() {
    }

    @Override
    public String toString() {
        return "TuanYuanEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img_path='" + img_path + '\'' +
                '}';
    }
}
