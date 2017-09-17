package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/7/25 0025.
 */
public class CommentListEntity {

    private String id;
    private String DistributorID;
    private String img_head;
    private String name;
    private String time;
    private String content;
    private String img_icon;
    private String state;

    public String getDistributorID() {
        return DistributorID;
    }

    public void setDistributorID(String distributorID) {
        DistributorID = distributorID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_head() {
        return img_head;
    }

    public void setImg_head(String img_head) {
        this.img_head = img_head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_icon() {
        return img_icon;
    }

    public void setImg_icon(String img_icon) {
        this.img_icon = img_icon;
    }

    public CommentListEntity(String id, String img_head, String time, String name, String content, String img_icon, String state) {
        this.id = id;
        this.img_head = img_head;
        this.time = time;
        this.name = name;
        this.content = content;
        this.img_icon = img_icon;
        this.state = state;
    }

    public CommentListEntity() {
    }

    @Override
    public String toString() {
        return "CommentListEntity{" +
                "id='" + id + '\'' +
                ", img_head='" + img_head + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", img_icon='" + img_icon + '\'' +
                '}';
    }
}
