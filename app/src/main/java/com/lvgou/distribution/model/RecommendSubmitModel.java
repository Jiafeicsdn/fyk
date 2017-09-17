package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface RecommendSubmitModel {
    void recommendSubmit(String distributorid, String teachername, String weixin, String mobile, String intro, String sign, ICallBackListener callBackListener);
}
