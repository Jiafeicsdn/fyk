package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/10.
 */

public interface RecommendDatasModel {
    /**
     * 推荐
     * @param distributorid 导游ID
     * @param sign    签名
     * @return
     */
    void recommendDatas(String distributorid, String sign, ICallBackListener callBackListener);
}
