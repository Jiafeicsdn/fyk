package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

import retrofit.http.Field;

/**
 * Created by Administrator on 2016/10/15.
 */
public interface HomePageModel {
    void getIndex(String distributorid,
                  String sign, ICallBackListener mICallBackListener);

    void getFindcircle(String distributorid,
                       String keyword,
                       String tagId,
                       String prePageLastDataObjectId,
                       int currPage,
                       String sign, ICallBackListener mICallBackListener);

    void unreadcount(String distributorid, int getNewestDistributorId,
                     String sign, ICallBackListener mICallBackListener);


    void circleZan(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener);

    void circleUnZan(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener);

    void circleUnfollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener);


    void circlefollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener);

    void findtagandtopic(String distributorid,
                         String sign, ICallBackListener mICallBackListener);

    void followcircle(String distributorid, String prePageLastDataObjectId, int currPage,
                      String sign, ICallBackListener mICallBackListener);

    void mayknowperson(String distributorid, String sign, ICallBackListener mICallBackListener);
}
