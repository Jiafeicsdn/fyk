package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Snow on 2016/9/13.
 */
public interface ReportModel {

    /**
     * 获取报备
     * @param distributorid
     * @param pageindex
     * @param sign
     * @param callBackListener
     */
    void getData(String distributorid, String pageindex, String sign, ICallBackListener callBackListener);

}
