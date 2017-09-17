package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/12.
 */

public interface TeacherListModel {
    /**
     * 蜂优讲师
     *
     * @param distributorid 讲课Id
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    void teacherList(String distributorid, int pageindex, String sign, ICallBackListener callBackListener);
}
