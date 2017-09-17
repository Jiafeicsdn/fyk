package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/1.
 */

public interface CancelApplyActModel {
    /**
     * 活动-取消报名
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    void cancelApply(String distributorid, String activityid, String sign, ICallBackListener callBackListener);
}
