package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/9/20.
 */
public class DriectClassEntity {

    private String id;
    private String img_path;
    private String name;
    private String title;
    private String sign_num;
    private String time;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSign_num() {
        return sign_num;
    }

    public void setSign_num(String sign_num) {
        this.sign_num = sign_num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DriectClassEntity(String id, String img_path, String name, String title, String sign_num, String time) {
        this.id = id;
        this.img_path = img_path;
        this.name = name;
        this.title = title;
        this.sign_num = sign_num;
        this.time = time;
    }

    public DriectClassEntity() {
    }

    @Override
    public String toString() {
        return "DriectClassEntity{" +
                "id='" + id + '\'' +
                ", img_path='" + img_path + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", sign_num='" + sign_num + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
