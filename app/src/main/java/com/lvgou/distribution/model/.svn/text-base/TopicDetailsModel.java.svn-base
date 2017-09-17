package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface TopicDetailsModel {
    public void topicdetail(String topicId, String sign, ICallBackListener callBackListener);

    public void commenttopic(String distributorId, String topicId, String commentId, String content, String sign, ICallBackListener callBackListener);

    public void topiccommentlist(String topicId, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener callBackListener);

    void topiccommentdel(String distributorId,String talkCommentId, String sign, ICallBackListener mICallBackListener);
}
