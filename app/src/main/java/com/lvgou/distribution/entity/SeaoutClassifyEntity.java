package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/3/30 0030.
 */
public class SeaoutClassifyEntity {
    private String id;
    private String name;

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

    public SeaoutClassifyEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SeaoutClassifyEntity() {
    }

    @Override
    public String toString() {
        return "SeaoutClassifyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
