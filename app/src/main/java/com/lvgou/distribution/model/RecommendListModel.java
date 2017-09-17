package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface RecommendListModel {
    void recommendList(String distributorid, int pageindex, String sign, ICallBackListener callBackListener);
}
