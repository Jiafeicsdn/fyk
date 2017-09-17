package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/7.
 */

public interface BatchbBuyModel {
    /**
     * 系列课程-批量购买
     *
     * @param distributorid 讲课Id
     * @param teacherid     课程ID包含免费课程(1,2,3,4,5)
     * @param couponid      课程对应可使用听课券ID，没有则为0(1,2,0,3,0)
     * @param sign          签名
     * @return
     */
    void batchbBuy(String distributorid, String teacherid, String couponid, String sign, ICallBackListener callBackListener);
}
