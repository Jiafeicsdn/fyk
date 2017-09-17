package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/10/19.
 */
public class AllTopicEntity {

    private String id;
    private String img_path;
    private String title;
    private String readNum;
    private String topicNum;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(String topicNum) {
        this.topicNum = topicNum;
    }

    public AllTopicEntity(String img_path, String id, String title, String readNum, String topicNum) {
        this.img_path = img_path;
        this.id = id;
        this.title = title;
        this.readNum = readNum;
        this.topicNum = topicNum;
    }

    public AllTopicEntity() {
    }

    @Override
    public String toString() {
        return "AllTopicEntity{" +
                "id='" + id + '\'' +
                ", img_path='" + img_path + '\'' +
                ", title='" + title + '\'' +
                ", readNum='" + readNum + '\'' +
                ", topicNum='" + topicNum + '\'' +
                '}';
    }
}
