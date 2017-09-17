package com.lvgou.distribution.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class GetMemberList {

    private int FV;
    private  int FATV;
    private int UA;
    private int UGSV1;
    private List<UGSV2> UGSV2;
       private int UFSV;
        private int IMCGV;
        private List<UGSV2> IMGMV;
        private int CNV;
        private int CNPV;
        private int USTV;
        private int SSTV;
        private int SCV;

    public int getSCV() {
        return SCV;
    }

    public void setSCV(int SCV) {
        this.SCV = SCV;
    }

    public int getSSTV() {
        return SSTV;
    }

    public void setSSTV(int SSTV) {
        this.SSTV = SSTV;
    }

    public int getFV() {
        return FV;
    }

    public void setFV(int FV) {
        this.FV = FV;
    }

    public int getFATV() {
        return FATV;
    }

    public void setFATV(int FATV) {
        this.FATV = FATV;
    }

    public int getUA() {
        return UA;
    }

    public void setUA(int UA) {
        this.UA = UA;
    }

    public int getUGSV1() {
        return UGSV1;
    }

    public void setUGSV1(int UGSV1) {
        this.UGSV1 = UGSV1;
    }

    public List<com.lvgou.distribution.bean.UGSV2> getUGSV2() {
        return UGSV2;
    }

    public void setUGSV2(List<com.lvgou.distribution.bean.UGSV2> UGSV2) {
        this.UGSV2 = UGSV2;
    }

    public int getUFSV() {
        return UFSV;
    }

    public void setUFSV(int UFSV) {
        this.UFSV = UFSV;
    }

    public int getIMCGV() {
        return IMCGV;
    }

    public void setIMCGV(int IMCGV) {
        this.IMCGV = IMCGV;
    }

    public List<com.lvgou.distribution.bean.UGSV2> getIMGMV() {
        return IMGMV;
    }

    public void setIMGMV(List<com.lvgou.distribution.bean.UGSV2> IMGMV) {
        this.IMGMV = IMGMV;
    }

    public int getCNV() {
        return CNV;
    }

    public void setCNV(int CNV) {
        this.CNV = CNV;
    }

    public int getCNPV() {
        return CNPV;
    }

    public void setCNPV(int CNPV) {
        this.CNPV = CNPV;
    }

    public int getUSTV() {
        return USTV;
    }

    public void setUSTV(int USTV) {
        this.USTV = USTV;
    }
}
