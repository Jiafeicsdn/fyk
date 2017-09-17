package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/26.
 */

public interface MyFollowListModel {
    void myFollowList(String distributorId, String seeDistributorId,int currPage, String sign, ICallBackListener callBackListener);
}
