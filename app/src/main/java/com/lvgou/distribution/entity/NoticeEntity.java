package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/3/28 0028.
 */
public class NoticeEntity {

    private String id;
    private String name;
    private String content;
    private String time;
    private String status;
    private String user_id;// 大于0 已读

    private int IconType;

    public int getIconType() {
        return IconType;
    }

    public void setIconType(int iconType) {
        IconType = iconType;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public NoticeEntity(String id, String status, String time, String content, String name, String user_id) {
        this.id = id;
        this.status = status;
        this.time = time;
        this.content = content;
        this.name = name;
        this.user_id = user_id;
    }

    public NoticeEntity() {
    }

    @Override
    public String toString() {
        return "NoticeEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
