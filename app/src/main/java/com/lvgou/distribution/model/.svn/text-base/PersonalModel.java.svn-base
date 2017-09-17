package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.entity.ApplyClassEntity;

/**
 * Created by Administrator on 2016/10/11.
 */
public interface PersonalModel {


    /**
     * 获取个人中心信息
     *
     * @param distributorid
     * @param sign
     * @param icallBackListener
     */
    void getPersonalData(String distributorid, String sign, ICallBackListener icallBackListener);


    /**
     * 上传头像
     *
     * @param distributorid
     * @param sign
     * @param picurl
     * @param icallBackListener
     */
    void upLoadHead(String distributorid, String picurl, String sign, ICallBackListener icallBackListener);


    /**
     * 获取消息未读数
     *
     * @param distributorid
     * @param sign
     * @param getNewestDistributorId
     * @param icallBackListener
     */
    void getMessageNum(String distributorid, String getNewestDistributorId, String sign, ICallBackListener icallBackListener);

    /**
     * 获取订单数量
     *
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    void getOrderNum(String distributorid, String sign, ICallBackListener callBackListener);

    /**
     * 申请开课
     *
     * @param applyClassEntity
     * @param callBackListener
     */
    void applyClass(ApplyClassEntity applyClassEntity, ICallBackListener callBackListener);


    /**
     * 获取热门标签
     *
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    void getHotTag(String distributorid, String sign, ICallBackListener callBackListener);

}
