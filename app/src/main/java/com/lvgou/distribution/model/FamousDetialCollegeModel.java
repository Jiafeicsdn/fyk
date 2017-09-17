package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.CommentZanBean;

/**
 * Created by Snow on 2016/9/12.
 */
public interface FamousDetialCollegeModel {


    /**
     * 获取数据
     *
     * @param distributorid
     * @param id
     * @param sign
     */
    void getData(String distributorid, String id, String sign, ICallBackListener callBackListener);

    /**
     * 获取评论列表
     *
     * @param id
     * @param pageIndex
     * @param sign
     */
    void getCommentData(String id, String pageIndex, String sign, ICallBackListener callBackListener);

    /**
     * 评论，点赞
     *
     * @param commentZanBean
     * @param callBackListener
     */
    void doCommentZan(CommentZanBean commentZanBean, ICallBackListener callBackListener);

    /**
     * 查看课程
     *
     * @param distributorid
     * @param id
     * @param sign
     * @param callBackListener
     */
    void doFamousSeek(String distributorid, String id, String sign, ICallBackListener callBackListener);

    /**
     * 报名听课
     *
     * @param distributorid
     * @param id
     * @param sign
     * @param callBackListener
     */
    void doFamousSignUp(String distributorid, String id, String sign, ICallBackListener callBackListener);

    /**
     * 播放点击量
     *
     * @param distributorid
     * @param studyid
     * @param sign
     * @param callBackListener
     */
    void doPlayTimes(String distributorid, String studyid, String sign, ICallBackListener callBackListener);

    /**
     * 更改学习状态
     *
     * @param distributorid
     * @param studyid
     * @param sign
     * @param callBackListener
     */
    void doUpdateState(String distributorid, String studyid, String sign, ICallBackListener callBackListener);
    /**
     * 课程评论删除
     *
     * @param distributorid
     * @param commentid
     * @param sign
     * @param callBackListener
     */
    void delcomment(String distributorid, String commentid, String sign, ICallBackListener callBackListener);
}
