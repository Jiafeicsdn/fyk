package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Snow on 2016/9/12.
 */
public interface ApplyModel {

    /**
     * 去申请
     * @param distributorid
     * @param applyId          应用ID
     * @param sign
     * @param callBackListener 回调接口
     */
    void applay(String distributorid, String applyId, String sign, ICallBackListener callBackListener);
}
