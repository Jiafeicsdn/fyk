package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Snow on 2016/9/22.
 */
public interface TeacherSeatModel {

    /**
     * 讲师列表
     *
     * @param pageindex
     * @param key
     * @param sign
     * @param callBackListener
     */
    void getTeacherSeatData(String distributorid,String pageindex, String key, String sign, ICallBackListener callBackListener);

    /**
     * 申请讲师状态
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    void getSatte(String distributorid, String sign, ICallBackListener callBackListener);
}


