package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Snow on 2016/9/13.
 */
public interface ReportShopSearchModel {
    /**
     * 报备店铺列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     * @param callBackListener
     */
    void getShopSearchData(String distributorid, String key, String pageindex, String sign, ICallBackListener callBackListener);
}
