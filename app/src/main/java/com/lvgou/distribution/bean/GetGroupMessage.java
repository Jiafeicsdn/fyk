package com.lvgou.distribution.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/20.
 */
public class GetGroupMessage implements Serializable {
    private int SI;//用户ID
    private int DM;//处理方式
    private String CUI;//聊天对象ID(群组ID)
    private String MI;//基础消息编号
    private int MGT;//获取方式 1：获取基础消息的上N条 -1：获取基础消息的下N条 0：获取最新消息N条
    private int GN;//获取数量,获取数量=0时返回所有历史消息.
    private boolean IILM;//是否包含边界消息

    public int getSI() {
        return SI;
    }

    public void setSI(int SI) {
        this.SI = SI;
    }

    public int getDM() {
        return DM;
    }

    public void setDM(int DM) {
        this.DM = DM;
    }

    public String getCUI() {
        return CUI;
    }

    public void setCUI(String CUI) {
        this.CUI = CUI;
    }

    public String getMI() {
        return MI;
    }

    public void setMI(String MI) {
        this.MI = MI;
    }

    public int getMGT() {
        return MGT;
    }

    public void setMGT(int MGT) {
        this.MGT = MGT;
    }

    public int getGN() {
        return GN;
    }

    public void setGN(int GN) {
        this.GN = GN;
    }

    public boolean isIILM() {
        return IILM;
    }

    public void setIILM(boolean IILM) {
        this.IILM = IILM;
    }

    @Override
    public String toString() {
        return "GetGroupMessage{" +
                "SI=" + SI +
                ", DM=" + DM +
                ", CUI='" + CUI + '\'' +
                ", MI='" + MI + '\'' +
                ", MGT=" + MGT +
                ", GN=" + GN +
                ", IILM=" + IILM +
                '}';
    }
}
