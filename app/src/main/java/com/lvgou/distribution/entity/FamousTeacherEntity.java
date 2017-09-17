package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/5/16 0016.
 */
public class FamousTeacherEntity {

    private String id;
    private String state;
    private String img_path;
    private String name;
    private String fee;
    private String people_num;
    private String theme;
    private String time;
    private String fit_people;


    public String getFit_people() {
        return fit_people;
    }

    public void setFit_people(String fit_people) {
        this.fit_people = fit_people;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPeople_num() {
        return people_num;
    }

    public void setPeople_num(String people_num) {
        this.people_num = people_num;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public FamousTeacherEntity(String id, String time, String theme, String people_num, String fee, String name, String img_path, String state,String fit_people) {
        this.id = id;
        this.time = time;
        this.theme = theme;
        this.people_num = people_num;
        this.fee = fee;
        this.name = name;
        this.img_path = img_path;
        this.state = state;
        this.fit_people=fit_people;
    }

    public FamousTeacherEntity() {
    }

    @Override
    public String toString() {
        return "FamousTeacherEntity{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", img_path='" + img_path + '\'' +
                ", name='" + name + '\'' +
                ", fee='" + fee + '\'' +
                ", people_num='" + people_num + '\'' +
                ", theme='" + theme + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
