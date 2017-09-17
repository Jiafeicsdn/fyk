package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/3/10 0010.
 */
public class BankEntity {

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

    public BankEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BankEntity() {
    }
}
