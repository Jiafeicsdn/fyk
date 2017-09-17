package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.FengwenDelModel;
import com.lvgou.distribution.model.FengwenUnZanModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FengwenDelImpl implements FengwenDelModel {
    /**
     * 蜂文删除
     *
     * @param distributorId 导游编号
     * @param talkId        蜂文编号
     * @param sign          签名
     * @return
     */
    @Override
    public void fengwenDel(String distributorId, String talkId, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.fengwenDel(distributorId, talkId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}