package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/9/29.
 */
public class GroupSignEntity {

    private String id;
    private String img_path;
    private String name;
    private String star;
    private String time;
    private String state;
    private String isPublisher;

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

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsPublisher() {
        return isPublisher;
    }

    public void setIsPublisher(String isPublisher) {
        this.isPublisher = isPublisher;
    }

    public GroupSignEntity(String id, String state, String time, String star, String name, String img_path, String isPublisher) {
        this.id = id;
        this.state = state;
        this.time = time;
        this.star = star;
        this.name = name;
        this.img_path = img_path;
        this.isPublisher = isPublisher;
    }

    public GroupSignEntity() {
    }


    @Override
    public String toString() {
        return "GroupSignEntity{" +
                "id='" + id + '\'' +
                ", img_path='" + img_path + '\'' +
                ", name='" + name + '\'' +
                ", star='" + star + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
