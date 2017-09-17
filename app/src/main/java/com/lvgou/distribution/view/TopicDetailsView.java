package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2016/10/22.
 */
public interface TopicDetailsView {
    /**
     * 热门话题详情
     */
    public void topicdetailResponse(String resonpse);

    /**
     * 话题评论
     */
    public void commenttopicResponse(String resonpse);

    /***
     * 热门话题列表
     * @param resonpse
     */
    public void topiccommentlistResponse(String resonpse);
    public void excuteFailedCallBack(String type,String resonpse);
    public void excuteSuccessCallBack(String response);
}

