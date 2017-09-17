package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/3/24 0024.
 */
public class ClassifyEntity {
    private String id;
    private String name;
    private String num;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public ClassifyEntity(String id, String name, String num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public ClassifyEntity() {
    }

    @Override
    public String toString() {
        return "ClassifyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
