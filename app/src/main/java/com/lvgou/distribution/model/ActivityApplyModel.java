package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface ActivityApplyModel {
    /**
     * 活动-报名
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    void activityApply(String distributorid, String activityid, String sign, ICallBackListener callBackListener);
}
