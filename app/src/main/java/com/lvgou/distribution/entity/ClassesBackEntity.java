package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/7/27 0027.
 */
public class ClassesBackEntity {

    private String id;
    private String img_icon;
    private String name;
    private String title;
    private String sign_num;
    private String tuanbi;

private int State;

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
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

    public String getImg_icon() {
        return img_icon;
    }

    public void setImg_icon(String img_icon) {
        this.img_icon = img_icon;
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

    public String getTuanbi() {
        return tuanbi;
    }

    public void setTuanbi(String tuanbi) {
        this.tuanbi = tuanbi;
    }

    public ClassesBackEntity(String id, String tuanbi, String sign_num, String title, String name, String img_icon) {
        this.id = id;
        this.tuanbi = tuanbi;
        this.sign_num = sign_num;
        this.title = title;
        this.name = name;
        this.img_icon = img_icon;
    }

    public ClassesBackEntity() {
    }

    @Override
    public String toString() {
        return "ClassesBackEntity{" +
                "id='" + id + '\'' +
                ", img_icon='" + img_icon + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", sign_num='" + sign_num + '\'' +
                ", tuanbi='" + tuanbi + '\'' +
                '}';
    }
}
