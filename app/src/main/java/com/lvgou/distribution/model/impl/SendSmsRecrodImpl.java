package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.SendSmsRecordModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SendSmsRecrodImpl implements SendSmsRecordModel {

    /**
     * 短信记录列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    @Override
    public void getSmsRecord(String distributorid, String pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getSendSmsRecord(distributorid,pageindex,sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));

    }
}
