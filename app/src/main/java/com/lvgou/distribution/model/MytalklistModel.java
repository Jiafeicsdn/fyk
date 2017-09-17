package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface MytalklistModel {

    void mytalklist(String distributorid, String seeDistributorId, int currPage,String sign, ICallBackListener mICallBackListener);
    void circleZan(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener);

    void circleUnZan(String distributorid,String talkId,String sign,ICallBackListener mICallBackListener);
    void talkdel(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener);
    void distributormain(String distributorid,String seeDistributorId,String sign,ICallBackListener mICallBackListener);
    void circleUnfollow(String distributorid,String friendId,String sign,ICallBackListener mICallBackListener);

    void circlefollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener);
}
