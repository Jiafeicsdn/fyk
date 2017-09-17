package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/10/14.
 */
public interface PersonalInfoModel {

    /**
     * 获取个人信息
     *
     * @param distributorid
     * @param seekid
     * @param applyid
     * @param sign
     * @param callBackListener
     */
    void getPersonalInfo(String distributorid, String seekid, String applyid, String sign, ICallBackListener callBackListener);

    /**
     * 录用导游
     *
     * @param distributorid
     * @param seekid
     * @param applyid
     * @param sign
     * @param callBackListener
     */
    void doEmployment(String distributorid, String seekid, String applyid, String sign, ICallBackListener callBackListener);
}
