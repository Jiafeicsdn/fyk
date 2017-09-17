package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Snow on 2016/9/28.
 */
public interface DetialAndHotCityModel {


    /**
     * 获取派团信息、热门城市接口
     *
     * @param distributorid
     * @param seekid
     * @param sign
     * @param callBackListener
     */
    void getGroupDetial(String distributorid, String seekid, String sign, ICallBackListener callBackListener);


    /**
     * 获取报名列表
     *
     * @param seekid
     * @param pageindex
     * @param sign
     * @param icallBackListener
     */
    void getSignUpList(String seekid, String pageindex, String sign, ICallBackListener icallBackListener);


    /**
     * 取消派团
     *
     * @param distributorid
     * @param seekid
     * @param sign
     * @param callBackListener
     */
    void canclePublishGroup(String distributorid, String seekid, String sign, ICallBackListener callBackListener);


    /**
     * 删除派团
     *
     * @param distributorid
     * @param seekid
     * @param sign
     * @param callBackListener
     */
    void deletePublishGroup(String distributorid, String seekid, String sign, ICallBackListener callBackListener);

    /**
     * 报名派团
     * @param distributorid
     * @param seekid
     * @param sign
     * @param callBackListener
     */
    void doSignUp(String distributorid, String seekid, String sign, ICallBackListener callBackListener);
}
