package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/17.
 */

public interface UseFollowModel {
    /**
     * 关注好友
     *
     * @param distributorId 导游编号
     * @param friendId      被关注导游编号
     * @param sign          签名
     * @return
     */
    void useFollow(String distributorId, String friendId, String sign, ICallBackListener callBackListener);
}
