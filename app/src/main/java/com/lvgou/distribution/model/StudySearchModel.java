package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/14.
 */

public interface StudySearchModel {
    /**
     * 全局搜索
     *
     * @param distributorid 导游ID
     * @param searchword    搜索关键字
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    void studySearchDatas(String distributorid, String searchword, int pageindex, String sign, ICallBackListener callBackListener);
}
