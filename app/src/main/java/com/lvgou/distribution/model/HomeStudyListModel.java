package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/13.
 */

public interface HomeStudyListModel {
    /**
     * 个人主页课程列表
     *
     * @param distributorid 待查询的导游编号
     * @param currPage      当前页
     * @param sign          签名
     * @return
     */
    void homeStudyList(String distributorid, int currPage, String sign, ICallBackListener callBackListener);
}
