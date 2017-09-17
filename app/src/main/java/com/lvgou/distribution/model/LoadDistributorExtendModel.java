package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/10.
 */

public interface LoadDistributorExtendModel {
    /**
     * 我的-实名认证获取数据
     *
     * @param distributorid 讲课Id
     * @param sign          签名
     * @return
     */
    void loadDistributorExtend(String distributorid, String sign, ICallBackListener callBackListener);
}
