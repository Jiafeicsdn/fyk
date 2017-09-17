package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/10/19.
 */
public interface FengwenSearchModel {

    /**
     * 获取蜂文列表
     *
     * @param distributorId
     * @param keyword
     * @param tagId
     * @param currPage
     * @param sign
     * @param iCallBackListener
     */
    void getFengwenList(String distributorId, String keyword, String tagId, String currPage, String sign, ICallBackListener iCallBackListener);


    /**
     * 获取用户
     *
     * @param distributorId
     * @param keyword
     * @param currPage
     * @param sign
     * @param iCallBackListener
     */
    void getUserList(String distributorId, String keyword, String currPage, String sign, ICallBackListener iCallBackListener);


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
     * 加关注
     * @param distributorId
     * @param friendId
     * @param sign
     * @param callBackListener
     */
    void doFollow(String distributorId, String friendId, String sign, ICallBackListener callBackListener);


    /**
     * 取消关注
     * @param distributorId
     * @param friendId
     * @param sign
     * @param callBackListener
     */
    void cancleFollow(String distributorId, String friendId, String sign, ICallBackListener callBackListener);
}
