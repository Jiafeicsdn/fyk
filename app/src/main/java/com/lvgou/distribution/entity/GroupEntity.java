package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/4/18 0018.
 */
public class GroupEntity {

    private String id;
    private String name;
    private String phones;

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

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public GroupEntity(String id, String name, String phones) {
        this.id = id;
        this.name = name;
        this.phones = phones;
    }

    public GroupEntity() {
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }
}
