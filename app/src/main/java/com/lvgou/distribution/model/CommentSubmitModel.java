package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface CommentSubmitModel {
    /**
     * 活动评论-发布
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param comment       评论内容
     * @param sign          签名
     * @return
     */
    void commentSubmit(String distributorid, String activityid, String comment, String sign, ICallBackListener callBackListener);
}
