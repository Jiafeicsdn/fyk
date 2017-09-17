package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/10/9.
 */
public class DayEntity {

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

    public DayEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public DayEntity() {

    }

    @Override
    public String toString() {
        return "DayEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
