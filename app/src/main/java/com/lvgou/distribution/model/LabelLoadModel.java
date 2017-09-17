package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/17.
 */

public interface LabelLoadModel {
    /**
     * 申请开课-课程类型加载
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    void labelLoadDatas(String distributorid, String sign, ICallBackListener callBackListener);
}
