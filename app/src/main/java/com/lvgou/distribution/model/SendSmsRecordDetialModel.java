package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface SendSmsRecordDetialModel {

    /**
     * 获取短信记录详情
     *
     * @param distributorid
     * @param msgid
     * @param sign
     */
    void getSendSmsRecordDetial(String distributorid, String msgid, String sign, ICallBackListener callBackListener);
}
