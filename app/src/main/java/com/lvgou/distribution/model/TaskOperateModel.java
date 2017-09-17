package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface TaskOperateModel {
    /**
     * 我的团币-领取(蜂圈域名)
     *
     * @param distributorid
     * @param type          操作类型（对应值在下方注解）
     * @param sign
     * @return
     */
    void taskOperate(String distributorid, int type, String sign, ICallBackListener callBackListener);
}
