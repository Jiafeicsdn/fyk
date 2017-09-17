package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/5/2.
 */

public interface DelCommentModel {
    void delComment(String distributorid,String commentid,  String sign, ICallBackListener callBackListener);
}
