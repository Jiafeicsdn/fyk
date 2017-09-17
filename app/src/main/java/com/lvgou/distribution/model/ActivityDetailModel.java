package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface ActivityDetailModel {
    /**
     * 活动详情
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    void activityDetailDatas(String distributorid, String activityid, String sign, ICallBackListener callBackListener);
}
