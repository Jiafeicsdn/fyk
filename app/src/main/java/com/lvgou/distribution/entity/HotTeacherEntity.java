package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/9/20.
 */
public class HotTeacherEntity {

    private String id;
    private String img_path;
    private String name;
    private String is_authen;//是否 实名认证

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_authen() {
        return is_authen;
    }

    public void setIs_authen(String is_authen) {
        this.is_authen = is_authen;
    }

    public HotTeacherEntity(String id, String img_path, String name, String is_authen) {
        this.id = id;
        this.img_path = img_path;
        this.name = name;
        this.is_authen = is_authen;
    }

    public HotTeacherEntity() {
    }

    @Override
    public String toString() {
        return "HotTeacherEntity{" +
                "id='" + id + '\'' +
                ", img_path='" + img_path + '\'' +
                ", name='" + name + '\'' +
                ", is_authen='" + is_authen + '\'' +
                '}';
    }
}
