package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/9/20.
 */
public class RecommentClassesEntity {

    private String id;
    private String img_path;
    private String content;
    private String sign_num;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign_num() {
        return sign_num;
    }

    public void setSign_num(String sign_num) {
        this.sign_num = sign_num;
    }

    public RecommentClassesEntity(String id, String img_path, String content, String sign_num) {
        this.id = id;
        this.img_path = img_path;
        this.content = content;
        this.sign_num = sign_num;
    }

    public RecommentClassesEntity() {
    }

    @Override
    public String toString() {
        return "RecommentClassesEntity{" +
                "id='" + id + '\'' +
                ", img_path='" + img_path + '\'' +
                ", content='" + content + '\'' +
                ", sign_num='" + sign_num + '\'' +
                '}';
    }
}
