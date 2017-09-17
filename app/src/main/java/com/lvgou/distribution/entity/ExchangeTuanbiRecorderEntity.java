package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/11/3.
 */
public class ExchangeTuanbiRecorderEntity {

    private String id;
    private String time;
    private String state;
    private String img_path;
    private String title;
    private String tuanbi;
    private String num;
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTuanbi() {
        return tuanbi;
    }

    public void setTuanbi(String tuanbi) {
        this.tuanbi = tuanbi;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ExchangeTuanbiRecorderEntity(String id, String time, String state, String img_path, String title, String tuanbi, String num, String price) {
        this.id = id;
        this.time = time;
        this.state = state;
        this.img_path = img_path;
        this.title = title;
        this.tuanbi = tuanbi;
        this.num = num;
        this.price = price;
    }

    public ExchangeTuanbiRecorderEntity() {
    }

    @Override
    public String toString() {
        return "ExchangeTuanbiRecorderEntity{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", img_path='" + img_path + '\'' +
                ", title='" + title + '\'' +
                ", tuanbi='" + tuanbi + '\'' +
                ", num='" + num + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
