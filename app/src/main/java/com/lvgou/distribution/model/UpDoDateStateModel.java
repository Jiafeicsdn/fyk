package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface UpDoDateStateModel {
    /**
     * 更改学习状态
     *
     * @param distributorid
     * @param studyid
     * @param sign
     * @return
     */
    void upDoDateState(String distributorid, String studyid, String sign, ICallBackListener callBackListener);
}
