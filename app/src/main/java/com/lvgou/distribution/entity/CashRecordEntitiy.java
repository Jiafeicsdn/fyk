package com.lvgou.distribution.entity;

import com.lidroid.xutils.view.annotation.PreferenceInject;

/**
 * Created by Snow on 2016/3/11 0011.
 * 提现记录 实体类
 */
public class CashRecordEntitiy {
    private String id;
    private String money;// 体系金额
    private String apply_time;// 申请时间
    private String pay_time;//打款时间
    private String name;//姓名
    private String status;// 状态
    private String bank_name;// 银行名称
    private String bank_num;// 卡号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_num() {
        return bank_num;
    }

    public void setBank_num(String bank_num) {
        this.bank_num = bank_num;
    }

    public CashRecordEntitiy(String id, String money, String apply_time, String pay_time, String name, String status, String bank_name, String bank_num) {
        this.id = id;
        this.money = money;
        this.apply_time = apply_time;
        this.pay_time = pay_time;
        this.name = name;
        this.status = status;
        this.bank_name = bank_name;
        this.bank_num = bank_num;
    }

    public CashRecordEntitiy() {
    }

    @Override
    public String toString() {
        return "CashRecordEntitiy{" +
                "id='" + id + '\'' +
                ", money='" + money + '\'' +
                ", apply_time='" + apply_time + '\'' +
                ", pay_time='" + pay_time + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", bank_num='" + bank_num + '\'' +
                '}';
    }
}
