package com.lvgou.distribution.bean;

import android.graphics.Bitmap;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/31.
 */
@Table("test_model")
public class GetMessages implements Serializable {
    private String C;//表情文字
    private int MT;//消息类型
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int I;//id
    private String SI;//发送者编号
    private String GI;//群组编号
    private String CT;//创建时间
    private String U;//视频连接
    private String CI;//视频缩略图
    private Bitmap bitmap;//视频第一帧图片
    private String O;
    private String T;
    private int L;//语音长度
    private boolean isread;//消息是否读过
    private boolean isSendSuccess=true;//消息是否发送成功
    private boolean isplayVoice=true;

    private boolean message_state;//消息的发送状态


    public boolean isMessage_state() {
        return message_state;
    }

    public void setMessage_state(boolean message_state) {
        this.message_state = message_state;
    }

    public boolean isSendSuccess() {
        return isSendSuccess;
    }

    public void setIsSendSuccess(boolean isSendSuccess) {
        this.isSendSuccess = isSendSuccess;
    }

    public boolean isread() {
        return isread;
    }

    public void setIsread(boolean isread) {
        this.isread = isread;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public String getO() {
        return O;
    }

    public void setO(String o) {
        O = o;
    }

    public String getT() {
        return T;
    }

    public void setT(String t) {
        T = t;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getU() {
        return U;
    }

    public void setU(String u) {
        U = u;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public int getMT() {
        return MT;
    }

    public void setMT(int MT) {
        this.MT = MT;
    }

    public int getI() {
        return I;
    }

    public void setI(int i) {
        I = i;
    }

    public String getSI() {
        return SI;
    }

    public void setSI(String SI) {
        this.SI = SI;
    }

    public String getGI() {
        return GI;
    }

    public void setGI(String GI) {
        this.GI = GI;
    }

    public String getCT() {
        return CT;
    }

    public void setCT(String CT) {
        this.CT = CT;
    }

    public boolean isplayVoice() {
        return isplayVoice;
    }

    public void setIsplayVoice(boolean isplayVoice) {
        this.isplayVoice = isplayVoice;
    }

    @Override
    public String toString() {
        return "GroupMessageExtData{" +
                "C='" + C + '\'' +
                ", MT=" + MT +
                ", I='" + I + '\'' +
                ", SI='" + SI + '\'' +
                ", GI='" + GI + '\'' +
                ", CT='" + CT + '\'' +
                ", U='" + U + '\'' +
                ", CI='" + CI + '\'' +
                ", bitmap=" + bitmap +
                ", O='" + O + '\'' +
                ", T='" + T + '\'' +
                ", L=" + L +
                ", isread=" + isread +
                ", isSendSuccess=" + isSendSuccess +
                ", message_state=" + message_state +
                '}';
    }
}