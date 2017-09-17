package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.SendGroupRecordModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/14.
 */
public class SendGroupRecordImpl implements SendGroupRecordModel {


    /**
     * 获取派团记录
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getSendGroupRecord(String distributorid, String pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getSendGroupRecord(distributorid, pageindex, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
