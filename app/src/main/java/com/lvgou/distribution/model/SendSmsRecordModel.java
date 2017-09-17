package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface SendSmsRecordModel {

    /**
     * 获取短信记录
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    void getSmsRecord(String distributorid, String pageindex, String sign, ICallBackListener callBackListener);
}
