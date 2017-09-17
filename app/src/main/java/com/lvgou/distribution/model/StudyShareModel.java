package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface StudyShareModel {
    /**
     * 每日课程分享奖励团币
     *
     * @param distributorid
     * @param sign
     * @return
     */
    void studyShare(String distributorid, String sign, ICallBackListener callBackListener);
}
