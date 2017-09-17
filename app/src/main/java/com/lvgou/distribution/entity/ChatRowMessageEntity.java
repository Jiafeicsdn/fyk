package com.lvgou.distribution.entity;

import android.graphics.Bitmap;

/**
 * Created by Snow on 2016/8/11
 * <p/>
 * 消息 实体类
 */
public class ChatRowMessageEntity {

    private String id;
    private String img_path;
    private String name;
    private String content;
    private String driection;
    private Bitmap bitmap;

    public String getDriection() {
        return driection;
    }

    public void setDriection(String driection) {
        this.driection = driection;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatRowMessageEntity(String id, String name, String img_path, String content, String driection) {
        this.id = id;
        this.name = name;
        this.img_path = img_path;
        this.content = content;
        this.driection = driection;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ChatRowMessageEntity() {
    }

    @Override
    public String toString() {
        return "ChatRowMessageEntity{" +
                "id='" + id + '\'' +
                ", img_path='" + img_path + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
