package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/3/15 0015.
 * 订单中心 实体类
 */
public class OrderCentralEntity {

    private String order_time;// 下单时间
    private String order_name;// 名字
    private String order_number;// 订单号
    private String order_status;// 订单状态
    private String order_list;// 订单子数据
    private String order_pay;// 实际支付
    private String quntity;//商品总数
    private String detial_str;//详情页数据
    private String total_youjin;// 佣金


    public String getTotal_youjin() {
        return total_youjin;
    }

    public void setTotal_youjin(String total_youjin) {
        this.total_youjin = total_youjin;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getDetial_str() {
        return detial_str;
    }

    public void setDetial_str(String detial_str) {
        this.detial_str = detial_str;
    }

    public String getQuntity() {
        return quntity;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }

    public String getOrder_pay() {
        return order_pay;
    }

    public void setOrder_pay(String order_pay) {
        this.order_pay = order_pay;
    }

    public String getOrder_list() {
        return order_list;
    }

    public void setOrder_list(String order_list) {
        this.order_list = order_list;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public OrderCentralEntity(String order_time, String detial_str, String quntity, String order_pay, String order_list, String order_status, String order_number, String order_name, String total_youjin) {
        this.order_time = order_time;
        this.detial_str = detial_str;
        this.quntity = quntity;
        this.order_pay = order_pay;
        this.order_list = order_list;
        this.order_status = order_status;
        this.order_number = order_number;
        this.order_name = order_name;
        this.total_youjin = total_youjin;
    }

    public OrderCentralEntity() {
    }

    @Override
    public String toString() {
        return "OrderCentralEntity{" +
                "order_time='" + order_time + '\'' +
                ", order_name='" + order_name + '\'' +
                ", order_number='" + order_number + '\'' +
                ", order_status='" + order_status + '\'' +
                ", order_list='" + order_list + '\'' +
                ", order_pay='" + order_pay + '\'' +
                ", quntity='" + quntity + '\'' +
                ", detial_str='" + detial_str + '\'' +
                '}';
    }
}
