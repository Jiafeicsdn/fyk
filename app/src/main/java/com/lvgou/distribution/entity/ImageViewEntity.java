package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/3/17 0017.
 */
public class ImageViewEntity {

    private String id;
    private String path;
    private String title;
    private String intro;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public ImageViewEntity(String id, String path, String intro, String title) {
        this.id = id;
        this.path = path;
        this.intro = intro;
        this.title = title;
    }

    public ImageViewEntity() {
    }

    @Override
    public String toString() {
        return "ImageViewEntity{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", intro='" + intro + '\'' +
                '}';
    }
}
