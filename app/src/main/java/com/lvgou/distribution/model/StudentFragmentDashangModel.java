package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface StudentFragmentDashangModel {
    /**
     * @param distributorid 导游ID
     * @param studyid       课堂ID
     * @param tuanbi        打赏团币数量
     * @param sign          签名
     */
    void dashang(String distributorid, String studyid, int tuanbi, String sign, ICallBackListener callBackListener);

    /**
     * 打赏详情
     *
     * @param studyid 课堂ID
     * @param sign    签名
     */
    void dashangDetail(String studyid, int pageindex, String sign, ICallBackListener callBackListener);
}
