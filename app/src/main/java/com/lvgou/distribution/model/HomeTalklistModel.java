package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/13.
 */

public interface HomeTalklistModel {
    /**
     * 个人首页-蜂文列表
     *
     * @param distributorId    导游编号
     * @param seeDistributorId 查看导游编号
     * @param currPage         当前页
     * @param sign             签名
     * @return
     */
    void homeTalklist(String distributorId, String seeDistributorId, int currPage, String sign, ICallBackListener callBackListener);
}
