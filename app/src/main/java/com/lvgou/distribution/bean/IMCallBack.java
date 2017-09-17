package com.lvgou.distribution.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/9.
 */
public class IMCallBack implements Serializable{
    int e1;
    String e2;

    public int getE1() {
        return e1;
    }

    public void setE1(int e1) {
        this.e1 = e1;
    }

    public String getE2() {
        return e2;
    }

    public void setE2(String e2) {
        this.e2 = e2;
    }
}
