package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/14.
 */

public interface StudySearchloadModel {
    /**
     * 全局搜索-加载
     * @param distributorid 导游ID
     * @param sign    签名
     * @return
     */
    void studySearchloadDatas(String distributorid, String sign, ICallBackListener callBackListener);
}
