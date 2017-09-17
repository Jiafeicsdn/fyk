package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface MyTaskModel {
    /**
     * 我的团币-加载(蜂圈域名)
     * @param distributorid
     * @param sign
     * @return
     */
    void myTaskList(String distributorid,String sign, ICallBackListener callBackListener);
}
