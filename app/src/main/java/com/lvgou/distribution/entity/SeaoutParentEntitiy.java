package com.lvgou.distribution.entity;

import java.util.List;

/**
 * Created by Snow on 2016/3/30 0030.
 */
public class SeaoutParentEntitiy {
    private String id;
    private String name;
    private List<SeaoutClassifyEntity> seaoutClassifyEntityList;

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

    public List<SeaoutClassifyEntity> getSeaoutClassifyEntityList() {
        return seaoutClassifyEntityList;
    }

    public void setSeaoutClassifyEntityList(List<SeaoutClassifyEntity> seaoutClassifyEntityList) {
        this.seaoutClassifyEntityList = seaoutClassifyEntityList;
    }

    public SeaoutParentEntitiy(String id, String name, List<SeaoutClassifyEntity> seaoutClassifyEntityList) {
        this.id = id;
        this.name = name;
        this.seaoutClassifyEntityList = seaoutClassifyEntityList;
    }

    public SeaoutParentEntitiy() {
    }

    @Override
    public String toString() {
        return "SeaoutParentEntitiy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", seaoutClassifyEntityList=" + seaoutClassifyEntityList +
                '}';
    }
}
