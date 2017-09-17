package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface RecommendFrientModel {

    /**
     * 获取推荐好友列表
     *
     * @param distributorId
     * @param currPage
     * @param sign
     */
    void getRecommendFriend(String distributorId, String currPage, String sign, ICallBackListener callBackListener);


    /**
     * 加关注
     *
     * @param distributorId
     * @param friendId
     * @param sign
     * @param callBackListener
     */
    void doFollow(String distributorId, String friendId, String sign, ICallBackListener callBackListener);


    /**
     * 取消关注
     *
     * @param distributorId
     * @param friendId
     * @param sign
     * @param callBackListener
     */
    void cancleFollow(String distributorId, String friendId, String sign, ICallBackListener callBackListener);
}
