package com.lvgou.distribution.model;

import com.lvgou.distribution.activity.SelectLocalAdressActivity;
import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Swow on 2016/10/18.
 */
public interface PersonalCircleListModel {

    void getTalkDetial(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener);
    /**
     * 获取信息列表
     *
     * @param distributorId
     * @param currPage
     * @param sign
     * @param callBackListener
     */
    void getCommentList(String distributorId, String prePageLastDataObjectId, String currPage, String sign, ICallBackListener callBackListener);


    /**
     * 获取点赞列表 （被赞）
     *
     * @param distributorId
     * @param currPage
     * @param sign
     * @param callBackListener
     */
    void getZanList(String distributorId, String prePageLastDataObjectId, String currPage, String sign, ICallBackListener callBackListener);
    /**
     * 获取点赞列表 （我的点赞）
     *
     * @param distributorId
     * @param currPage
     * @param sign
     * @param callBackListener
     */
    void getMyZanList1(String distributorId, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener callBackListener);


    /**
     * 获取评论列表 （我的评论）
     *
     * @param distributorId
     * @param currPage
     * @param sign
     * @param callBackListener
     */
    void getMycommentlist1(String distributorId, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener callBackListener);

    /**
     * 评论操作
     *
     * @param distributorId
     * @param talkId
     * @param commentId
     * @param content
     * @param sign
     * @param iCallBackListener
     */
    void doComment(String distributorId, String talkId, String commentId, String content, int tuanbi,String sign, ICallBackListener iCallBackListener);

    /**
     * 未读消息列表
     *
     * @param distributorId
     * @param sign
     */
    void unReadMessageList(String distributorId, String sign, ICallBackListener callBackListener);

    /**
     * @param distributorId
     * @param messageType
     * @param sign
     * @param callBackListener
     */
    void seekUnReadMessage(String distributorId, String messageType, String sign, ICallBackListener callBackListener);

    /**
     * 获取type 跳转
     *
     * @param distributorId
     * @param talkId
     * @param sign
     * @param iCallBackListener
     */
    void getUserType(String distributorId, String talkId, String sign, ICallBackListener iCallBackListener);
}
