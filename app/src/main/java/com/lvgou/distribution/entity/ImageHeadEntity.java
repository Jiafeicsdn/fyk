package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/5/17 0017.
 */
public class ImageHeadEntity {

    private String id;
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageHeadEntity(String id, String path) {
        this.id = id;
        this.path = path;
    }

    public ImageHeadEntity() {
    }

    @Override
    public String toString() {
        return "ImageHeadEntity{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
