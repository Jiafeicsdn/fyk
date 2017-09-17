package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/30.
 */

public interface FeedBackModel {
    /**
     * 意见反馈
     *
     * @param distributorid 导游ID
     * @param content       内容
     * @param sign          签名
     * @return
     */
    void feedBack(String distributorid, String content, String sign, ICallBackListener callBackListener);
}
