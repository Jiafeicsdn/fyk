package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/10/21.
 */
public interface FansFollowListModel {

    /**
     * 关注列表
     *
     * @param distributorId
     * @param seeDistributorId
     * @param currPage
     * @param sign
     */
    void getFollowFriend(String distributorId, String seeDistributorId, String currPage, String sign, ICallBackListener callBackListener);



    /**
     * 粉丝列表
     *
     * @param distributorId
     * @param seeDistributorId
     * @param currPage
     * @param sign
     */
    void getFansFriend(String distributorId, String seeDistributorId, String currPage, String sign, ICallBackListener callBackListener);



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
