package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface LookTeacherModel {
    /**
     * 名师讲堂-查看
     *
     * @param distributorid 导游ID
     * @param id            课程ID
     * @param couponid      听课券ID(导游领取后的记录ID)
     * @param sign          签名
     * @return
     */
    void lookTeacher(String distributorid, String id, String couponid, String sign, ICallBackListener callBackListener);
}
