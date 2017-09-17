package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface SmsModel {

    /**
     * 获取模板列表
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    public void getModelList(String distributorid, String sign, ICallBackListener callBackListener);

    /**
     * 删除模板
     * @param distributorid
     * @param tmpid
     * @param sign
     * @param callBackListener
     */
    public void deleteModel(String distributorid, String tmpid, String sign, ICallBackListener callBackListener);

}
