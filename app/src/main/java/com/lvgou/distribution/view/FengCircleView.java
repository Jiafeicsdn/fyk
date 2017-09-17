package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface FengCircleView extends BaseView{
    /**
     * 蜂圈动态
     */
    public void findcircleResponse(String resonpse);

    /**
     * 点赞
     */
    public void zanResponse(String resonpse,int position);
    /**
     * 取消点赞
     */
    public void unzanResponse(String resonpse,int position);
    /**
     * 关注
     */
    public void followResponse(String resonpse,int position);
    /**
     * 取消关注
     */
    public void unfollowResponse(String resonpse,int position);
    /**
     * 推荐蜂文
     */
    public void findtagandtopicResponse(String resonpse);
    /**
     * 关注 蜂圈动态
     */
    public void followcircleResponse(String resonpse);
    /**
     * 可能认识的人
     */
    public void mayknowpersonResponse(String resonpse);
    /**
     * 可能认识的人
     */
    public void unreadcountResponse(String resonpse);

}
