package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.CancelApplyActModel;
import com.lvgou.distribution.model.DelActivityModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/1.
 */

public class CancelApplyActImpl implements CancelApplyActModel {
    /**
     * 活动-取消报名
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    @Override
    public void cancelApply(String distributorid, String activityid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.cancelApply(distributorid, activityid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}