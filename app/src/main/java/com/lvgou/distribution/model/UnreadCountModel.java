package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/21.
 */

public interface UnreadCountModel {
    /**
     * 未读消息数
     * @param distributorid
     * @param getNewestDistributorId  	是否获取最新评论或者点赞的用户编号 1=获取 其余=不获取
     * @param sign
     * @return
     */
    void unreadCount(String distributorid,int getNewestDistributorId, String sign, ICallBackListener callBackListener);
}
