package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.FengwenZanModel;
import com.lvgou.distribution.model.HomeTalklistModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FengwenZanImpl implements FengwenZanModel {
    /**
     * 点赞
     *
     * @param distributorId 导游编号
     * @param talkId        点赞的蜂文编号
     * @param sign          签名
     * @return
     */
    @Override
    public void fengwenZan(String distributorId, String talkId, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.fengwenZan(distributorId, talkId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}