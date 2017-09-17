package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/13.
 */

public interface DistributorHomeModel {
    /**
     * 个人主页
     *
     * @param distributorid    导游编号
     * @param seeDistributorId 查看导游编号
     * @param sign             签名
     * @return
     */
    void distributorHome(String distributorid, String seeDistributorId, String sign, ICallBackListener callBackListener);
}
