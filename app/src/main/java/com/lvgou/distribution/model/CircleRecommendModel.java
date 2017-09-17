package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Swow on 2016/10/18.
 */
public interface CircleRecommendModel {

    /**
     * 点赞
     *
     * @param distributorId
     * @param talkId
     * @param sign
     * @param callBackListener
     */
    void doZan(String distributorId, String talkId, String sign, ICallBackListener callBackListener);


    /**
     * 取消点赞
     *
     * @param distributorId
     * @param talkId
     * @param sign
     * @param callBackListener
     */
    void cancleZan(String distributorId, String talkId, String sign, ICallBackListener callBackListener);

    /**
     * 获取推荐列表
     *
     * @param distributorId
     * @param currPage
     * @param sign
     * @param callBackListener
     */
    void getCircleRecomment(String distributorId, String prePageLastDataObjectId, String currPage, String sign, ICallBackListener callBackListener);

    /**
     * 主页详情
     */
    void distributormain(String distributorid, String seeDistributorId, String sign, ICallBackListener mICallBackListener);
}
