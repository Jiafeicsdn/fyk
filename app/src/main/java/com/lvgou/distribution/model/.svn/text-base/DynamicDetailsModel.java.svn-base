package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/10/15.
 */
public interface DynamicDetailsModel {
    void talkisnormal(String distributorid,
                      String sign, ICallBackListener mICallBackListener);

    void talkcommentlist(String talkId, String prePageLastDataObjectId,
                         int currPage,
                         String sign, ICallBackListener mICallBackListener);

    void commenttalk(String distributorid, String talkId, String commentId, String content,int tuanbi, String sign, ICallBackListener mICallBackListener);

    void zhuanFa(String distributorid, String talkId, String content, String sign, ICallBackListener mICallBackListener);

    void getTalkDetial(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener);

    void recommenttalkcontent(String talkId, String sign, ICallBackListener mICallBackListener);
    void talkcommentdel(String distributorId,String talkCommentId, String sign, ICallBackListener mICallBackListener);

    void talkcollection(String distributorId,String talkId, String sign, ICallBackListener mICallBackListener);
    void talkuncollection(String distributorId,String talkId, String sign, ICallBackListener mICallBackListener);
}
